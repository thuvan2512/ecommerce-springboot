package com.thunv.ecommerceou.dto;

import com.thunv.ecommerceou.models.pojo.PromotionCode;
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

public class PromotionCodeDTO {

    @Getter
    @Setter
    private String endUsableDate;

    @Getter
    @Setter
    @NotNull(message = "totalRelease can not be null")
    @Min(value = 1)
    private Integer totalRelease;

    @Getter
    @Setter
    @Size(min = 1,max = 30,message = "Must be between 1 and 30 characters")
    private String prefix;

    public PromotionCode loadPromotionCode(PromotionCode promotionCode) throws ParseException, RuntimeException {
        if (this.getEndUsableDate() != null){
            DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
            Date endUsableDate = formatter.parse(this.getEndUsableDate());
            if (endUsableDate.after(promotionCode.getProgram().getEndUsable()) || endUsableDate.before(promotionCode.getProgram().getBeginUsable())){
                throw new RuntimeException("code duration must be within program duration !!!");
            }
            promotionCode.setEndUsableDate(endUsableDate);
        }else {
            promotionCode.setEndUsableDate(promotionCode.getProgram().getEndUsable());
        }

        promotionCode.setTotalRelease(this.getTotalRelease());
        promotionCode.setTotalCurrent(this.getTotalRelease());
        promotionCode.setTotalUse(0);
        return promotionCode;
    }
}
