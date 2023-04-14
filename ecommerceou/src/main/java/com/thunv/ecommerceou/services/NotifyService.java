package com.thunv.ecommerceou.services;

import com.thunv.ecommerceou.models.NotificationEntity;

public interface NotifyService {
    String pushNotify(String recipientID, String image, String title, String details, String type);
    void updateSeenStatusOfNotify(String recipientID);
}
