package com.thunv.ecommerceou.repositories;

import com.thunv.ecommerceou.models.pojo.PromotionProgram;
import com.thunv.ecommerceou.repositories.custom.PromotionProgramRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PromotionProgramRepository extends JpaRepository<PromotionProgram, Integer>, PromotionProgramRepositoryCustom {
}
