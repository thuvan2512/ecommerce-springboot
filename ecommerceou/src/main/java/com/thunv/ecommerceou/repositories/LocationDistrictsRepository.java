package com.thunv.ecommerceou.repositories;

import com.thunv.ecommerceou.models.pojo.LocationDistricts;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LocationDistrictsRepository extends JpaRepository<LocationDistricts,String> {
    List<LocationDistricts> findByProvinceID(String provinceId);
}
