package com.thunv.ecommerceou.services.impl;

import com.thunv.ecommerceou.models.pojo.LocationProvinces;
import com.thunv.ecommerceou.repositories.LocationProvincesRepository;
import com.thunv.ecommerceou.services.LocationProvincesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LocationProvincesServiceImpl implements LocationProvincesService {
    @Autowired
    private LocationProvincesRepository locationProvincesRepository;
    @Override
    public LocationProvinces getLocationProvincesByID(String id) throws RuntimeException{
        return this.locationProvincesRepository.findById(id).orElseThrow(() ->
                new RuntimeException("Can not find provinces with id = " + id));
    }

    @Override
    public List<LocationProvinces> getListLocationProvinces() throws RuntimeException{
        try {
            return this.locationProvincesRepository.findAll();
        }catch (Exception ex){
            String error_ms = ex.getMessage();
            throw new RuntimeException(error_ms);
        }
    }
}
