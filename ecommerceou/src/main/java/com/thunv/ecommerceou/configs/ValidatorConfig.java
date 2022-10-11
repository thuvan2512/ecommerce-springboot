package com.thunv.ecommerceou.configs;

import com.thunv.ecommerceou.services.UserService;
import com.thunv.ecommerceou.validator.CommonChangePasswordDTOValidator;
import com.thunv.ecommerceou.validator.CommonUserDTOValidator;
import com.thunv.ecommerceou.validator.userdto.ConfirmPasswordValidator;
import com.thunv.ecommerceou.validator.userdto.ExistEmailValidator;
import com.thunv.ecommerceou.validator.userdto.ExistUsernameValidator;
import com.thunv.ecommerceou.validator.userdto.MatchPasswordDTOValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.Validator;

import java.util.HashSet;
import java.util.Set;

@Configuration
public class ValidatorConfig {
    @Autowired
    private UserService userService;
    @Bean
    public CommonUserDTOValidator userDTOValidator(){
        Set<Validator> springValidator = new HashSet<>();
        ExistEmailValidator existEmailValidator = new ExistEmailValidator();
        existEmailValidator.setUserService(this.userService);
        ExistUsernameValidator existUsernameValidator = new ExistUsernameValidator();
        existUsernameValidator.setUserService(this.userService);
        springValidator.add(new ConfirmPasswordValidator());
        springValidator.add(existEmailValidator);
        springValidator.add(existUsernameValidator);
        CommonUserDTOValidator userDTOValidator = new CommonUserDTOValidator();
        userDTOValidator.setSpringValidators(springValidator);
        return userDTOValidator;
    }
    @Bean
    public CommonChangePasswordDTOValidator commonChangePasswordDTOValidator(){
        Set<Validator> springValidator = new HashSet<>();
        springValidator.add(new MatchPasswordDTOValidator());
        CommonChangePasswordDTOValidator changePasswordDTOValidator = new CommonChangePasswordDTOValidator();
        changePasswordDTOValidator.setSpringValidators(springValidator);
        return changePasswordDTOValidator;
    }
}
