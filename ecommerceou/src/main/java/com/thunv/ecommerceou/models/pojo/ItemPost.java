package com.thunv.ecommerceou.models.pojo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "item_post")
public class ItemPost {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    @Getter @Setter
    private Integer id;
    @Column(name = "name")
    @Getter @Setter
    private String name;
    @Column(name = "avatar")
    @Getter @Setter
    private String avatar;
    @Column(name = "active")
    @Getter @Setter
    private Integer isActive;
    @Column(name = "unit_price")
    @Getter @Setter
    private Double unitPrice;
    @Column(name = "inventory")
    @Getter @Setter
    private Integer inventory;
    @Column(name = "description")
    @Getter @Setter
    private String description;
    @ManyToOne
    @JoinColumn(name = "sale_post",referencedColumnName = "id")
    @Getter
    @Setter
    @JsonIgnoreProperties({"itemPostSet","picturePostSet"})
    private SalePost salePost;
    @OneToMany(mappedBy = "itemPost",cascade = CascadeType.REMOVE, orphanRemoval = true)
    @JsonIgnore
    private Set<OrderDetail> orderDetailSet;
    @OneToMany(mappedBy = "itemPost",cascade = CascadeType.REMOVE, orphanRemoval = true)
    @JsonIgnore
    @Getter @Setter
    private Set<CartItem> cartItemSet;

    public ItemPost() {
    }
}
