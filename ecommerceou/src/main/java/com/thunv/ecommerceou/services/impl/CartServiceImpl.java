package com.thunv.ecommerceou.services.impl;

import com.thunv.ecommerceou.models.pojo.*;
import com.thunv.ecommerceou.repositories.*;
import com.thunv.ecommerceou.services.*;
import com.thunv.ecommerceou.utils.MomoPaymentUtils;
import com.thunv.ecommerceou.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class CartServiceImpl implements CartService {
    @Autowired
    private CartRepository cartRepository;
    @Autowired
    private ManageErrorLogService manageErrorLogService;
    @Autowired
    private CartItemRepository cartItemRepository;
    @Autowired
    private OrderStateService orderStateService;
    @Autowired
    private OrdersRepository ordersRepository;
    @Autowired
    private OrdersAgencyRepository ordersAgencyRepository;
    @Autowired
    private AgencyService agencyService;
    @Autowired
    private OrderDetailRepository orderDetailRepository;
    @Autowired
    private ItemRepository itemRepository;
    @Autowired
    private Utils utils;
    @Autowired
    private MailService mailService;
    @Autowired
    private MomoPaymentUtils momoPaymentUtils;
    @Autowired
    private OrderTrackingService orderTrackingService;
    @Override
    public List<CartItem> addToCart(User user, ItemPost itemPost, int quantity) throws RuntimeException{
        try {
            Cart cart;
            if (this.cartRepository.existsByAuthor(user)){
                cart = this.cartRepository.findByAuthor(user).get(0);
                if (this.cartItemRepository.existsByItemPost(cart,itemPost)){
                    CartItem cartItem = this.cartItemRepository.getItemExistInCart(cart,itemPost);
                    int qty = cartItem.getQuantity() + quantity;
                    if (itemPost.getInventory() < qty){
                        throw new RuntimeException("Not enough quantity in stock");
                    }
                    cartItem.setQuantity(qty);
                    this.cartItemRepository.save(cartItem);
                }else {
                    CartItem cartItem = new CartItem();
                    cartItem.setCart(cart);
                    if (itemPost.getInventory() < quantity){
                        throw new RuntimeException("Not enough quantity in stock");
                    }
                    cartItem.setItemPost(itemPost);
                    cartItem.setQuantity(quantity);
                    this.cartItemRepository.save(cartItem);
                }
            }else {
                cart = new Cart();
                cart.setAuthor(user);
                this.cartRepository.save(cart);
                CartItem cartItem = new CartItem();
                cartItem.setCart(cart);
                cartItem.setItemPost(itemPost);
                cartItem.setQuantity(quantity);
                this.cartItemRepository.save(cartItem);
            }
            return this.cartItemRepository.findByCart(cart).stream().toList();
        }catch (Exception ex){
            String error_ms = ex.getMessage();
            throw new RuntimeException(error_ms);
        }
    }

    @Override
    public List<CartItem> updateCart(User user, ItemPost itemPost, int quantity) throws RuntimeException {
        try {
            Cart cart;
            if (itemPost.getInventory() < quantity){
                throw new RuntimeException("Not enough quantity in stock");
            }
            if (this.cartRepository.existsByAuthor(user)){
                cart = this.cartRepository.findByAuthor(user).get(0);
                if (this.cartItemRepository.existsByItemPost(cart,itemPost)){
                    CartItem cartItem = this.cartItemRepository.getItemExistInCart(cart,itemPost);
                    cartItem.setQuantity(quantity);
                    this.cartItemRepository.save(cartItem);
                }else {
                    CartItem cartItem = new CartItem();
                    cartItem.setCart(cart);
                    cartItem.setItemPost(itemPost);
                    cartItem.setQuantity(quantity);
                    this.cartItemRepository.save(cartItem);
                }
            }else {
                cart = new Cart();
                cart.setAuthor(user);
                this.cartRepository.save(cart);
                CartItem cartItem = new CartItem();
                cartItem.setCart(cart);
                cartItem.setItemPost(itemPost);
                cartItem.setQuantity(quantity);
                this.cartItemRepository.save(cartItem);
            }
            return this.cartItemRepository.findByCart(cart).stream().toList();
        }catch (Exception ex){
            String error_ms = ex.getMessage();
            throw new RuntimeException(error_ms);
        }
    }

    @Override
    public List<CartItem> removeItemFromCart(User user, ItemPost itemPost) throws RuntimeException{
        try {
            Cart cart = this.cartRepository.findByAuthor(user).get(0);
            if (this.cartRepository.existsByAuthor(user) && this.cartItemRepository.existsByItemPost(cart,itemPost)){
                CartItem cartItem = this.cartItemRepository.getItemExistInCart(cart,itemPost);
                this.cartItemRepository.deleteById(cartItem.getId());
            }else {
                throw new RuntimeException("Cart item does not exist");
            }
            return this.cartItemRepository.findByCart(cart).stream().toList();
        }catch (Exception ex){
            String error_ms = ex.getMessage();
            throw new RuntimeException(error_ms);
        }
    }

    @Override
    public List<CartItem> clearCart(User user) throws RuntimeException {
        try {
            Cart cart;
            if (this.cartRepository.existsByAuthor(user)){
                cart = this.cartRepository.findByAuthor(user).get(0);
            }else {
                cart = new Cart();
                cart.setAuthor(user);
                this.cartRepository.save(cart);
            }
            List<CartItem> cartItemList = this.cartItemRepository.findByCart(cart).stream().toList();
            for (CartItem cartItem: cartItemList){
                this.cartItemRepository.deleteById(cartItem.getId());
            }
            return this.cartItemRepository.findByCart(cart).stream().toList();
        }catch (Exception ex){
            String error_ms = ex.getMessage();
            throw new RuntimeException(error_ms);
        }
    }

    @Override
    public List<CartItem> paymentCart(User user, PaymentType paymentType, CustomerAddressBook customerAddressBook, Integer serviceID, Integer serviceTypeID) throws RuntimeException{
        try {
            if (this.utils.checkPhoneNumberIsValid(customerAddressBook.getDeliveryPhone())== false){
                throw new RuntimeException("Invalid phone !!!");
            }
            Cart cart;
            if (this.cartRepository.existsByAuthor(user)){
                cart = this.cartRepository.findByAuthor(user).get(0);
            }else {
                cart = new Cart();
                cart.setAuthor(user);
                this.cartRepository.save(cart);
            }
            List<CartItem> cartItemList = this.cartItemRepository.findByCart(cart).stream().toList();
            for (CartItem cartItem: cartItemList){
                if (cartItem.getItemPost().getInventory() < cartItem.getQuantity()){
                    throw new RuntimeException(String.format("Item %s not enough quantity in stock",cartItem.getItemPost().getName()));
                }
            }
            if (cartItemList.size() > 0){
                Orders orders = new Orders();
                orders.setPaymentType(paymentType);
                orders.setCreatedDate(new Date());
                orders.setTotalPrice(this.getTotalPriceInCart(cart));
                if (paymentType.getId() == 2){
                    orders.setPaymentState(1);
                } else if (paymentType.getId() == 1) {
                    orders.setPaymentState(0);
                }
                orders.setAuthor(user);
                this.ordersRepository.save(orders);
                Map<Integer, List<CartItem>> groupItemByAgency =
                        cartItemList.stream().collect(Collectors.groupingBy(item -> item.getItemPost().getSalePost().getAgency().getId()));
                for (Map.Entry<Integer,  List<CartItem>> v : groupItemByAgency.entrySet()) {
                    OrderAgency orderAgency = new OrderAgency();
                    orderAgency.setDeliveryInfo(customerAddressBook);
                    orderAgency.setAgency(this.agencyService.getAgencyByID(v.getKey()));
                    orderAgency.setOrders(orders);
                    orderAgency.setOrderState(this.orderStateService.getOrderStateByID(1));
                    Double totalPrice = 0.0;
                    for (CartItem cartItem: v.getValue()){
                        totalPrice += cartItem.getQuantity() * cartItem.getItemPost().getUnitPrice();
                    }
                    orderAgency.setTotalPrice(totalPrice);
                    try {
                        Integer payShipType;
                        Integer amountCOD;
                        if (paymentType.getId() == 1){
                            payShipType = 2;
                            amountCOD = (int)(double) totalPrice;
                        }else {
                            payShipType = 1;
                            amountCOD = 0;
                        }
                        Agency agencyShip = this.agencyService.getAgencyByID(v.getKey());
                        Map<Object, Object> createOrder = this.orderTrackingService.createOrderOfGHNExpress(payShipType, agencyShip, customerAddressBook, v.getValue(), serviceID, serviceTypeID, amountCOD);
                        if (String.valueOf(createOrder.get("code")).equals("200")){
                            Map<Object, Object> createOrderTemp= (Map<Object,Object>) createOrder.get("data");
                            System.out.println(createOrderTemp.toString());
                            orderAgency.setOrderExpressID(createOrderTemp.get("order_code").toString());
                            orderAgency.setShipFee(Double.parseDouble(createOrderTemp.get("total_fee").toString()));
                            orderAgency.setExpectedDeliveryTime(createOrderTemp.get("expected_delivery_time").toString());
                            System.out.println(orderAgency.getOrderExpressID());
                        }else {
                            this.utils.saveLogError("ERROR_CREATE_ORDER_OF_GHN_EXPRESS",
                                    String.format("Hóa đơn: %d\nChi tiết sự cố: %s\n\nThông tin người giao hàng: %s\nThông tin người nhận hàng: %s\n",
                                    orders.getId(),
                                    createOrder.get("message").toString(),
                                    orderAgency.getAgency().toString(),
                                    customerAddressBook.toString()));
                        }
                    }catch (Exception exception){
                        System.out.println(exception.getMessage());
                    }
                    this.ordersAgencyRepository.save(orderAgency);
                    for (CartItem cartItem: v.getValue()){
                        OrderDetail orderDetail = new OrderDetail();
                        orderDetail.setQuantity(cartItem.getQuantity());
                        orderDetail.setItemPost(cartItem.getItemPost());
                        orderDetail.setOrderAgency(orderAgency);
                        this.orderDetailRepository.save(orderDetail);
                        int newQty = cartItem.getItemPost().getInventory() - cartItem.getQuantity();
                        cartItem.getItemPost().setInventory(newQty);
                        this.itemRepository.save(cartItem.getItemPost());
                    }
                }
                String mailTo = user.getEmail();
                String subject = "Thank you for shopping at OU ecommerce";
                String title = String.format("Dear %s,", user.getUsername());
                String content = "We have received your order";
                String mailTemplate = "mail";
                String items = this.utils.customMailForPayment(cartItemList);
                this.mailService.sendMail(mailTo,subject,title,content,items,mailTemplate);
                for (CartItem cartItem: cartItemList){
                    this.cartItemRepository.deleteById(cartItem.getId());
                }
            }else {
                throw new RuntimeException("Cart is empty");
            }
            return this.cartItemRepository.findByCart(cart).stream().toList();
        }catch (Exception ex){
            String error_ms = ex.getMessage();
            throw new RuntimeException(error_ms);
        }
    }

    @Override
    public List<Object> getCheckOutPayment(User user) throws RuntimeException{
        try {
            List<Object> mapResult = new ArrayList<>();
            Cart cart;
            if (this.cartRepository.existsByAuthor(user)){
                cart = this.cartRepository.findByAuthor(user).get(0);
            }else {
                throw new RuntimeException("Card not found");
            }
            List<CartItem> cartItemList = this.cartItemRepository.findByCart(cart).stream().toList();
            for (CartItem cartItem: cartItemList){
                if (cartItem.getItemPost().getInventory() < cartItem.getQuantity()){
                    throw new RuntimeException(String.format("Item %s not enough quantity in stock",cartItem.getItemPost().getName()));
                }
            }
            if (cartItemList.size() > 0){
                Map<Integer, List<CartItem>> groupItemByAgency =
                        cartItemList.stream().collect(Collectors.groupingBy(item -> item.getItemPost().getSalePost().getAgency().getId()));
                for (Map.Entry<Integer,  List<CartItem>> v : groupItemByAgency.entrySet()) {
                    Map<Object, Object> temp = new HashMap<>();
                    temp.put("agencyID", v.getKey());
                    temp.put("cartItems", v.getValue());
                    Double totalPrice = 0.0;
                    for (CartItem cartItem: v.getValue()){
                        totalPrice += cartItem.getQuantity() * cartItem.getItemPost().getUnitPrice();
                    }
                    temp.put("calculatorPrice", totalPrice);
                    mapResult.add(temp);
                }
            }
            return mapResult;
        }catch (Exception ex){
            String error_ms = ex.getMessage();
            throw new RuntimeException(error_ms);
        }
    }

    @Override
    public Map<String, String> getMomoPaymentInfo(User user, Double shipFee) throws RuntimeException{
        Map<String, String> result = new HashMap<>();
        try {
            Map<String, String> res = null;
            Cart cart;
            String orderInfo = "Pay bill on e-commerce OU";
            if (this.cartRepository.existsByAuthor(user)){
                cart = this.cartRepository.findByAuthor(user).get(0);
            }else {
                cart = new Cart();
                cart.setAuthor(user);
                this.cartRepository.save(cart);
            }
            List<CartItem> cartItemList = this.cartItemRepository.findByCart(cart).stream().toList();
            for (CartItem cartItem: cartItemList){
                if (cartItem.getItemPost().getInventory() < cartItem.getQuantity()){
                    throw new RuntimeException(String.format("Item %s not enough quantity in stock",cartItem.getItemPost().getName()));
                }
            }
            if (cartItemList.size() > 0){
                String id = this.utils.generateUUID();
                int amount = (int)(double)(getTotalPriceInCart(cart) +  shipFee);
                String amountStr = String.valueOf(amount);
                System.out.println(amountStr);
                res = this.momoPaymentUtils.getPaymentInfo(id, amountStr, orderInfo, id);
                System.out.println(res);
                if (String.valueOf(res.get("resultCode")).equals("0")){
                    result.put("payUrl", res.get("payUrl"));
                    result.put("message", "Create payment info successfully!!!");
                }
                else {
                    result.put("payUrl", null);
                    result.put("message", "Create payment info failed !!!");
                }
            }else {
                throw new RuntimeException("Cart is empty");
            }
            return result;
        }catch (Exception ex){
            String error_ms = ex.getMessage();
            throw new RuntimeException(error_ms);
        }
    }

    @Override
    public Cart getCartByUser(User user) throws RuntimeException{
        try {
            Cart cart;
            if (this.cartRepository.existsByAuthor(user)){
                cart = this.cartRepository.findByAuthor(user).get(0);
            }else {
                cart = new Cart();
                cart.setAuthor(user);
                this.cartRepository.save(cart);
            }
            return cart;
        }catch (Exception ex){
            String error_ms = ex.getMessage();
            throw new RuntimeException(error_ms);
        }
    }

    @Override
    public Double getTotalPriceInCart(Cart cart) throws RuntimeException{
        try {
            double totalPrice = 0;
            List<CartItem> cartItemList = this.cartItemRepository.findByCart(cart).stream().toList();
            for (CartItem cartItem: cartItemList){
               totalPrice += cartItem.getQuantity() * cartItem.getItemPost().getUnitPrice();
            }
            return totalPrice;
        }catch (Exception ex){
            String error_ms = ex.getMessage();
            throw new RuntimeException(error_ms);
        }
    }
}
