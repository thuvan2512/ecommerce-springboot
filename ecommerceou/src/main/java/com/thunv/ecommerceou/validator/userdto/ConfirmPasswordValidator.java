package com.thunv.ecommerceou.validator.userdto;

import com.thunv.ecommerceou.dto.UserRegisterDTO;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

public class ConfirmPasswordValidator implements Validator {
    @Override
    public boolean supports(Class<?> clazz) {
        return UserRegisterDTO.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        UserRegisterDTO userRegisterDTO = (UserRegisterDTO) target;
        if (userRegisterDTO.getRePassword() != null &&
        userRegisterDTO.getRePassword() != null &&
                !userRegisterDTO.getPassword().equals(userRegisterDTO.getRePassword())) {
            errors.rejectValue("rePassword", "validate.rePassword.notMatch");
        }
    }
}
