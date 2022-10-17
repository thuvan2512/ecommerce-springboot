package com.thunv.ecommerceou.services;


import com.thunv.ecommerceou.dto.SalePostCreateDTO;
import com.thunv.ecommerceou.dto.SalePostUpdateDTO;
import com.thunv.ecommerceou.dto.SearchSalePostDTO;
import com.thunv.ecommerceou.models.pojo.Agency;
import com.thunv.ecommerceou.models.pojo.SalePost;
import com.thunv.ecommerceou.models.pojo.User;

import java.util.List;

public interface SalePostService {
    SalePost getSalePostByID(int postID);
    List<SalePost> getAllSalePost();
    List<SalePost> searchSalePost(SearchSalePostDTO searchSalePostDTO);
    List<SalePost> getListSalePostLikeByUser(User user);
    List<SalePost> getListSalePostUnpublished(Agency agency);
    List<SalePost> getListSalePostPublished(Agency agency);
    SalePost publishSalePost(SalePost salePost);
    SalePost unPublishSalePost(SalePost salePost);
    Integer countSalePost();
    SalePost createSalePost(SalePostCreateDTO salePostCreateDTO);
    SalePost updateSalePost(SalePost salePost);
    boolean deleteSalePost(int postID);
    List<Object[]> getTopSeller(int top);
    Integer getTotalPageSalePost();
    List<Object[]> getStatsSalePostByCategory(Agency agency);
    List<Object[]> getStatsSalePostByCategory();
    List<Object[]> getStatsRevenueMonthByYear(Agency agency, int year);
    List<Object[]> getStatsRevenueQuarterByYear(Agency agency,int year);
    List<Object[]> getStatsRevenueYear(Agency agency);

}
