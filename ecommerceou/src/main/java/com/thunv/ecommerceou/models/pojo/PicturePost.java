package com.thunv.ecommerceou.models.pojo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "picture_post")
public class PicturePost {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    @Getter @Setter
    private Integer id;
//    @NotNull(message = "Information is not allowed to be left blank")
    @Column(name = "image")
    @Getter @Setter
    private String image;
//    @NotNull(message = "Information is not allowed to be left blank")
    @Column(name = "description")
    @Getter @Setter
    private String description;
    @ManyToOne
    @JoinColumn(name = "sale_post",referencedColumnName = "id")
    @JsonIgnore
    @Getter
    @Setter
    private SalePost salePost;

    public PicturePost() {
    }
}
