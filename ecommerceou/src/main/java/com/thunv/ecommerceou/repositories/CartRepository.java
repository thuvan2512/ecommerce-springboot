package com.thunv.ecommerceou.repositories;

import com.thunv.ecommerceou.models.pojo.Cart;
import com.thunv.ecommerceou.models.pojo.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CartRepository extends JpaRepository<Cart,Integer> {
    boolean existsByAuthor(User user);
    List<Cart> findByAuthor(User user);
}
