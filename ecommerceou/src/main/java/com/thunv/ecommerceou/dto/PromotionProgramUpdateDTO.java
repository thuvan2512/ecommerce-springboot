package com.thunv.ecommerceou.dto;

import com.thunv.ecommerceou.models.enumerate.ReductionType;
import com.thunv.ecommerceou.models.pojo.PromotionProgram;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class PromotionProgramUpdateDTO {
    @Getter @Setter
    private String avatar;

    @Getter @Setter
    @Size(min = 1, max = 200,message = "Must be between 1 and 200 characters")
    private String programName;

    @Getter @Setter
    @Size(min = 1, max = 200,message = "Must be between 1 and 200 characters")
    private String programTitle;

    @Getter @Setter
    private String description;

    @Getter @Setter
    private Integer state;

    public PromotionProgram loadPromotionProgram(PromotionProgram promotionProgram){
        if (this.getProgramName() != null){
            promotionProgram.setProgramName(this.getProgramName());
        }
        if (this.getProgramTitle() != null){
            promotionProgram.setProgramTitle(this.getProgramTitle());
        }
        if (this.getDescription() != null){
            promotionProgram.setDescription(this.getDescription());
        }
        if (this.getState() != null){
            promotionProgram.setState(this.getState());
        }
        if (this.getAvatar() != null){
            promotionProgram.setAvatar(this.getAvatar());
        }
        return promotionProgram;
    }
}
