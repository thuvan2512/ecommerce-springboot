package com.thunv.ecommerceou.models.pojo;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "location_wards_tb")
public class LocationWards {
    @Id
    @Basic(optional = false)
    @Column(name = "ward_id")
    @Getter
    @Setter
    private String wardID;
    @Column(name = "province_id")
    @Getter
    @Setter
    private String provinceID;
    @Column(name = "district_id")
    @Getter
    @Setter
    private String districtID;
    @Column(name = "ward_name_vi")
    @Getter
    @Setter
    private String wardName;
    @Column(name = "longitude")
    @Getter
    @Setter
    private Float longitude;
    @Column(name = "latitude")
    @Getter
    @Setter
    private Float latitude;
}
