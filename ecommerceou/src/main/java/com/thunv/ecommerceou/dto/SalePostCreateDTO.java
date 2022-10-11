package com.thunv.ecommerceou.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.thunv.ecommerceou.models.pojo.Agency;
import com.thunv.ecommerceou.models.pojo.Category;
import com.thunv.ecommerceou.models.pojo.SalePost;
import com.thunv.ecommerceou.models.pojo.SellStatus;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class SalePostCreateDTO {
    @Getter @Setter
    @NotNull(message = "Avatar can not be null")
    private String avatar;
    @Getter @Setter
    @NotNull(message = "Title can not be null")
    @Size(min = 20, max = 200,message = "Must be between 20 and 200 characters")
    private String title;
    @Getter @Setter
    @NotNull(message = "Price can not be null")
    @Min(value = 0,message = "Price can not be negative ")
    private Double finalPrice;
    @Getter @Setter
    @NotNull(message = "Price can not be null")
    @Min(value = 0,message = "Price can not be negative ")
    private Double initialPrice;
    @Getter @Setter
    private String manufacturer;
    @Getter @Setter
    private String origin;
    @Getter @Setter
    private String brand;
    @Getter @Setter
    private String description;
    @Getter @Setter
    @NotNull(message = "Category can not be null")
    private Integer categoryID;
    @Getter @Setter
    @NotNull(message = "Sell status can not be null")
    private Integer sellStatusID;
    @Getter @Setter
    @JsonIgnore
    private Agency agency;
    public SalePost loadSalePostFromDTO(SalePost salePost){
        salePost.setAvatar(this.getAvatar());
        salePost.setTitle(this.getTitle());
        salePost.setFinalPrice(this.getFinalPrice());
        salePost.setInitialPrice(this.getInitialPrice());
        salePost.setManufacturer(this.getManufacturer());
        salePost.setOrigin(this.getOrigin());
        salePost.setBrand(this.getBrand());
        salePost.setDescription(this.getDescription());
        return salePost;
    }
}
