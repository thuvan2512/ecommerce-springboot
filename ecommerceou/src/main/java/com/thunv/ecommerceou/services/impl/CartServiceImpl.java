package com.thunv.ecommerceou.services.impl;

import com.thunv.ecommerceou.models.pojo.*;
import com.thunv.ecommerceou.repositories.*;
import com.thunv.ecommerceou.services.*;
import com.thunv.ecommerceou.utils.MomoPaymentUtils;
import com.thunv.ecommerceou.utils.Utils;
import io.swagger.models.auth.In;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class CartServiceImpl implements CartService {
    @Autowired
    private CartRepository cartRepository;
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
    public List<CartItem> paymentCart(User user, PaymentType paymentType) throws RuntimeException{
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
                    orderAgency.setAgency(this.agencyService.getAgencyByID(v.getKey()));
                    orderAgency.setOrders(orders);
                    orderAgency.setOrderState(this.orderStateService.getOrderStateByID(1));
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
    public Map<String, String> getMomoPaymentInfo(User user) throws RuntimeException{
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
                int amount = (int)((double) getTotalPriceInCart(cart));
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
