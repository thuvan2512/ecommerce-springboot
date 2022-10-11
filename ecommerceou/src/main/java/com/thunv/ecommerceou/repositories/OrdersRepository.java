package com.thunv.ecommerceou.repositories;

import com.thunv.ecommerceou.models.pojo.Orders;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrdersRepository extends JpaRepository<Orders,Integer> {
}
