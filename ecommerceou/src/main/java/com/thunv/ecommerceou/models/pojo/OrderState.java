package com.thunv.ecommerceou.models.pojo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "order_state")
public class OrderState {
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
    @OneToMany(mappedBy = "orderState")
    @JsonIgnore
    @Getter @Setter
    private Set<OrderAgency> orderAgencySet;

    public OrderState() {
    }

    public OrderState(Integer id, String name) {
        this.id = id;
        this.name = name;
    }
}
