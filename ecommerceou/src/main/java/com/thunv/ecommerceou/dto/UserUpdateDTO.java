package com.thunv.ecommerceou.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.thunv.ecommerceou.models.pojo.User;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class UserUpdateDTO {
    @Getter @Setter
    private String avatar;
    @Getter @Setter
    private String firstName;
    @Getter @Setter
    private String lastName;
    @Getter @Setter
    private String phone;
    @Getter @Setter
    private String address;
    public User loadUserFromUserDTO(User user) throws RuntimeException{
        if (this.avatar != null){
            user.setAvatar(this.getAvatar());
        }
        if (this.address != null){
            user.setAddress(this.getAddress());
        }
        if (this.firstName != null){
            user.setFirstName(this.getFirstName());
        }
        if (this.lastName!= null){
            user.setLastName(this.getLastName());
        }
        if (this.phone != null){
            user.setPhone(this.getPhone());
        }
        return user;
    }
}
