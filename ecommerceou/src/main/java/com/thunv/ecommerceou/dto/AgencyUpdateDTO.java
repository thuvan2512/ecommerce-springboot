package com.thunv.ecommerceou.dto;

import com.thunv.ecommerceou.models.pojo.Agency;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

public class AgencyUpdateDTO {
    @Getter @Setter
    private String name;
    @Getter @Setter
    private String avatar;
    @Getter @Setter
    private String coverImage;
    @Getter @Setter
    private String address;
    @Getter @Setter
    private String hotline;
    public Agency loadAgencyFromAgencyDTO(Agency agency){
        if (this.getName() != null){
            agency.setName(this.getName());
        }
        if (this.getAvatar() != null){
            agency.setAvatar(this.getAvatar());
        }
        if (this.getCoverImage() != null){
            agency.setCoverImage(this.getCoverImage());
        }
        if (this.getAddress() != null){
            agency.setAddress(this.getAddress());
        }
        if (this.getHotline() != null){
            agency.setHotline(this.getHotline());
        }
        return agency;
    }
}
