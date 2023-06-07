package com.thunv.ecommerceou.repositories.custom;

import com.thunv.ecommerceou.dto.SearchSalePostDTO;
import com.thunv.ecommerceou.models.pojo.Agency;
import com.thunv.ecommerceou.models.pojo.SalePost;
import com.thunv.ecommerceou.models.pojo.User;

import java.util.List;

public interface SalePostRepositoryCustom {
    List<SalePost> searchSalePost(SearchSalePostDTO searchSalePostDTO);
    List<Object[]> getTopSeller(int top);

    List<SalePost> getListSalePostByListID(List<Integer> listPostID);

    List<Object[]> getTopSellerByAgency(int top, Agency agency);
    List<Object[]> getStatsInfoOfCommentByPost(SalePost salePost);
    List<SalePost> getListSalePostLikeByUser(User user);
    List<SalePost> getListSalePostUnpublished(Agency agency);
    List<SalePost> getListSalePostPublished(Agency agency);
    List<Object[]> getStatsSalePostByCategoryByAgency(Agency agency);
    List<Object[]> getStatsSalePostByCategory();
    List<Object[]> getStatsRevenueMonthByYear(Agency agency, int year);
    List<Object[]> getStatsRevenueQuarterByYear(Agency agency, int year);
    List<Object[]> getStatsRevenueYear(Agency agency);

}
