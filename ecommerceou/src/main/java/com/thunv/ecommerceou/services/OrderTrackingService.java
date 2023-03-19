package com.thunv.ecommerceou.services;

import java.util.Map;

public interface OrderTrackingService {
    Map<Object, Object> getLocationProvincesOfGHNExpress();
    Map<Object, Object> getLocationDistrictsOfGHNExpress(int provinceID);
    Map<Object, Object> getLocationWardsOfGHNExpress(int districtID);
    Map<Object, Object> getAvailableServiceShippingOfGHNExpress(int fromDistrictID, int toDistrictID, String toWardID);
}
