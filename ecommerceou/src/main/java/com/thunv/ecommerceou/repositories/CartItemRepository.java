package com.thunv.ecommerceou.repositories;

import com.thunv.ecommerceou.models.pojo.Cart;
import com.thunv.ecommerceou.models.pojo.CartItem;
import com.thunv.ecommerceou.models.pojo.ItemPost;
import com.thunv.ecommerceou.models.pojo.User;
import com.thunv.ecommerceou.repositories.custom.CartItemRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CartItemRepository extends JpaRepository<CartItem,Integer>, CartItemRepositoryCustom {
    List<CartItem> findByCart(Cart cart);

}
