package com.thunv.ecommerceou.dto;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

public class JwtDTO implements Serializable {
    private static final long serialVersionUID = 5926468583005150707L;
    @Getter @Setter
    private String username;
    @Getter @Setter
    private String password;

    //need default constructor for JSON Parsing
    public JwtDTO()
    {

    }

    public JwtDTO(String username, String password) {
        this.setUsername(username);
        this.setPassword(password);
    }

}
