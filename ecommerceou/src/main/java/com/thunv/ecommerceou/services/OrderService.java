package com.thunv.ecommerceou.services;

import com.thunv.ecommerceou.models.pojo.*;

import java.util.List;

public interface OrderService {
    OrderAgency getOrderAgencyByID(int orderAgencyID);
    List<OrderAgency> getListOrderAgencyByAgency(Agency agency);
    List<OrderAgency> getListOrderAgencyByUser(User user);
    List<Orders> getListOrderByUser(User user);
    OrderAgency updateStateOfOrdersAgency(OrderAgency orderAgency,OrderState orderState);
}
