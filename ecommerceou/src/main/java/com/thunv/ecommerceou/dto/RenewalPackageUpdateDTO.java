package com.thunv.ecommerceou.dto;

import com.thunv.ecommerceou.models.pojo.RenewalPackage;
import lombok.Getter;
import lombok.Setter;

public class RenewalPackageUpdateDTO {
    @Getter @Setter
    private Integer usualPrice;

    @Getter @Setter
    private Integer discountPrice;


    public RenewalPackage loadRenewalPackage(RenewalPackage renewalPackage){
        if(this.getUsualPrice() != null){
            renewalPackage.setUsualPrice(this.getUsualPrice());
        }
        if(this.getDiscountPrice() != null){
            renewalPackage.setDiscountPrice(this.getDiscountPrice());
        }
        return renewalPackage;
    }
}
