package com.thunv.ecommerceou.models.pojo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;
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
    @JsonIgnore
    private Set<OrderDetail> orderDetailSet;
    @ManyToOne
    @JoinColumn(name = "orders",referencedColumnName = "id")
    @Getter @Setter
    private Orders orders;

    @Column(name = "expected_delivery_time")
    @Getter @Setter
    private String expectedDeliveryTime;

    @Column(name = "order_express_id")
    @Getter @Setter
    private String orderExpressID;

    @Column(name = "ship_fee")
    @Getter @Setter
    private Double shipFee;

    @ManyToOne
    @JoinColumn(name = "order_state",referencedColumnName = "id")
    @Getter @Setter
    private OrderState orderState;
    @Column(name = "total_price")
    @Getter
    @Setter
    private Double totalPrice;

    @ManyToOne
    @JoinColumn(name = "delivery_info",referencedColumnName = "id")
    @Getter
    @Setter
    private CustomerAddressBook deliveryInfo;
    public OrderAgency() {
    }
}
