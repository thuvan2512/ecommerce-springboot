package com.thunv.ecommerceou.services.impl;

import com.thunv.ecommerceou.models.pojo.Category;
import com.thunv.ecommerceou.models.pojo.LocationDistricts;
import com.thunv.ecommerceou.models.pojo.LocationProvinces;
import com.thunv.ecommerceou.models.pojo.LocationWards;
import com.thunv.ecommerceou.repositories.LocationDistrictsRepository;
import com.thunv.ecommerceou.repositories.LocationProvincesRepository;
import com.thunv.ecommerceou.repositories.LocationRepository;
import com.thunv.ecommerceou.repositories.LocationWardsRepository;
import com.thunv.ecommerceou.services.LocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class LocationServiceImpl implements LocationService {
    @Autowired
    private LocationProvincesRepository locationProvincesRepository;
    @Autowired
    private LocationDistrictsRepository locationDistrictsRepository;
    @Autowired
    private LocationWardsRepository locationWardsRepository;
    @Autowired
    private LocationRepository locationRepository;
    @Override
    public String getFullAddressByWardID(String wardID) throws RuntimeException{
        String rs = "";
        LocationWards locationWards = this.locationWardsRepository.findById(wardID).orElseThrow(() ->
                new RuntimeException("Can not find ward with id = " + wardID));
        if (locationWards != null){
            LocationDistricts locationDistricts = this.locationDistrictsRepository.findById(locationWards.getDistrictID()).orElseThrow(() ->
                    new RuntimeException("Can not find district with ward id = " + wardID));
            if (locationDistricts != null){
                LocationProvinces locationProvinces = this.locationProvincesRepository.findById(locationWards.getProvinceID()).orElseThrow(() ->
                        new RuntimeException("Can not find province with ward id = " + wardID));
                if (locationProvinces != null){
                    rs = String.format("%s, %s, %s", locationWards.getWardName(), locationDistricts.getDistrictName() ,locationProvinces.getProvinceName());
                }
            }
        }
        return rs;
    }

    @Override
    public List<Object[]> findNearestLocation(Float latitude, Float longitude) throws RuntimeException{
        return this.locationRepository.findNearestLocation(latitude,longitude);
    }
}
