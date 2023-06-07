package com.thunv.ecommerceou.services;

import com.thunv.ecommerceou.models.NotificationEntity;
import com.thunv.ecommerceou.models.pojo.Agency;
import com.thunv.ecommerceou.models.pojo.FollowAgency;
import com.thunv.ecommerceou.models.pojo.PromotionProgram;

import java.util.List;

public interface NotifyService {
    void pushNotify(String recipientID, String image, String title, String details, String type);

    void pushListFollowNotifyForUser(List<FollowAgency> followAgencyList, String titleSalePost);

    void pushListFollowNotifyPromotionForUser(List<FollowAgency> followAgencyList, PromotionProgram promotionProgram);
    void pushListBanAgencyNotifyForManager(List<Agency> agencyList);
    void updateSeenStatusOfNotify(String recipientID);
    void pushListNotifyRemindAgency(List<Agency> agencyList);
}
