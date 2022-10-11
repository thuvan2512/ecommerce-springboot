package com.thunv.ecommerceou.repositories.custom;

import com.thunv.ecommerceou.models.pojo.Agency;
import com.thunv.ecommerceou.models.pojo.CommentPost;
import com.thunv.ecommerceou.models.pojo.SalePost;

import java.util.List;

public interface CommentRepositoryCustom {
    List<CommentPost> getListCommentByPost(SalePost salePost);
    int countCommentByPost(SalePost salePost);
    double getAverageStarByAgency(Agency agency);
}
