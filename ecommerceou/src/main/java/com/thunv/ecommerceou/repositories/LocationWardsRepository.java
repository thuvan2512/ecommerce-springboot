package com.thunv.ecommerceou.repositories;

import com.thunv.ecommerceou.models.pojo.LocationDistricts;
import com.thunv.ecommerceou.models.pojo.LocationWards;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface LocationWardsRepository extends JpaRepository<LocationWards,String> {
    List<LocationWards> findByProvinceID(String provinceId);
    List<LocationWards> findByDistrictID(String districtId);

    @Query(value = "SELECT root.ward_id, ((ACOS(SIN(?1 * PI() / 180) * SIN(root.latitude * PI() / 180) + COS(?1 * PI() / 180) * COS(root.latitude * PI() / 180) * COS((?2 - root.longitude) * PI() / 180)) * 180 / PI()) * 60 * 1.1515) AS distance FROM location_wards_tb as root WHERE root.longitude IS NOT NULL AND root.latitude IS NOT NULL ORDER BY distance ASC LIMIT 0,1", nativeQuery = true)
    List<Object[]> getNearestLocationWard(Double latitude, Double longitude);
}
