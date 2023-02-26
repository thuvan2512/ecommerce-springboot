package com.thunv.ecommerceou.services;

import com.thunv.ecommerceou.models.pojo.LocationWards;

import java.util.List;

public interface LocationWardsService {
    LocationWards getLocationWardsByID(String id);
    List<LocationWards> getAllLocationWards();
    List<LocationWards> getListLocationWardsByProvinceID(String provinceID);
    List<LocationWards> getListLocationWardsByDistrictID(String districtID);
}
