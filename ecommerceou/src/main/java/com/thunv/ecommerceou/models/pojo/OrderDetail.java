package com.thunv.ecommerceou.models.pojo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "order_detail")
public class OrderDetail {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    @Getter
    @Setter
    private Integer id;
    @Column(name = "quantity")
    @Getter
    @Setter
    private Integer quantity;
    @ManyToOne
    @JoinColumn(name = "item_post",referencedColumnName = "id")
    @Getter
    @Setter
    @JsonIgnoreProperties({"salePost"})
    private ItemPost itemPost;
    @ManyToOne
    @JoinColumn(name = "order_agency",referencedColumnName = "id")
    @Getter
    @Setter
    @JsonIgnore
    private OrderAgency orderAgency;

    public OrderDetail() {
    }
}
