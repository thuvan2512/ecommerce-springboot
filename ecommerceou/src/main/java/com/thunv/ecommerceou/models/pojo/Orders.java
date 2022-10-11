package com.thunv.ecommerceou.models.pojo;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

@Entity
@Table(name = "orders")
public class Orders  {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    @Getter @Setter
    private Integer id;
    @Column(name = "payment_state")
    @Getter @Setter
    private Integer paymentState;
    @Column(name = "total_price")
    @Getter @Setter
    private Double totalPrice;
    @Column(name = "created_date")
    @Temporal(TemporalType.TIMESTAMP)
    @Getter @Setter
    private Date createdDate;
    @ManyToOne
    @JoinColumn(name = "payment_type",referencedColumnName = "id")
    @Getter @Setter
    private PaymentType paymentType;
    @ManyToOne
    @JoinColumn(name = "order_state",referencedColumnName = "id")
    @Getter @Setter
    private OrderState orderState;
    @OneToMany(mappedBy = "orders",cascade = CascadeType.REMOVE, orphanRemoval = true)
    @Getter @Setter
    private Set<OrderAgency> orderAgencySet;

    public Orders () {
    }
}
