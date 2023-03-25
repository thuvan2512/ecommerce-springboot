package com.thunv.ecommerceou.models.pojo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.Set;

@Entity
@Table(name = "customer_address_book")
public class CustomerAddressBook {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    @Getter
    @Setter
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "customer_id",referencedColumnName = "id")
    @Getter @Setter
    private User customer;

    @Column(name = "full_address")
    @Getter @Setter
    private String fullAddress;

    @Column(name = "created_date")
    @Temporal(TemporalType.TIMESTAMP)
    @Getter @Setter
    private Date createdDate;

    @Column(name = "updated_date")
    @Temporal(TemporalType.TIMESTAMP)
    @Getter @Setter
    private Date updatedDate;

    @Column(name = "address_type")
    @Getter @Setter
    private String addressType;

    @Column(name = "delivery_phone")
    @Getter @Setter
    private String deliveryPhone;

    @Column(name = "description")
    @Getter @Setter
    private String description;

    @Column(name = "to_province_name")
    @Getter @Setter
    private String toProvinceName;

    @Column(name = "province_id")
    @Getter @Setter
    private Integer provinceID;

    @Column(name = "to_district_name")
    @Getter @Setter
    private String toDistrictName;

    @Column(name = "district_id")
    @Getter @Setter
    private Integer districtID;

    @Column(name = "to_ward_name")
    @Getter @Setter
    private String toWardName;

    @Column(name = "ward_id")
    @Getter @Setter
    private String wardID;

    @Column(name = "to_address")
    @Getter @Setter
    private String toAddress;

    @OneToMany(mappedBy = "deliveryInfo",cascade = CascadeType.REMOVE, orphanRemoval = true)
    @JsonIgnore
    @Getter @Setter
    private Set<OrderAgency> orderAgencySet;

    @Override
    public String toString() {
        return "CustomerAddressBook{" +
                "customer=" + customer.getUsername() +
                ", deliveryPhone='" + deliveryPhone + '\'' +
                ", description='" + description + '\'' +
                ", toProvinceName='" + toProvinceName + '\'' +
                ", provinceID=" + provinceID +
                ", toDistrictName='" + toDistrictName + '\'' +
                ", districtID=" + districtID +
                ", toWardName='" + toWardName + '\'' +
                ", wardID='" + wardID + '\'' +
                ", toAddress='" + toAddress + '\'' +
                "}\n";
    }
}
