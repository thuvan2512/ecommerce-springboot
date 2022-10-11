package com.thunv.ecommerceou.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.thunv.ecommerceou.utils.Utils;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Min;
import java.text.SimpleDateFormat;
import java.util.Date;

public class SearchSalePostDTO {
    @Getter @Setter
    @Min(value = 0,message = "Page must be greater than or equal to 0")
    private int page = 0;
    @Getter @Setter
    private String kw;
    @Getter @Setter
    @Min(value = 0,message = "Price can not be negative ")
    private Double fromPrice;
    @Getter @Setter
    @Min(value = 0,message = "Price can not be negative ")
    private Double toPrice;
    @Getter @Setter
    private Integer categoryID;
    @Getter @Setter
    private String nameOfAgency;
    @Getter @Setter
    private String fromDate;
    @Getter @Setter
    private String toDate;

}
