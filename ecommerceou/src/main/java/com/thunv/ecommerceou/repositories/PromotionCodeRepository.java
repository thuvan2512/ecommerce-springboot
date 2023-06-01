package com.thunv.ecommerceou.repositories;

import com.thunv.ecommerceou.models.pojo.PromotionCode;
import com.thunv.ecommerceou.repositories.custom.PromotionCodeRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface PromotionCodeRepository extends JpaRepository<PromotionCode, String>, PromotionCodeRepositoryCustom {
    List<PromotionCode> findByCode(String code);
}
