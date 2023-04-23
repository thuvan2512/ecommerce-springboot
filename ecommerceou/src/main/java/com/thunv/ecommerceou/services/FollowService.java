package com.thunv.ecommerceou.services;

import com.thunv.ecommerceou.models.pojo.Agency;
import com.thunv.ecommerceou.models.pojo.FollowAgency;
import com.thunv.ecommerceou.models.pojo.User;

import java.util.List;

public interface FollowService {
    FollowAgency createFollow(User user, Agency agency);
    boolean getFollowStateAgencyByUser(User user, Agency agency);

    int countFollowByAgency(Agency agency);

    List<FollowAgency> getListFollowByUser(User user);

    List<FollowAgency> getListFollowByAgency(Agency agency);
}
