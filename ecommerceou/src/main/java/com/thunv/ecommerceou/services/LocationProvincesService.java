package com.thunv.ecommerceou.services;

import com.thunv.ecommerceou.models.pojo.LocationProvinces;

import java.util.List;

public interface LocationProvincesService {
    LocationProvinces getLocationProvincesByID(String id);
    List<LocationProvinces> getListLocationProvinces();
}
