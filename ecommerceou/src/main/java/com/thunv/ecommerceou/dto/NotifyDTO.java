package com.thunv.ecommerceou.dto;

import com.thunv.ecommerceou.models.NotificationEntity;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

public class NotifyDTO {
    @Setter @Getter
    @NotNull(message = "RecipientID can not be null")
    private String recipientID;

    @Setter @Getter
    @NotNull(message = "Image can not be null")
    private String image;

    @Setter @Getter
    @NotNull(message = "Title can not be null")
    private String title;

    @Setter @Getter
    @NotNull(message = "Details can not be null")
    private String details;

    @Setter @Getter
    @NotNull(message = "Type can not be null")
    private String type;

}
