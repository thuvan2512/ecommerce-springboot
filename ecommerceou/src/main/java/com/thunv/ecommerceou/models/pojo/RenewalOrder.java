package com.thunv.ecommerceou.models.pojo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "renewal_order")
public class RenewalOrder {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    @Getter
    @Setter
    private Integer id;

    @JoinColumn(name = "renewal_id", referencedColumnName = "id")
    @ManyToOne
    @JsonIgnore
    @Getter @Setter
    private RenewalManage renewalManage;

    @JoinColumn(name = "pakage_id", referencedColumnName = "id")
    @ManyToOne
    @Getter @Setter
    private RenewalPackage renewalPackage;

    @Column(name = "number_of_days_available")
    @Getter @Setter
    private Integer numberOfDaysAvailable;

    @Column(name = "price")
    @Getter @Setter
    private Integer price;

    @Column(name = "created_date")
    @Getter @Setter
    private Date createdDate = new Date();


}
