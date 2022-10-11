package com.thunv.ecommerceou.dto;

import com.thunv.ecommerceou.models.pojo.PicturePost;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

public class PicturePostDTO {
    @Getter @Setter
    private String image;
    @Getter @Setter
    private String description;
    @Getter @Setter
    @NotNull(message = "Sale post id can not be null")
    private Integer postID;
    public PicturePost loadPicturePostFromDTO(PicturePost picturePost){
        picturePost.setDescription(this.getDescription());
        picturePost.setImage(this.getImage());
        return picturePost;
    }
}
