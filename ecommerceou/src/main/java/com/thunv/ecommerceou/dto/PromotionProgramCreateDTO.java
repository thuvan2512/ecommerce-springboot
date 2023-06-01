package com.thunv.ecommerceou.dto;

import com.thunv.ecommerceou.models.enumerate.ReductionType;
import com.thunv.ecommerceou.models.pojo.PromotionProgram;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class PromotionProgramCreateDTO {

    @Getter @Setter
    @NotNull(message = "reductionType can not be null")
    private ReductionType reductionType;

    @Getter @Setter
    @NotNull(message = "beginUsable can not be null")
    private String beginUsable;

    @Getter @Setter
    @NotNull(message = "endUsable can not be null")
    private String endUsable;

    @Getter @Setter
    @NotNull(message = "percentageReduction can not be null")
    @Min(value = 1)
    @Max(value = 100)
    private Integer percentageReduction;

    @Getter @Setter
    @NotNull(message = "reductionAmountMax can not be null")
    private Integer reductionAmountMax;

    @Getter @Setter
    @NotNull(message = "avatar can not be null")
    private String avatar;

    @Getter @Setter
    @NotNull(message = "programName can not be null")
    @Size(min = 1, max = 200,message = "Must be between 1 and 200 characters")
    private String programName;

    @Getter @Setter
    @Size(min = 1, max = 200,message = "Must be between 1 and 200 characters")
    @NotNull(message = "programTitle can not be null")
    private String programTitle;

    @Getter @Setter
    @NotNull(message = "description can not be null")
    private String description;

    @Getter @Setter
    @NotNull(message = "available_sku can not be null")
    private String availableSku;

    public PromotionProgram loadPromotionProgram(PromotionProgram promotionProgram) throws ParseException, RuntimeException {
        DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        Date beginUsableDate = formatter.parse(this.getBeginUsable());
        Date endUsableDate = formatter.parse(this.getEndUsable());
        if (beginUsableDate.after(endUsableDate)){
            throw new RuntimeException("begin date and end date is invalid !!!");
        }
        promotionProgram.setBeginUsable(beginUsableDate);
        promotionProgram.setEndUsable(endUsableDate);
        Integer type = this.getReductionType().getValue();
        promotionProgram.setReductionType(type);
        promotionProgram.setProgramName(this.getProgramName());
        promotionProgram.setProgramTitle(this.getProgramTitle());
        promotionProgram.setDescription(this.getDescription());
        promotionProgram.setAvatar(this.getAvatar());
        promotionProgram.setAvailableSku((this.getAvailableSku().strip().isBlank() ? "ALL": this.getAvailableSku()));
        promotionProgram.setPercentageReduction(this.getPercentageReduction());
        promotionProgram.setReductionAmountMax(this.getReductionAmountMax());
        return promotionProgram;
    }
}
