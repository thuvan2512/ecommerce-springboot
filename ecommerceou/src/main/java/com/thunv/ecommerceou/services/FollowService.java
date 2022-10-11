package com.thunv.ecommerceou.services;

import com.thunv.ecommerceou.models.pojo.Agency;
import com.thunv.ecommerceou.models.pojo.FollowAgency;
import com.thunv.ecommerceou.models.pojo.User;

public interface FollowService {
    FollowAgency createFollow(User user, Agency agency);
    boolean getFollowStateAgencyByUser(User user, Agency agency);
}
