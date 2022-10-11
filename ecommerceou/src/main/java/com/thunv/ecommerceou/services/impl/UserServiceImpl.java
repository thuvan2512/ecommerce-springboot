package com.thunv.ecommerceou.services.impl;

import com.thunv.ecommerceou.dto.UserRegisterDTO;
import com.thunv.ecommerceou.models.pojo.User;
import com.thunv.ecommerceou.repositories.UserRepository;
import com.thunv.ecommerceou.services.AuthProviderService;
import com.thunv.ecommerceou.services.GenderService;
import com.thunv.ecommerceou.services.RoleService;
import com.thunv.ecommerceou.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private GenderService genderService;
    @Autowired
    private AuthProviderService authProviderService;
    @Autowired
    private RoleService roleService;
    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        List<User> users = this.getUserByUsername(s);
        if (users.isEmpty()) {
            throw new UsernameNotFoundException("User does not exist!");
        }
        User user = users.get(0);
        Set<GrantedAuthority> auth = new HashSet<>();
        auth.add(new SimpleGrantedAuthority(user.getRole().getName()));
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), auth);
    }
    @Override
    public List<User> getUserByUsername(String username) {
        return this.userRepository.findByUsername(username);
    }

    @Override
    public List<User> loadUserByEmail(String email) {
        return this.userRepository.findByEmail(email);
    }

    @Override
    public List<User> getAllUser() throws RuntimeException{
        try {
            return this.userRepository.findAll();
        }catch (Exception ex){
            String error_ms = ex.getMessage();
            throw new RuntimeException(error_ms);
        }
    }

    @Override
    public User getUserByID(int userID) throws RuntimeException{
        return this.userRepository.findById(userID).orElseThrow(() ->
                new RuntimeException("Can not find user with id = " + userID));
    }

    @Override
    public User registerUser(UserRegisterDTO userRegisterDTO) throws RuntimeException{
        try {
            User user = new User();
            userRegisterDTO.loadUserFromUserDTO(user);
            user.setJoinedDate(new Date());
            user.setGender(this.genderService.getGenderByID(userRegisterDTO.getGenderID()));
            user.setPassword(this.passwordEncoder.encode(userRegisterDTO.getPassword() ));
            user.setAuthProvider(this.authProviderService.getAuthProviderByID(1));
            user.setRole(this.roleService.getRoleByID(3));
            return this.userRepository.save(user);
        }catch (Exception ex){
            String error_ms = ex.getMessage();
            throw new RuntimeException(error_ms);
        }
    }

    @Override
    public User updateUser(User user) {
        try {
            return this.userRepository.save(user);
        }catch (Exception ex){
            String error_ms = ex.getMessage();
            throw new RuntimeException(error_ms);
        }
    }

    @Override
    public boolean deleteUser(int userID) throws RuntimeException{
        try {
            if (this.userRepository.existsById(userID) == false){
                throw new RuntimeException("User does not exist");
            }
            this.userRepository.deleteById(userID);
            return true;
        }catch (Exception ex){
            String error_ms = ex.getMessage();
            throw new RuntimeException(error_ms);
        }
    }

    @Override
    public boolean checkExistUsername(String username) {
        try {
            List<User> userList = this.getUserByUsername(username);
            if (userList.size() > 0){
                return true;
            }
            return false;
        }catch (Exception ex){
            String error_ms = ex.getMessage();
            throw new RuntimeException(error_ms);
        }
    }

    @Override
    public boolean checkExistEmail(String email) {
        try {
            List<User> userList = this.loadUserByEmail(email);
            if (userList.size() > 0){
                return true;
            }
            return false;
        }catch (Exception ex){
            String error_ms = ex.getMessage();
            throw new RuntimeException(error_ms);
        }
    }

}
