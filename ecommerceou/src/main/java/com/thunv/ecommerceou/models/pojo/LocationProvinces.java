package com.thunv.ecommerceou.models.pojo;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "location_provinces_tb")
public class LocationProvinces {
    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "province_id")
    @Getter
    @Setter
    private String provinceID;
    @Column(name = "province_name_vi")
    @Getter
    @Setter
    private String provinceName;
    @Column(name = "longitude")
    @Getter
    @Setter
    private Float longitude;
    @Column(name = "latitude")
    @Getter
    @Setter
    private Float latitude;

}
