package com.thunv.ecommerceou.services;

import com.thunv.ecommerceou.models.pojo.Agency;
import com.thunv.ecommerceou.models.pojo.PromotionCode;
import com.thunv.ecommerceou.models.pojo.PromotionProgram;
import com.thunv.ecommerceou.models.pojo.User;

import java.util.List;
import java.util.Map;

public interface PromotionService {
    PromotionProgram getPromotionProgramByID(Integer id);
    PromotionCode getPromotionCodeByID(String id);
    List<PromotionCode> getListPublishPromotionCodeByProgramID(PromotionProgram promotionProgram);
    List<PromotionCode> getAllPromotionCodeByProgramID(PromotionProgram promotionProgram);
    List<PromotionProgram> getListProgramByListAgencyID(List<Integer> listAgencyID, Integer top);
    List<PromotionProgram> getALLProgramByAgency(Agency agency);
    List<PromotionProgram> getAllPublishProgram();
    PromotionProgram createPromotionProgram(PromotionProgram promotionProgram);
    boolean checkExistCode(String code);
    PromotionCode createPromotionCode(PromotionCode promotionCode);

    Object previewDiscountByVoucher(User user, PromotionCode voucherCode);
    PromotionCode checkAvailablePromotionCode(String promotionCode);

    PromotionProgram getProgramByPromotionCode(String promotionCode);

    void useVoucher(String promotionCode);
}
