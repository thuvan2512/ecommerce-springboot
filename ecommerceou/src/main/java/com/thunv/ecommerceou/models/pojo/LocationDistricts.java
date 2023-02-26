package com.thunv.ecommerceou.models.pojo;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "location_districts_tb")
public class LocationDistricts {
    @Id
    @Basic(optional = false)
    @Column(name = "district_id")
    @Getter
    @Setter
    private String districtID;
    @Column(name = "province_id")
    @Getter
    @Setter
    private String provinceID;
    @Column(name = "district_name_vi")
    @Getter
    @Setter
    private String districtName;
    @Column(name = "longitude")
    @Getter
    @Setter
    private Float longitude;
    @Column(name = "latitude")
    @Getter
    @Setter
    private Float latitude;


}
