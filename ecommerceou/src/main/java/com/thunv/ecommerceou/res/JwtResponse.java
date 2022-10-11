package com.thunv.ecommerceou.res;

import lombok.Getter;

import java.io.Serializable;
import java.util.Date;

public class JwtResponse implements Serializable {
    private static final long serialVersionUID = -8091879091924046844L;
    @Getter
    private final String token;
    @Getter
    private final String expirationDate;

    public JwtResponse(String jwttoken, String expirationDate) {
        this.token = jwttoken;
        this.expirationDate = expirationDate;
    }

}
