package com.thunv.ecommerceou.repositories;

import com.thunv.ecommerceou.models.pojo.Orders;
import com.thunv.ecommerceou.models.pojo.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrdersRepository extends JpaRepository<Orders,Integer> {
    List<Orders> findByAuthor(User user);
}
