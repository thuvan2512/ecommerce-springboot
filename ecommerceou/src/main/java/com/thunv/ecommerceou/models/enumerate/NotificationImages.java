package com.thunv.ecommerceou.models.enumerate;

public enum NotificationImages {
    ORDER_TRACKING, AGENCY_MANAGE;
    public String getValue(){
        switch (this){
            case ORDER_TRACKING:
                return "https://res.cloudinary.com/dec25/image/upload/v1661668526/cardboard-box-brown-vector-graphic-pixabay-2_z3dqeu.png";
            case AGENCY_MANAGE:
                return "https://res.cloudinary.com/dec25/image/upload/v1661924163/store_dqiefo.png";
            default:
                return "https://res.cloudinary.com/dec25/image/upload/v1661924163/categories_mprlqi.png";
        }
    }
}
