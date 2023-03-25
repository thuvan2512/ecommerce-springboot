package com.thunv.ecommerceou.services;

import com.thunv.ecommerceou.models.pojo.*;

import java.util.List;
import java.util.Map;

public interface CartService {
    List<CartItem> addToCart(User user, ItemPost itemPost, int quantity);
    List<CartItem> updateCart(User user,ItemPost itemPost,int quantity);
    List<CartItem> removeItemFromCart(User user,ItemPost itemPost);
    List<CartItem> clearCart(User user);
    List<CartItem> paymentCart(User user, PaymentType paymentType, CustomerAddressBook customerAddressBook, Integer serviceID, Integer serviceTyprID);
    List<Object> getCheckOutPayment(User user);
    Map<String, String> getMomoPaymentInfo(User user, Double shipFee);
    Cart getCartByUser(User user);
    Double getTotalPriceInCart(Cart cart);
}
