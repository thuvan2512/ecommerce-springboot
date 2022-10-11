package com.thunv.ecommerceou.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.thunv.ecommerceou.models.pojo.ItemPost;
import com.thunv.ecommerceou.models.pojo.SalePost;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class ItemCreateDTO {
    @Getter @Setter
    @NotNull(message = "Name can not be null")
    @Size(min = 1, max = 100,message = "Must be between 1 and 100 characters")
    private String name;
    @Getter @Setter
    @NotNull(message = "Avatar can not be null")
    private String avatar;
    @Getter @Setter
    @NotNull(message = "Price can not be null")
    @Min(value = 0,message = "Price can not be negative ")
    private Double unitPrice;
    @Getter @Setter
    @NotNull(message = "Inventory can not be null")
    @Min(value = 0,message = "Inventory can not be negative ")
    private Integer inventory;
    @Getter @Setter
    @NotNull(message = "Description can not be null")
    @Size(min = 1, max = 50,message = "Must be between 1 and 50 characters")
    private String description;
    @Getter @Setter
    @JsonIgnore
    private SalePost salePost;
    public ItemPost loadItemFromDTO(ItemPost itemPost){
        itemPost.setName(this.getName());
        itemPost.setAvatar(this.getAvatar());
        itemPost.setUnitPrice(this.getUnitPrice());
        itemPost.setInventory(this.getInventory());
        itemPost.setDescription(this.getDescription());
        itemPost.setSalePost(this.getSalePost());
        return itemPost;
    }
}
