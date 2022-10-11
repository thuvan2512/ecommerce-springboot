package com.thunv.ecommerceou.validator.userdto;

import com.thunv.ecommerceou.dto.ChangePasswordDTO;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
@Component
public class MatchPasswordDTOValidator implements Validator {
    @Override
    public boolean supports(Class<?> clazz) {
        return ChangePasswordDTO.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        ChangePasswordDTO changePasswordDTO = (ChangePasswordDTO) target;
        if (changePasswordDTO.getRePassword() != null && changePasswordDTO.getPassword() != null
                && !changePasswordDTO.getPassword().equals(changePasswordDTO.getRePassword())) {
            errors.rejectValue("rePassword", "validate.rePassword.notMatch");
        }
    }
}
