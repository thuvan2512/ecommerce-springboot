package com.thunv.ecommerceou.models.pojo;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Table(name = "like_post")
public class LikePost {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    @Getter @Setter
    private Integer id;
    @Column(name = "state")
    @Getter @Setter
    private Integer state;
    @JoinColumn(name = "sale_post", referencedColumnName = "id")
    @ManyToOne
    @Getter @Setter
    @JsonIgnoreProperties({"itemPostSet","picturePostSet"})
    private SalePost salePost;
    @JoinColumn(name = "author", referencedColumnName = "id")
    @ManyToOne
    @Getter
    @Setter
    private User author;

    public LikePost() {
    }

}
