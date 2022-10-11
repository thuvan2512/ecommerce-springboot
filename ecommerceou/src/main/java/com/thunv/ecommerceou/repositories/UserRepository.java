package com.thunv.ecommerceou.repositories;

import com.thunv.ecommerceou.models.pojo.User;
import com.thunv.ecommerceou.repositories.custom.UserRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<User,Integer>, UserRepositoryCustom {
    List<User> findByUsername(String username);
    List<User> findByEmail(String username);
}
