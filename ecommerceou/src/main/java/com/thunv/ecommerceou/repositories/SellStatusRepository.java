package com.thunv.ecommerceou.repositories;

import com.thunv.ecommerceou.models.pojo.SellStatus;
import io.swagger.models.auth.In;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

public interface SellStatusRepository extends JpaRepository<SellStatus,Integer> {
}
