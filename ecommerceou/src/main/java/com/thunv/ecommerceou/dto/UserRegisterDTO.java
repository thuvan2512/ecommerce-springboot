package com.thunv.ecommerceou.dto;

import com.thunv.ecommerceou.models.pojo.User;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class UserRegisterDTO {
    @Getter @Setter
    @NotNull(message = "Avatar can not be null")
    private String avatar;
    @Getter @Setter
    @Size(min = 6, max = 50,message = "Must be between 6 and 100 characters")
    @NotNull(message = "Username can not be null")
    private String username;
    @Setter @Getter
    @NotNull(message = "Password can not be null")
    @Size(min = 1,max = 16,message = "Must be between 1 and 16 characters")
    private String password;
    @Setter @Getter
    @NotNull(message = "Re password can not be null")
    @Size(min = 1,max = 16,message = "Must be between 1 and 16 characters")
    private String rePassword;
    @Getter @Setter
    private String firstName;
    @Getter @Setter
    private String lastName;
    @Getter @Setter
    @Pattern(regexp="[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?", message="Invalid email")
    @NotNull(message = "Email can not be null")
    private String email;
    @Getter @Setter
    private String phone;
    @Getter @Setter
    private String address;
    @Getter @Setter
    @NotNull(message = "Gender can not be null")
    private Integer genderID;
    public User loadUserFromUserDTO(User user) throws RuntimeException{
        user.setAvatar(this.getAvatar());
        user.setUsername(this.getUsername());
        user.setAddress(this.getAddress());
        user.setFirstName(this.getFirstName());
        user.setLastName(this.getLastName());
        user.setPhone(this.getPhone());
        user.setEmail(this.getEmail());
        return user;
    }
}
