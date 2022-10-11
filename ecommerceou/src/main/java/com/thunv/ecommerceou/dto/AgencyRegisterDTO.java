package com.thunv.ecommerceou.dto;

import com.thunv.ecommerceou.models.pojo.Agency;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

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
    private String hotline;
    @Getter @Setter
    @NotNull(message = "Manager can not be null")
    private Integer managerID;
    @Getter @Setter
    @NotNull(message = "Field can not be null")
    private Integer fieldID;
    public Agency loadAgencyFromAgencyDTO(Agency agency) throws RuntimeException{
        agency.setName(this.getName());
        agency.setAvatar(this.getAvatar());
        agency.setCoverImage(this.getCoverImage());
        agency.setAddress(this.getAddress());
        agency.setHotline(this.getHotline());
        return agency;
    }

}
