package com.thunv.ecommerceou.models.pojo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "cart_item")
public class CartItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    @Getter @Setter
    private Integer id;
    @Getter @Setter
    @ManyToOne
    @JoinColumn(name = "cart",referencedColumnName = "id")
    @JsonIgnore
    private Cart cart;
    @Getter @Setter
    @ManyToOne
    @JoinColumn(name = "item_post",referencedColumnName = "id")
    @JsonIgnoreProperties({"salePost"})
    private ItemPost itemPost;
    @Getter @Setter
    private int quantity;
}
