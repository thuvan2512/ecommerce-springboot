package com.thunv.ecommerceou.repositories.custom;

import com.thunv.ecommerceou.dto.SearchSalePostDTO;
import com.thunv.ecommerceou.models.pojo.Agency;
import com.thunv.ecommerceou.models.pojo.SalePost;
import com.thunv.ecommerceou.models.pojo.User;

import java.util.List;

public interface SalePostRepositoryCustom {
    List<SalePost> searchSalePost(SearchSalePostDTO searchSalePostDTO);
    List<SalePost> getListSalePostLikeByUser(User user);
    List<SalePost> getListSalePostUnpublished(Agency agency);
    List<SalePost> getListSalePostPublished(Agency agency);

}
