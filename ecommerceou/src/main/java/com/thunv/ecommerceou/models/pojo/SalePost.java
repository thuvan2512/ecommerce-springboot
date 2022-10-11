package com.thunv.ecommerceou.models.pojo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

@Entity
@Table(name = "sale_post")
public class SalePost {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    @Getter
    @Setter
    private Integer id;
    @Column(name = "avatar")
    @Getter @Setter
    private String avatar;
    @Column(name = "created_date")
    @Temporal(TemporalType.TIMESTAMP)
    @Getter @Setter
    private Date createdDate;
    @Column(name = "active")
    @Getter @Setter
    private Integer isActive;
    @Column(name = "title")
    @Getter @Setter
    private String title;
    @Column(name = "final_price")
    @Getter @Setter
    private Double finalPrice;
    @Column(name = "initial_price")
    @Getter @Setter
    private Double initialPrice;
    @Column(name = "manufacturer")
    @Getter @Setter
    private String manufacturer;
    @Column(name = "origin")
    @Getter @Setter
    private String origin;
    @Column(name = "brand")
    @Getter @Setter
    private String brand;
    @Column(name = "description")
    @Getter @Setter
    private String description;
    @ManyToOne
    @JoinColumn(name = "category", referencedColumnName = "id")
    @Getter @Setter
    private Category category;
    @ManyToOne
    @JoinColumn(name = "agency", referencedColumnName = "id")
    @Getter @Setter
    @JsonIgnoreProperties({"manager"})
    private Agency agency;
    @ManyToOne
    @JoinColumn(name = "sell_status", referencedColumnName = "id")
    @Getter @Setter
    private SellStatus sellStatus;
    @OneToMany(mappedBy = "salePost",cascade = CascadeType.REMOVE,
            orphanRemoval = true,fetch = FetchType.EAGER)
    @Getter @Setter
    private Set<PicturePost> picturePostSet;
    @OneToMany(mappedBy = "salePost",cascade = CascadeType.REMOVE,
            orphanRemoval = true,fetch = FetchType.EAGER)
    @Getter @Setter
    @JsonIgnoreProperties({"salePost"})
    private Set<ItemPost> itemPostSet;
    @OneToMany(mappedBy = "salePost",cascade = CascadeType.REMOVE, orphanRemoval = true)
    @Getter @Setter
    @JsonIgnore
    private  Set<CommentPost> commentPostSet;
    @OneToMany(mappedBy = "salePost",cascade = CascadeType.REMOVE, orphanRemoval = true)
    @JsonIgnore
    @Getter @Setter
    private  Set<LikePost> likePostSet;

    public SalePost() {
    }
}
