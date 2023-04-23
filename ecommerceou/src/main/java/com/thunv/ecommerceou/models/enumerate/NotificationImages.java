package com.thunv.ecommerceou.models.enumerate;

public enum NotificationImages {
    ORDER_TRACKING, BAN_AGENCY, UNBAN_AGENCY;
    public String getValue(){
        switch (this){
            case ORDER_TRACKING:
                return "https://res.cloudinary.com/ngnohieu/image/upload/v1681832337/2Asset_1_rb8300.png";
            case BAN_AGENCY:
                return "https://res.cloudinary.com/dec25/image/upload/v1661924163/store_dqiefo.png";
            case UNBAN_AGENCY:
                return "https://res.cloudinary.com/dec25/image/upload/v1661924163/store_dqiefo.png";
            default:
                return "https://res.cloudinary.com/dec25/image/upload/v1661924163/categories_mprlqi.png";
        }
    }
}
