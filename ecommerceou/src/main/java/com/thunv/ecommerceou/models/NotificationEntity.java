package com.thunv.ecommerceou.models;

import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;
import java.util.Date;
import java.util.UUID;


public class NotificationEntity {
    @Setter @Getter
    private String image;

    @Setter @Getter
    private String title;

    @Setter @Getter
    private String details;

    @Setter @Getter
    private String type;

    @Setter @Getter
    private boolean seen = false;

    @Setter @Getter
    private Timestamp createdDate = new Timestamp(System.currentTimeMillis());;

}
