package com.thunv.ecommerceou.validator;

import com.thunv.ecommerceou.dto.ChangePasswordDTO;
import com.thunv.ecommerceou.dto.UserRegisterDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import javax.validation.ConstraintViolation;
import java.util.Set;

public class CommonChangePasswordDTOValidator implements Validator {
    @Autowired
    private javax.validation.Validator beanValidator;
    Set<Validator> springValidators;

    public void setSpringValidators(Set<Validator> springValidators) {
        this.springValidators = springValidators;
    }
    @Override
    public boolean supports(Class<?> clazz) {
        return ChangePasswordDTO.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Set<ConstraintViolation<Object>> beans = this.beanValidator.validate(target);
        for (ConstraintViolation<Object> obj : beans) {
            errors.rejectValue(obj.getPropertyPath().toString(), obj.getMessageTemplate(), obj.getMessage());
        }
        for (Validator v : this.springValidators) {
            v.validate(target, errors);
        }
    }
}
