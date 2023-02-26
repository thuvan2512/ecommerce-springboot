package com.thunv.ecommerceou.services.impl;

import com.thunv.ecommerceou.models.pojo.LocationWards;
import com.thunv.ecommerceou.repositories.LocationWardsRepository;
import com.thunv.ecommerceou.services.LocationWardsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LocationWardsServiceImpl implements LocationWardsService {
    @Autowired
    private LocationWardsRepository locationWardsRepository;
    @Override
    public LocationWards getLocationWardsByID(String id) throws RuntimeException{
        return this.locationWardsRepository.findById(id).orElseThrow(() ->
                new RuntimeException("Can not find ward with id = " + id));
    }

    @Override
    public List<LocationWards> getAllLocationWards() throws RuntimeException{
        try {
            return this.locationWardsRepository.findAll();
        }catch (Exception ex){
            String error_ms = ex.getMessage();
            throw new RuntimeException(error_ms);
        }
    }

    @Override
    public List<LocationWards> getListLocationWardsByProvinceID(String provinceID) throws RuntimeException{
        try {
            return this.locationWardsRepository.findByProvinceID(provinceID);
        }catch (Exception ex){
            String error_ms = ex.getMessage();
            throw new RuntimeException(error_ms);
        }
    }

    @Override
    public List<LocationWards> getListLocationWardsByDistrictID(String districtID) throws RuntimeException{
        try {
            return this.locationWardsRepository.findByDistrictID(districtID);
        }catch (Exception ex){
            String error_ms = ex.getMessage();
            throw new RuntimeException(error_ms);
        }
    }
}
