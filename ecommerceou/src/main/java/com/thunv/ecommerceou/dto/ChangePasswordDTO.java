package com.thunv.ecommerceou.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class ChangePasswordDTO {
    @Setter
    @Getter
    @NotNull(message = "Password can not be null")
    @Size(min = 1,max = 16,message = "Must be between 1 and 16 characters")
    private String password;
    @Setter @Getter
    @NotNull(message = "Re password can not be null")
    @Size(min = 1,max = 16,message = "Must be between 1 and 16 characters")
    private String rePassword;
}
