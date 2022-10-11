package com.thunv.ecommerceou.models.pojo;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "orders_agency")
public class OrderAgency {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    @Getter @Setter
    private Integer id;
    @ManyToOne
    @JoinColumn(name = "agency",referencedColumnName = "id")
    @Getter @Setter
    private Agency agency;
    @OneToMany(mappedBy = "orderAgency",cascade = CascadeType.REMOVE, orphanRemoval = true)
    @Getter @Setter
    private Set<OrderDetail> orderDetailSet;
    @ManyToOne
    @JoinColumn(name = "orders",referencedColumnName = "id")
    @Getter @Setter
    private Orders orders;

    public OrderAgency() {
    }
}
