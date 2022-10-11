package com.thunv.ecommerceou.repositories;

import com.thunv.ecommerceou.models.pojo.OrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderDetailRepository extends JpaRepository<OrderDetail,Integer> {
}
