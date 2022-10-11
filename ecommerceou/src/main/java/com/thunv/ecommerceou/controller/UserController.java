package com.thunv.ecommerceou.controller;

import com.thunv.ecommerceou.dto.*;
import com.thunv.ecommerceou.jwt.JwtTokenUtils;
import com.thunv.ecommerceou.res.ModelResponse;
import com.thunv.ecommerceou.models.pojo.User;
import com.thunv.ecommerceou.services.ConfirmCodeService;
import com.thunv.ecommerceou.services.UserService;
import com.thunv.ecommerceou.utils.Utils;
import com.thunv.ecommerceou.validator.CommonChangePasswordDTOValidator;
import com.thunv.ecommerceou.validator.CommonUserDTOValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/api/user")
public class UserController {
    @Autowired
    private Utils utils;
    @Autowired
    private UserService userService;
    @Autowired
    private CommonUserDTOValidator userDTOValidator;
    @Autowired
    private CommonChangePasswordDTOValidator commonChangePasswordDTOValidator;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private JwtTokenUtils jwtTokenUtils;
    @Autowired
    private ConfirmCodeService confirmCodeService;
    @InitBinder
    public void initBinder(WebDataBinder binder) {
        try {
            if (this.userDTOValidator.supports(binder.getTarget().getClass())) {
                binder.setValidator(this.userDTOValidator);
            }
            if (this.commonChangePasswordDTOValidator.supports(binder.getTarget().getClass())) {
                binder.setValidator(this.commonChangePasswordDTOValidator);
            }
        } catch (Exception e) {
        }
    }
    @GetMapping(path = "/{userID}")
    public ResponseEntity<ModelResponse> getUserByID(@PathVariable(value = "userID") String userID){
        String ms = "Get user successfully";
        String code = "200";
        User user = null;
        HttpStatus status = HttpStatus.OK;
        try {
            user = this.userService.getUserByID(Integer.parseInt(userID));
        }catch (Exception ex){
            ms = ex.getMessage();
            code = "400";
            status = HttpStatus.BAD_REQUEST;
        }
        return ResponseEntity.status(status).body(
                new ModelResponse(code,ms,user)
        );
    }
    @GetMapping(path = "/current-user")
    public ResponseEntity<ModelResponse> getCurrentUser(HttpServletRequest request){
        String ms = "Get current user successfully";
        String code = "200";
        User user = null;
        HttpStatus status = HttpStatus.OK;
        try {
            if (request.getHeader("Authorization") == null){
                throw new RuntimeException("Authorization info not found");
            }
            String token = request.getHeader("Authorization").split("\s")[1];
            List<User> list = this.userService.getUserByUsername(jwtTokenUtils.getUsernameFromToken(token));
            if (list.size() == 0){
                throw new RuntimeException("Can not find current user");
            }
            user = list.get(0);
        }catch (Exception ex){
            ms = ex.getMessage();
            code = "400";
            status = HttpStatus.BAD_REQUEST;
        }
        return ResponseEntity.status(status).body(
                new ModelResponse(code,ms,user)
        );
    }
    @GetMapping(path = "/all")
    public ResponseEntity<ModelResponse> getAllUser(){
        String ms = "Get all users successfully";
        String code = "200";
        List<User> list = null;
        HttpStatus status = HttpStatus.OK;
        try {
            list = this.userService.getAllUser();
        }catch (Exception ex){
            ms = ex.getMessage();
            code = "400";
            status = HttpStatus.BAD_REQUEST;
        }
        return ResponseEntity.status(status).body(
                new ModelResponse(code,ms,list)
        );
    }
    @PostMapping(path = "/register")
    public ResponseEntity<ModelResponse> registerUser(@RequestBody @Valid UserRegisterDTO userRegisterDTO,
                                                           BindingResult result){
        if (result.hasErrors()) {
            Map<String, String> errors = this.utils.getAllErrorValidation(result);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    new ModelResponse("400", "Invalid information", errors)
            );
        }
        String ms = "Register user successfully";
        String code = "201";
        User res = null;
        try {
            res = this.userService.registerUser(userRegisterDTO);
        }catch (Exception ex){
            ms = ex.getMessage();
            code = "400";
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(
                new ModelResponse(code,ms,res)
        );
    }
    @PostMapping(path = "/reset-password")
    public ResponseEntity<ModelResponse> resetPassword(@RequestBody @Valid ResetPasswordDTO resetPasswordDTO,
                                                       BindingResult result){
        if (result.hasErrors()) {
            Map<String, String> errors = this.utils.getAllErrorValidation(result);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    new ModelResponse("400", "Invalid information", errors)
            );
        }
        String ms = "Reset password successfully";
        String code = "200";
        String data = "Check your email inbox to get confirm code";
        try {
            User user;
            List<User> list = this.userService.loadUserByEmail(resetPasswordDTO.getEmail());
            if (list.size() > 0){
                user = list.get(0);
                if (user.getAuthProvider().getId() != 1){
                    throw new RuntimeException("This auth provider does not allow to change the password");
                }
            }else {
                throw new RuntimeException("Can not find user with email = " + resetPasswordDTO.getEmail());
            }
            this.confirmCodeService.createOrUpdateCode(user);
        }catch (Exception ex){
            ms = ex.getMessage();
            code = "400";
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(
                new ModelResponse(code,ms,data)
        );
    }
    @PostMapping(path = "/send-confirm")
    public ResponseEntity<ModelResponse> confirmResetPassword(@RequestBody @Valid SendConfirmDTO sendConfirmDTO,
                                                       BindingResult result){
        if (result.hasErrors()) {
            Map<String, String> errors = this.utils.getAllErrorValidation(result);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    new ModelResponse("400", "Invalid information", errors)
            );
        }
        String ms = "Reset password successfully";
        String code = "200";
        String data = "Password has been changed";
        try {
            User user;
            List<User> list = this.userService.loadUserByEmail(sendConfirmDTO.getEmail());
            if (list.size() > 0){
                user = list.get(0);
                if (user.getAuthProvider().getId() != 1){
                    throw new RuntimeException("This auth provider does not allow to change the password");
                }
            }else {
                throw new RuntimeException("Can not find user with email = " + sendConfirmDTO.getEmail());
            }
            this.confirmCodeService.sendConfirm(user,sendConfirmDTO.getCode());
        }catch (Exception ex){
            ms = ex.getMessage();
            code = "400";
            data = "";
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(
                new ModelResponse(code,ms,data)
        );
    }
    @PutMapping(path = "/{userID}")
    public ResponseEntity<ModelResponse> updateUserInfo(@RequestBody UserUpdateDTO userUpdateDTO,
                                                        @PathVariable(value = "userID") String userID,
                                                        HttpServletRequest request){
        String ms = "Update user info successfully";
        String code = "200";
        User res = null;
        HttpStatus status = HttpStatus.OK;
        try {
            if (request.getHeader("Authorization") == null){
                throw new RuntimeException("Authorization info not found");
            }
            User user = this.userService.getUserByUsername(jwtTokenUtils.getUsernameFromToken(
                    request.getHeader("Authorization")
                            .split("\s")[1])).get(0);
            if (Integer.parseInt(userID) != user.getId()){
                code = "403";
                status = HttpStatus.FORBIDDEN;
                throw new RuntimeException("No owner permission");
            }
            userUpdateDTO.loadUserFromUserDTO(user);
            res = this.userService.updateUser(user);
        }catch (Exception ex){
            ms = ex.getMessage();
            if (code.equals("200")){
                code = "400";
                status = HttpStatus.BAD_REQUEST;

            }
        }
        return ResponseEntity.status(status).body(
                new ModelResponse(code,ms,res)
        );
    }
    @PatchMapping(path = "/change-password/{userID}")
    public ResponseEntity<ModelResponse> changePassword(@RequestBody @Valid ChangePasswordDTO changePasswordDTO,
                                                        @PathVariable(value = "userID") String userID,
                                                        BindingResult result,
                                                        HttpServletRequest request){
        if (result.hasErrors()) {
            Map<String, String> errors = this.utils.getAllErrorValidation(result);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    new ModelResponse("400", "Invalid information", errors)
            );
        }
        String ms = "Change password successfully";
        String code = "200";
        User res = null;
        HttpStatus status = HttpStatus.OK;
        try {
            if (request.getHeader("Authorization") == null){
                throw new RuntimeException("Authorization info not found");
            }
            User user = this.userService.getUserByUsername(jwtTokenUtils.getUsernameFromToken(
                    request.getHeader("Authorization")
                            .split("\s")[1])).get(0);
            if (Integer.parseInt(userID) != user.getId()){
                code = "403";
                status = HttpStatus.FORBIDDEN;
                throw new RuntimeException("No owner permission");
            }
            if (user.getAuthProvider().getId() != 1){
                throw new RuntimeException("This auth provider does not allow to change the password");
            }
            user.setPassword(this.passwordEncoder.encode(changePasswordDTO.getPassword()));
            res = this.userService.updateUser(user);
        }catch (Exception ex){
            ms = ex.getMessage();
            if (code.equals("200")){
                code = "400";
                status = HttpStatus.BAD_REQUEST;
            }
        }
        return ResponseEntity.status(status).body(
                new ModelResponse(code,ms,res)
        );
    }
    @DeleteMapping(path = "/{userID}")
    public ResponseEntity<ModelResponse> deleteUser(@PathVariable(value = "userID") String userID){
        String ms = "Delete user successfully";
        String code = "204";
        HttpStatus status = HttpStatus.OK;
        try {
            this.userService.deleteUser(Integer.parseInt(userID));
        }catch (Exception ex){
            ms = ex.getMessage();
            code = "400";
            status = HttpStatus.BAD_REQUEST;
        }
        return ResponseEntity.status(status).body(
                new ModelResponse(code,ms,null)
        );
    }
}
