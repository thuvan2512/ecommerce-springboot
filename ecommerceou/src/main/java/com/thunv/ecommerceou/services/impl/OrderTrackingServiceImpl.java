package com.thunv.ecommerceou.services.impl;

import com.thunv.ecommerceou.models.pojo.*;
import com.thunv.ecommerceou.repositories.CartItemRepository;
import com.thunv.ecommerceou.repositories.CartRepository;
import com.thunv.ecommerceou.services.OrderTrackingService;
import com.thunv.ecommerceou.utils.GHNExpressUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class OrderTrackingServiceImpl implements OrderTrackingService {
    @Autowired
    private GHNExpressUtils ghnExpressUtils;
    @Autowired
    private CartRepository cartRepository;
    @Autowired
    private CartItemRepository cartItemRepository;
    @Override
    public Map<Object, Object> getLocationProvincesOfGHNExpress() throws RuntimeException{
        try {
            Map<Object, Object> results = this.ghnExpressUtils.getLocationProvinceOfGHNExpress();
            return results;
        }catch (Exception exception){
            System.out.println(exception.getMessage());
        }
        return new HashMap<>();
    }

    @Override
    public Map<Object, Object> getLocationDistrictsOfGHNExpress(int provinceID) throws RuntimeException{
        try {
            Map<Object, Object> results = this.ghnExpressUtils.getLocationDistrictsOfGHNExpress(provinceID);
            return results;
        }catch (Exception exception){
            System.out.println(exception.getMessage());
        }
        return new HashMap<>();
    }

    @Override
    public Map<Object, Object> getLocationWardsOfGHNExpress(int districtID) throws RuntimeException{
        try {
            Map<Object, Object> results = this.ghnExpressUtils.getLocationWardsOfGHNExpress(districtID);
            return results;
        }catch (Exception exception){
            System.out.println(exception.getMessage());
        }
        return new HashMap<>();
    }

    @Override
    public Map<Object, Object> generateTokenToPrintOrderOfGHNExpress(String orderCode) throws RuntimeException{
        try {
            Map<Object, Object> results = this.ghnExpressUtils.generateTokenToPrintOrderOfGHNExpress(orderCode);
            return results;
        }catch (Exception exception){
            System.out.println(exception.getMessage());
        }
        return new HashMap<>();
    }

    @Override
    public Map<Object, Object> cancelOrderOfGHNExpress(String orderCode) {
        try {
            Map<Object, Object> results = this.ghnExpressUtils.cancelOrderOfGHNExpress(orderCode);
            return results;
        }catch (Exception exception){
            System.out.println(exception.getMessage());
        }
        return new HashMap<>();
    }

    @Override
    public Map<Object, Object> reviewOrderOfGHNExpress(String orderCode) {
        try {
            Map<Object, Object> results = this.ghnExpressUtils.reviewOrderOfGHNExpress(orderCode);
            return results;
        }catch (Exception exception){
            System.out.println(exception.getMessage());
        }
        return new HashMap<>();
    }

    @Override
    public Map<Object, Object> getPickShiftOfGHNExpress() {
        try {
            Map<Object, Object> results = this.ghnExpressUtils.getPickShiftOfGHNExpress();
            return results;
        }catch (Exception exception){
            System.out.println(exception.getMessage());
        }
        return new HashMap<>();
    }

    @Override
    public Map<Object, Object> setPickShiftOfGHNExpress(String orderCode, Integer pickShiftID) {
        try {
            Map<Object, Object> results = this.ghnExpressUtils.setPickShiftForOrderOfGHNExpress(orderCode, pickShiftID);
            return results;
        }catch (Exception exception){
            System.out.println(exception.getMessage());
        }
        return new HashMap<>();
    }

    @Override
    public Map<Object, Object> getAvailableServiceShippingOfGHNExpress(User user, Agency agency, CustomerAddressBook customerAddressBook) throws RuntimeException{
        try {
            List<Object> mapResult = new ArrayList<>();
            Cart cart;
            if (this.cartRepository.existsByAuthor(user)){
                cart = this.cartRepository.findByAuthor(user).get(0);
            }else {
                throw new RuntimeException("Card not found");
            }
            List<CartItem> cartItemList = this.cartItemRepository.findByCart(cart).stream().toList();
            if (cartItemList.size() > 0){
                Map<Integer, List<CartItem>> groupItemByAgency =
                        cartItemList.stream().collect(Collectors.groupingBy(item -> item.getItemPost().getSalePost().getAgency().getId()));
                List<CartItem> itemList = groupItemByAgency.get(agency.getId());
                if (itemList == null){
                    throw new RuntimeException("Agency not found in order!!!");
                }
                Double totalPrice = 0.0;
                for (CartItem cartItem: itemList){
                    totalPrice += cartItem.getQuantity() * cartItem.getItemPost().getUnitPrice();
                }
                Map<Object, Object> results = this.ghnExpressUtils.getAvailableServiceShippingOfGHNExpress(agency, customerAddressBook, itemList, totalPrice);
                return results;
            }else {
                throw new RuntimeException("Cart is empty !!!");
            }
        }catch (Exception exception){
            throw new RuntimeException(exception.getMessage());
        }
    }

    @Override
    public Map<Object, Object> createOrderOfGHNExpress(Integer paymentTypeOfGHN, Agency agency, CustomerAddressBook customerAddressBook, List<CartItem> listItem, Integer serviceID, Integer serviceTypeID, Integer amountCOD) throws RuntimeException{
        try {
            Map<Object, Object> results = this.ghnExpressUtils.createOrderShipping(paymentTypeOfGHN, agency, customerAddressBook, listItem, serviceID, serviceTypeID, amountCOD);
            return results;
        }catch (Exception exception){
            System.out.println(exception.getMessage());
        }
        return new HashMap<>();
    }
}
