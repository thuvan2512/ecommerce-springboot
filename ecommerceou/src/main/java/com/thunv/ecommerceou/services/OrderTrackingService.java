package com.thunv.ecommerceou.services;

import com.thunv.ecommerceou.models.pojo.Agency;
import com.thunv.ecommerceou.models.pojo.CartItem;
import com.thunv.ecommerceou.models.pojo.CustomerAddressBook;
import com.thunv.ecommerceou.models.pojo.User;

import java.util.List;
import java.util.Map;

public interface OrderTrackingService {
    Map<Object, Object> getLocationProvincesOfGHNExpress();
    Map<Object, Object> getLocationDistrictsOfGHNExpress(int provinceID);
    Map<Object, Object> getLocationWardsOfGHNExpress(int districtID);
    Map<Object, Object> generateTokenToPrintOrderOfGHNExpress(String orderCode);

    Map<Object, Object> cancelOrderOfGHNExpress(String orderCode);
    Map<Object, Object> reviewOrderOfGHNExpress(String orderCode);
    Map<Object, Object> getPickShiftOfGHNExpress();
    Map<Object, Object> setPickShiftOfGHNExpress(String orderCode, Integer pickShiftID);
    Map<Object, Object> getAvailableServiceShippingOfGHNExpress(User user, Agency agency, CustomerAddressBook customerAddressBook);
    Map<Object, Object> createOrderOfGHNExpress(Integer paymentTypeOfGHN, Agency agency, CustomerAddressBook customerAddressBook, List<CartItem> listItem, Integer serviceID, Integer serviceTypeID, Integer amountCOD);
}
