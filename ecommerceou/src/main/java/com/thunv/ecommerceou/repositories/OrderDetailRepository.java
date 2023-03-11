package com.thunv.ecommerceou.repositories;

import com.thunv.ecommerceou.models.pojo.OrderAgency;
import com.thunv.ecommerceou.models.pojo.OrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderDetailRepository extends JpaRepository<OrderDetail,Integer> {
    List<OrderDetail> findByOrderAgency(OrderAgency orderAgency);
}
