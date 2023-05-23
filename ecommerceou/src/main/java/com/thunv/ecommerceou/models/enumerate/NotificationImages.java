package com.thunv.ecommerceou.models.enumerate;

public enum NotificationImages {
    ORDER_TRACKING, BAN_AGENCY, UNBAN_AGENCY, APP_LOGO;
    public String getValue(){
        switch (this){
            case ORDER_TRACKING:
                return "https://res.cloudinary.com/ngnohieu/image/upload/v1681832337/2Asset_1_rb8300.png";
            case BAN_AGENCY:
                return "https://res.cloudinary.com/dec25/image/upload/v1661924163/store_dqiefo.png";
            case UNBAN_AGENCY:
                return "https://res.cloudinary.com/dec25/image/upload/v1661924163/store_dqiefo.png";
            case APP_LOGO:
                return "https://res.cloudinary.com/dec25/image/upload/v1683117159/avtArtboard_18_2x-100_l1mifl.jpg";
            default:
                return "https://res.cloudinary.com/dec25/image/upload/v1661924163/categories_mprlqi.png";
        }
    }
}
