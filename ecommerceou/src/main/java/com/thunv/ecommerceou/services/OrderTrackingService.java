package com.thunv.ecommerceou.services;

import com.thunv.ecommerceou.models.pojo.Agency;
import com.thunv.ecommerceou.models.pojo.CartItem;
import com.thunv.ecommerceou.models.pojo.CustomerAddressBook;

import java.util.List;
import java.util.Map;

public interface OrderTrackingService {
    Map<Object, Object> getLocationProvincesOfGHNExpress();
    Map<Object, Object> getLocationDistrictsOfGHNExpress(int provinceID);
    Map<Object, Object> getLocationWardsOfGHNExpress(int districtID);
    Map<Object, Object> getAvailableServiceShippingOfGHNExpress(int fromDistrictID, String fromWard, int toDistrictID, String toWardID);
    Map<Object, Object> createOrderOfGHNExpress(Integer paymentTypeOfGHN, Agency agency, CustomerAddressBook customerAddressBook, List<CartItem> listItem, Integer serviceID, Integer serviceTypeID, Integer amountCOD);
}
