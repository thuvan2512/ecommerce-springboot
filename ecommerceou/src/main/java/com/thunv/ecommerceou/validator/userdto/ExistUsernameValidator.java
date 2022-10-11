package com.thunv.ecommerceou.validator.userdto;

import com.thunv.ecommerceou.dto.UserRegisterDTO;
import com.thunv.ecommerceou.services.UserService;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

public class ExistUsernameValidator implements Validator {
    private UserService userService;

    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return UserRegisterDTO.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        UserRegisterDTO userRegisterDTO = (UserRegisterDTO) target;
        if (this.userService.getUserByUsername(userRegisterDTO.getUsername()).size() > 0) {
            errors.rejectValue("username", "validate.username.exists");
        }
    }
}
