package com.thunv.ecommerceou.repositories.custom;

import com.thunv.ecommerceou.models.pojo.OrderAgency;
import com.thunv.ecommerceou.models.pojo.User;

import java.util.List;

public interface OrdersAgencyRepositoryCustom {
    List<OrderAgency> getListOrderAgencyByUser(User user);
}
