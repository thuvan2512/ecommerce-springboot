package com.thunv.ecommerceou.services;

import com.thunv.ecommerceou.models.pojo.LocationDistricts;

import java.util.List;

public interface LocationDistrictsService {
    LocationDistricts getLocationDistrictsByID(String id);
    List<LocationDistricts> getAllLocationProvinces();
    List<LocationDistricts> getListLocationProvincesByProvinceID(String provinceID);
}
