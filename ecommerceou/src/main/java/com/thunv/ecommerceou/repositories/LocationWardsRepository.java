package com.thunv.ecommerceou.repositories;

import com.thunv.ecommerceou.models.pojo.LocationDistricts;
import com.thunv.ecommerceou.models.pojo.LocationWards;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LocationWardsRepository extends JpaRepository<LocationWards,String> {
    List<LocationWards> findByProvinceID(String provinceId);
    List<LocationWards> findByDistrictID(String districtId);
}
