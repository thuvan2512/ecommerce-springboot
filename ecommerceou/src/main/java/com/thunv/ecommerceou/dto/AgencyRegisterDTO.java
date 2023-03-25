package com.thunv.ecommerceou.dto;

import com.thunv.ecommerceou.models.pojo.Agency;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

public class AgencyRegisterDTO {
    @Getter @Setter
    @NotNull(message = "Name can not be null")
    private String name;
    @Getter @Setter
    @NotNull(message = "Avatar can not be null")
    private String avatar;
    @Getter @Setter
    private String coverImage;
    @Getter @Setter
    @NotNull(message = "address can not be null")
    private String address;
    @Getter @Setter
    @NotNull(message = "Hotline can not be null")
//    @Pattern(regexp="^\\d{10}$", message="Invalid phone/fax format, should be as xxx-xxx-xxxx")//if the field contains phone or fax number consider using this annotation to enforce field validation
    private String hotline;
    @Getter @Setter
    @NotNull(message = "Manager can not be null")
    private Integer managerID;
    @Getter @Setter
    @NotNull(message = "Field can not be null")
    private Integer fieldID;

    @NotNull(message = "Can't be not null")
    @Getter @Setter
    private String fromProvinceName;

    @NotNull(message = "Can't be not null")
    @Getter @Setter
    private Integer provinceID;

    @NotNull(message = "Can't be not null")
    @Getter @Setter
    private String fromDistrictName;

    @NotNull(message = "Can't be not null")
    @Getter @Setter
    private Integer districtID;

    @NotNull(message = "Can't be not null")
    @Getter @Setter
    private String fromWardName;

    @NotNull(message = "Can't be not null")
    @Getter @Setter
    private String wardID;

    @NotNull(message = "Can't be not null")
    @Getter @Setter
    private String fromAddress;
    public Agency loadAgencyFromAgencyDTO(Agency agency) throws RuntimeException{
        agency.setName(this.getName());
        agency.setAvatar(this.getAvatar());
        agency.setCoverImage(this.getCoverImage());
        agency.setAddress(this.getAddress());
        agency.setHotline(this.getHotline());
        agency.setFromProvinceName(this.getFromProvinceName());
        agency.setProvinceID(this.getProvinceID());
        agency.setFromDistrictName(this.getFromDistrictName());
        agency.setDistrictID(this.getDistrictID());
        agency.setFromWardName(this.getFromWardName());
        agency.setWardID(this.getWardID());
        agency.setFromAddress(this.getFromAddress());
        return agency;
    }

}
