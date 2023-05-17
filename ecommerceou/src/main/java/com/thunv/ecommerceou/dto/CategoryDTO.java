package com.thunv.ecommerceou.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

public class CategoryDTO {
    @NotNull(message = "Picture of category can't be not null")
    @Getter @Setter
    private String avatar;

    @NotNull(message = "Name of category can't be not null")
    @Getter @Setter
    private String name;

    @Getter @Setter
    private String nameVi;

    @Getter @Setter
    private Integer active;
}
