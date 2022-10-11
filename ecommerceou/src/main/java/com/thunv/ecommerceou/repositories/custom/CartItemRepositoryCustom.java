package com.thunv.ecommerceou.repositories.custom;

import com.thunv.ecommerceou.models.pojo.Cart;
import com.thunv.ecommerceou.models.pojo.CartItem;
import com.thunv.ecommerceou.models.pojo.ItemPost;

import java.util.List;

public interface CartItemRepositoryCustom {
    boolean existsByItemPost(Cart cart, ItemPost itemPost);
    CartItem getItemExistInCart(Cart cart,ItemPost itemPost);
}
