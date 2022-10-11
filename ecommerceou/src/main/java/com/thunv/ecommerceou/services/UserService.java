package com.thunv.ecommerceou.services;

import com.thunv.ecommerceou.dto.UserRegisterDTO;
import com.thunv.ecommerceou.models.pojo.User;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UserService extends UserDetailsService {
    List<User> getUserByUsername(String username);
    List<User> loadUserByEmail(String email);
    List<User> getAllUser();
    User getUserByID(int userID);
    User registerUser(UserRegisterDTO userRegisterDTO);
    User updateUser(User user);
    boolean deleteUser(int userID);
    boolean checkExistUsername(String username);
    boolean checkExistEmail(String email);
}
