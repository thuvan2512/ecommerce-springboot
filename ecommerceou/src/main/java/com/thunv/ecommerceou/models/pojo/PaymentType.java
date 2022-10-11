package com.thunv.ecommerceou.models.pojo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "payment_type")
public class PaymentType {
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
    @OneToMany(mappedBy = "paymentType")
    @JsonIgnore
    @Getter @Setter
    private Set<Orders> orderSet;

    public PaymentType() {
    }

    public PaymentType(Integer id, String name) {
        this.id = id;
        this.name = name;
    }
}
