package com.thunv.ecommerceou.repositories;

import com.thunv.ecommerceou.models.pojo.OrderState;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderStateRepository extends JpaRepository<OrderState,Integer> {
}
