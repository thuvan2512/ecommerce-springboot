package com.thunv.ecommerceou.services;


import com.thunv.ecommerceou.dto.CommentDTO;
import com.thunv.ecommerceou.models.pojo.Agency;
import com.thunv.ecommerceou.models.pojo.CommentPost;
import com.thunv.ecommerceou.models.pojo.PicturePost;
import com.thunv.ecommerceou.models.pojo.SalePost;

import java.util.List;

public interface CommentService {
    CommentPost getCommentPostByID(int commentID);
    List<CommentPost> getAllCommentPost();
    CommentPost createCommentPost(CommentDTO commentDTO);
    boolean deleteCommentPost(int commentID);
    List<CommentPost> getListCommentByPost(SalePost salePost);
    int countCommentByPost(SalePost salePost);
    double getAverageStarByAgency(Agency agency);
}
