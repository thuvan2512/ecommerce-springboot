package com.thunv.ecommerceou.services.impl;

import com.thunv.ecommerceou.models.pojo.LocationDistricts;
import com.thunv.ecommerceou.repositories.LocationDistrictsRepository;
import com.thunv.ecommerceou.services.LocationDistrictsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LocationDistrictsServiceImpl implements LocationDistrictsService {
    @Autowired
    private LocationDistrictsRepository locationDistrictsRepository;
    @Override
    public LocationDistricts getLocationDistrictsByID(String id) throws RuntimeException{
        return this.locationDistrictsRepository.findById(id).orElseThrow(() ->
                new RuntimeException("Can not find district with id = " + id));
    }

    @Override
    public List<LocationDistricts> getAllLocationProvinces() throws RuntimeException{
        try {
            return this.locationDistrictsRepository.findAll();
        }catch (Exception ex){
            String error_ms = ex.getMessage();
            throw new RuntimeException(error_ms);
        }
    }

    @Override
    public List<LocationDistricts> getListLocationProvincesByProvinceID(String provinceID) throws RuntimeException{
        try {
            return this.locationDistrictsRepository.findByProvinceID(provinceID);
        }catch (Exception ex){
            String error_ms = ex.getMessage();
            throw new RuntimeException(error_ms);
        }
    }
}
