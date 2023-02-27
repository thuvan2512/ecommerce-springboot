package com.thunv.ecommerceou.repositories;

import com.thunv.ecommerceou.models.pojo.Category;
import com.thunv.ecommerceou.models.pojo.LocationWards;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface LocationRepository{
    List<Object[]> findNearestLocation(Float latitude, Float longitude);
}
