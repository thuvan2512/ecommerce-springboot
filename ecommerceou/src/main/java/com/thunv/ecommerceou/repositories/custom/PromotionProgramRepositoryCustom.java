package com.thunv.ecommerceou.repositories.custom;

import com.thunv.ecommerceou.models.pojo.Agency;
import com.thunv.ecommerceou.models.pojo.PromotionProgram;

import java.util.List;

public interface PromotionProgramRepositoryCustom {
    List<PromotionProgram> getListProgramByListAgencyID(List<Integer> listAgencyID, Integer top);
    List<PromotionProgram> getAllProgramByListAgency(Agency agency);

    List<PromotionProgram> getAllPublishProgram();
}
