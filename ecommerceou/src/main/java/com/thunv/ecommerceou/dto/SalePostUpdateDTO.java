package com.thunv.ecommerceou.dto;


import com.thunv.ecommerceou.models.pojo.SalePost;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Min;
import javax.validation.constraints.Size;

public class SalePostUpdateDTO {
    @Getter @Setter
    private String avatar;
    @Getter @Setter
    @Size(min = 20, max = 200,message = "Must be between 1 and 200 characters")
    private String title;
    @Getter @Setter
    @Min(value = 0,message = "Price can not be negative ")
    private Double finalPrice;
    @Getter @Setter
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
    private Integer categoryID;
    @Getter @Setter
    private Integer sellStatusID;
    public SalePost loadSalePostFromDTO(SalePost salePost){
        if (this.getAvatar() != null){
            salePost.setAvatar(this.getAvatar());
        }
        if (this.getTitle() != null){
            salePost.setTitle(this.getTitle());
        }
        if (this.getFinalPrice() != null){
            salePost.setFinalPrice(this.getFinalPrice());
        }
        if (this.getInitialPrice() != null){
            salePost.setInitialPrice(this.getInitialPrice());
        }
        if(this.getManufacturer() != null){
            salePost.setManufacturer(this.getManufacturer());
        }
        if (this.getOrigin() != null){
            salePost.setOrigin(this.getOrigin());
        }
        if (this.getBrand() != null){
            salePost.setBrand(this.getBrand());
        }
        if (this.getDescription() != null){
            salePost.setDescription(this.getDescription());
        }
        return salePost;
    }
}
