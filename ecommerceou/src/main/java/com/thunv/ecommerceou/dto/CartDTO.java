package com.thunv.ecommerceou.dto;

import com.thunv.ecommerceou.models.pojo.ItemPost;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

public class CartDTO {
    @Getter @Setter
    @NotNull(message = "ItemID can not be null")
    private Integer itemID;
    @NotNull(message = "Quantity can not be null")
    @Min(value = 1,message = "Quantity can not be negative ")
    @Getter @Setter
    private int quantity;
}
