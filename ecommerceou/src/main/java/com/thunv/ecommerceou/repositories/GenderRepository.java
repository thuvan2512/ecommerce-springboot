package com.thunv.ecommerceou.repositories;

import com.thunv.ecommerceou.models.pojo.Gender;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GenderRepository extends JpaRepository<Gender,Integer> {
}
