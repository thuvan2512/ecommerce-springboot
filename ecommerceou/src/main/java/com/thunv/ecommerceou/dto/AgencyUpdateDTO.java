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

    @Getter @Setter
    private Integer isUpdateLocation = 0;

    @Getter @Setter
    private String fromProvinceName;

    @Getter @Setter
    private Integer provinceID;

    @Getter @Setter
    private String fromDistrictName;

    @Getter @Setter
    private Integer districtID;

    @Getter @Setter
    private String fromWardName;

    @Getter @Setter
    private String wardID;

    @Getter @Setter
    private String fromAddress;
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
        if (this.getIsUpdateLocation() == 1){
            if (this.getFromAddress() != null && this.getFromProvinceName() != null && this.getFromDistrictName() != null
            && this.getFromWardName() != null && this.getWardID() != null && this.getDistrictID() != null && this.getProvinceID() != null){
                agency.setFromProvinceName(this.getFromProvinceName());
                agency.setProvinceID(this.getProvinceID());
                agency.setFromDistrictName(this.getFromDistrictName());
                agency.setDistrictID(this.getDistrictID());
                agency.setFromWardName(this.getFromWardName());
                agency.setWardID(this.getWardID());
                agency.setFromAddress(this.getFromAddress());
            }
        }
        return agency;
    }
}
