package com.thunv.ecommerceou.repositories.custom;

import com.thunv.ecommerceou.models.pojo.Agency;
import com.thunv.ecommerceou.models.pojo.LikePost;
import com.thunv.ecommerceou.models.pojo.SalePost;
import com.thunv.ecommerceou.models.pojo.User;

import java.util.List;

public interface LikePostRepositoryCustom {
    Boolean checkExistLikePost(User user,SalePost salePost);
    LikePost getLikePostExist(User user,SalePost salePost);
    int countLikeByPost(SalePost salePost);

    int countLikeByAgency(Agency agency);
}
