package com.thunv.ecommerceou.services.impl;

import com.thunv.ecommerceou.models.pojo.Agency;
import com.thunv.ecommerceou.models.pojo.CartItem;
import com.thunv.ecommerceou.models.pojo.CustomerAddressBook;
import com.thunv.ecommerceou.services.OrderTrackingService;
import com.thunv.ecommerceou.utils.GHNExpressUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class OrderTrackingServiceImpl implements OrderTrackingService {
    @Autowired
    private GHNExpressUtils ghnExpressUtils;
    @Override
    public Map<Object, Object> getLocationProvincesOfGHNExpress() throws RuntimeException{
        try {
            Map<Object, Object> results = this.ghnExpressUtils.getLocationProvinceOfGHNExpress();
            return results;
        }catch (Exception exception){
            System.out.println(exception.getMessage());
        }
        return new HashMap<>();
    }

    @Override
    public Map<Object, Object> getLocationDistrictsOfGHNExpress(int provinceID) throws RuntimeException{
        try {
            Map<Object, Object> results = this.ghnExpressUtils.getLocationDistrictsOfGHNExpress(provinceID);
            return results;
        }catch (Exception exception){
            System.out.println(exception.getMessage());
        }
        return new HashMap<>();
    }

    @Override
    public Map<Object, Object> getLocationWardsOfGHNExpress(int districtID) throws RuntimeException{
        try {
            Map<Object, Object> results = this.ghnExpressUtils.getLocationWardsOfGHNExpress(districtID);
            return results;
        }catch (Exception exception){
            System.out.println(exception.getMessage());
        }
        return new HashMap<>();
    }

    @Override
    public Map<Object, Object> getAvailableServiceShippingOfGHNExpress(int fromDistrictID, String fromWard, int toDistrictID, String toWardID) throws RuntimeException{
        try {
            Map<Object, Object> results = this.ghnExpressUtils.getAvailableServiceShippingOfGHNExpress(fromDistrictID, fromWard, toDistrictID, toWardID);
            return results;
        }catch (Exception exception){
            System.out.println(exception.getMessage());
        }
        return new HashMap<>();
    }

    @Override
    public Map<Object, Object> createOrderOfGHNExpress(Integer paymentTypeOfGHN, Agency agency, CustomerAddressBook customerAddressBook, List<CartItem> listItem, Integer serviceID, Integer serviceTypeID, Integer amountCOD) throws RuntimeException{
        try {
            Map<Object, Object> results = this.ghnExpressUtils.createOrderShipping(paymentTypeOfGHN, agency, customerAddressBook, listItem, serviceID, serviceTypeID, amountCOD);
            return results;
        }catch (Exception exception){
            System.out.println(exception.getMessage());
        }
        return new HashMap<>();
    }
}
