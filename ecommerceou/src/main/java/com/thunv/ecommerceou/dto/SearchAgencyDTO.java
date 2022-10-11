package com.thunv.ecommerceou.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

public class SearchAgencyDTO {
    @Getter @Setter
    @Min(value = 0,message = "Page must be greater than or equal to 0")
    private int page = 0;
    @Getter @Setter
    private String kw;
}
