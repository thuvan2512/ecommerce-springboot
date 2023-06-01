package com.thunv.ecommerceou.repositories.custom;

import com.thunv.ecommerceou.models.pojo.PromotionCode;
import com.thunv.ecommerceou.models.pojo.PromotionProgram;

import java.util.List;

public interface PromotionCodeRepositoryCustom {
    List<PromotionCode> getListPromotionCodeByProgram(PromotionProgram promotionProgram);

    List<PromotionCode> getListPromotionCodeByProgramForManage(PromotionProgram promotionProgram);
}
