package com.thunv.ecommerceou.repositories;

import com.thunv.ecommerceou.models.pojo.Agency;
import com.thunv.ecommerceou.models.pojo.RenewalManage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RenewalManageRepository extends JpaRepository<RenewalManage, Integer> {
    boolean existsByAgency(Agency agency);
    List<RenewalManage> findByAgency(Agency agency);
}
