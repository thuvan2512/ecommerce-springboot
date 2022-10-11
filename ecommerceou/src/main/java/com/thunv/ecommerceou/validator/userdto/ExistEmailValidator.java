package com.thunv.ecommerceou.validator.userdto;

import com.thunv.ecommerceou.dto.UserRegisterDTO;
import com.thunv.ecommerceou.services.UserService;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

public class ExistEmailValidator implements Validator {
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
        if (this.userService.loadUserByEmail(userRegisterDTO.getEmail()).size() > 0) {
            errors.rejectValue("email", "validate.email.exists");
        }
    }
}
