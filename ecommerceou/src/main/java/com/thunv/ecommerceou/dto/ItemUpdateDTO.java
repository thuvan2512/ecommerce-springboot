package com.thunv.ecommerceou.dto;

import com.thunv.ecommerceou.models.pojo.ItemPost;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class ItemUpdateDTO {
    @Getter @Setter
    @Size(min = 1, max = 100,message = "Must be between 1 and 100 characters")
    private String name;
    @Getter @Setter
    private String avatar;
    @Getter @Setter
    @Min(value = 0,message = "Price can not be negative ")
    private Double unitPrice;
    @Getter @Setter
    @Min(value = 0,message = "Inventory can not be negative ")
    private Integer inventory;
    @Getter @Setter
    @Size(min = 1, max = 50,message = "Must be between 1 and 50 characters")
    private String description;
    public ItemPost loadItemFromDTO(ItemPost itemPost){
        if (this.getAvatar() != null){
            itemPost.setAvatar(this.getAvatar());
        }
        if (this.getName() != null){
            itemPost.setName(this.getName());
        }
        if (this.getUnitPrice() != null){
            itemPost.setUnitPrice(this.getUnitPrice());
        }
        if (this.getInventory() != null){
            itemPost.setInventory(this.getInventory());
        }
        if (this.getDescription() != null){
            itemPost.setDescription(this.getDescription());
        }
        return itemPost;
    }
}
