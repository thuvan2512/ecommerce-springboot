package com.thunv.ecommerceou.services;


import com.thunv.ecommerceou.models.pojo.OrderState;

import java.util.List;

public interface OrderStateService {
    OrderState getOrderStateByID(int stateID);
    List<OrderState> getAllOrderState();
    OrderState createOrderState(OrderState orderState);
    OrderState updateOrderState(OrderState orderState);
    boolean deleteOrderState(int stateID);
}
