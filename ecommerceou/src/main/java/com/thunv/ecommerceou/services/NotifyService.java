package com.thunv.ecommerceou.services;

import com.thunv.ecommerceou.models.NotificationEntity;
import com.thunv.ecommerceou.models.pojo.FollowAgency;

import java.util.List;

public interface NotifyService {
    void pushNotify(String recipientID, String image, String title, String details, String type);

    void pushListFollowNotifyForUser(List<FollowAgency> followAgencyList, String titleSalePost);
    void updateSeenStatusOfNotify(String recipientID);
}
