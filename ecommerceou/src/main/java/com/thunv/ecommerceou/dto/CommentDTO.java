package com.thunv.ecommerceou.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.thunv.ecommerceou.models.pojo.CommentPost;
import com.thunv.ecommerceou.models.pojo.SalePost;
import com.thunv.ecommerceou.models.pojo.User;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class CommentDTO {
    @Getter @Setter
    @NotNull(message = "Content can not be null")
    @Size(min = 1, max = 200,message = "Must be between 1 and 200 characters")
    private String content;
    @Getter @Setter
    @Min(value = 1,message = "Must be between 1 and 5")
    @Max(value = 5,message = "Must be between 1 and 5")
    private Integer starRate;
    @Getter @Setter
    @JsonIgnore
    private User author;
    @Getter @Setter
    @JsonIgnore
    private SalePost post;
    public CommentPost loadCommentFromDTO(CommentPost commentPost){
        commentPost.setContent(this.getContent());
        commentPost.setStarRate(this.getStarRate());
        return commentPost;
    }
}
