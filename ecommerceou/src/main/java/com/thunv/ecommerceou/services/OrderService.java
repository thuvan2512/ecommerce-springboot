package com.thunv.ecommerceou.services;

import com.thunv.ecommerceou.models.pojo.*;

import java.util.List;
import java.util.Set;

public interface OrderService {
    OrderAgency getOrderAgencyByID(int orderAgencyID);

    List<OrderAgency> getListOrderAgencyByAgency(Agency agency);

    List<OrderAgency> getListOrderAgencyByUser(User user);

    List<Orders> getListOrderByUser(User user);

    List<OrderDetail> getListOrderDetailByOrderAgency(OrderAgency orderAgency);

    OrderAgency updateStateOfOrdersAgency(OrderAgency orderAgency, OrderState orderState);

    boolean returnOldQuantityAfterCancelOrder(List<OrderDetail> orderDetailList);
}