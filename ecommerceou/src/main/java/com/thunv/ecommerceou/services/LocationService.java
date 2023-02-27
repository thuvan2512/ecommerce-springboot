package com.thunv.ecommerceou.services;

import com.thunv.ecommerceou.models.pojo.Category;

import java.util.List;

public interface LocationService {
    String getFullAddressByWardID(String wardID);
    List<Object[]> findNearestLocation(Float latitude, Float longitude);
}
