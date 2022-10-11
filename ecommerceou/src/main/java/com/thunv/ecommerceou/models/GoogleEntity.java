package com.thunv.ecommerceou.models;

import lombok.Getter;
import lombok.Setter;

public class GoogleEntity {
    @Getter @Setter
    private String id;
    @Getter @Setter
    private String email;
    @Getter @Setter
    private boolean verified_email;
    @Getter @Setter
    private String name;
    @Getter @Setter
    private String given_name;
    @Getter @Setter
    private String family_name;
    @Getter @Setter
    private String link;
    @Getter @Setter
    private String hd;
    @Getter @Setter
    private String picture;
}
