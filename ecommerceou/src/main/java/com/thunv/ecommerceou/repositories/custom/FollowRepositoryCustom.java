package com.thunv.ecommerceou.repositories.custom;


import com.thunv.ecommerceou.models.pojo.Agency;
import com.thunv.ecommerceou.models.pojo.FollowAgency;
import com.thunv.ecommerceou.models.pojo.SalePost;
import com.thunv.ecommerceou.models.pojo.User;

public interface FollowRepositoryCustom {
    Boolean checkExistFollow(User user, Agency agency);
    FollowAgency getFollowExist(User user, Agency agency);
    int countFollowByPost(Agency agency);


}
