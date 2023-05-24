package com.thunv.ecommerceou.services;

import com.thunv.ecommerceou.models.pojo.Agency;
import com.thunv.ecommerceou.models.pojo.LikePost;
import com.thunv.ecommerceou.models.pojo.SalePost;
import com.thunv.ecommerceou.models.pojo.User;

import java.util.List;

public interface LikePostService {
    LikePost createLikePost(User user, SalePost salePost);
    boolean getLikeStatePostByUser(User user, SalePost salePost);

    int countLikeByPost(SalePost salePost);

    int countLikeByAgency(Agency agency);
}
