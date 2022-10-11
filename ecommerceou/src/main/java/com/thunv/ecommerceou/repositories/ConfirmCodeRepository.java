package com.thunv.ecommerceou.repositories;

import com.thunv.ecommerceou.models.pojo.ConfirmCode;
import com.thunv.ecommerceou.models.pojo.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ConfirmCodeRepository extends JpaRepository<ConfirmCode,Integer> {
    List<ConfirmCode> findByAuthor(User user);
}
