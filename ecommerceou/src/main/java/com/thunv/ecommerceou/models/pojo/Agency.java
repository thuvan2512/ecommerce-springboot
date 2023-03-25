package com.thunv.ecommerceou.models.pojo;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

@Entity
@Table(name = "agency")
public class Agency {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    @Getter @Setter
    private Integer id;

    @Basic(optional = false)
    @Column(name = "name")
    @Getter @Setter
    private String name;

    @Column(name = "active")
    @Getter @Setter
    private Integer isActive = 0;

    @Column(name = "avatar")
    @Getter @Setter
    private String avatar;

    @Column(name = "cover_image")
    @Getter @Setter
    private String coverImage;

    @Column(name = "address")
    @Getter @Setter
    private String address;

    @Column(name = "hotline")
    @Getter @Setter
    private String hotline;

    @Column(name = "from_province_name")
    @Getter @Setter
    private String fromProvinceName;

    @Column(name = "province_id")
    @Getter @Setter
    private Integer provinceID;

    @Column(name = "from_district_name")
    @Getter @Setter
    private String fromDistrictName;

    @Column(name = "district_id")
    @Getter @Setter
    private Integer districtID;

    @Column(name = "from_ward_name")
    @Getter @Setter
    private String fromWardName;

    @Column(name = "ward_id")
    @Getter @Setter
    private String wardID;

    @Column(name = "from_address")
    @Getter @Setter
    private String fromAddress;


    @Column(name = "created_date")
    @Temporal(TemporalType.TIMESTAMP)
    @Getter @Setter
    private Date createdDate;
    @Column(name = "censored")
    @Getter @Setter
    private Integer isCensored = 0;
    @ManyToOne
    @JoinColumn(name = "manager",referencedColumnName = "id")
    @Getter @Setter
    private User manager;
    @JoinColumn(name = "field", referencedColumnName = "id")
    @ManyToOne
    @Getter @Setter
    private AgentField field;
    @OneToMany(mappedBy = "agency",cascade = CascadeType.REMOVE, orphanRemoval = true)
    @JsonIgnore
    @Getter @Setter
    private Set<SalePost> salePostSet;
    @OneToMany(mappedBy = "agency",cascade = CascadeType.REMOVE, orphanRemoval = true)
    @JsonIgnore
    @Getter @Setter
    private  Set<CensorshipAgency> censorshipAgencySet;
    @OneToMany(mappedBy = "agency",cascade = CascadeType.REMOVE, orphanRemoval = true)
    @JsonIgnore
    @Getter @Setter
    private  Set<FollowAgency> followAgencySet;
    @OneToMany(mappedBy = "agency",cascade = CascadeType.REMOVE, orphanRemoval = true)
    @JsonIgnore
    @Getter @Setter
    private  Set<ResponseAgency> responseAgencySet;
    @OneToMany(mappedBy = "agency",cascade = CascadeType.REMOVE, orphanRemoval = true)
    @JsonIgnore
    @Getter @Setter
    private Set<OrderAgency> orderAgencySet;
    public Agency() {

    }

    @Override
    public String toString() {
        return "Agency {" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", hotline='" + hotline + '\'' +
                ", fromProvinceName='" + fromProvinceName + '\'' +
                ", provinceID=" + provinceID +
                ", fromDistrictName='" + fromDistrictName + '\'' +
                ", districtID=" + districtID +
                ", fromWardName='" + fromWardName + '\'' +
                ", wardID='" + wardID + '\'' +
                ", fromAddress='" + fromAddress + '\'' +
                "}\n";
    }
}
