package com.thunv.ecommerceou.repositories;

import com.thunv.ecommerceou.models.pojo.Agency;
import com.thunv.ecommerceou.models.pojo.OrderAgency;
import com.thunv.ecommerceou.repositories.custom.OrdersAgencyRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrdersAgencyRepository extends JpaRepository<OrderAgency,Integer>, OrdersAgencyRepositoryCustom {
    List<OrderAgency> findByAgency(Agency agency);
}
