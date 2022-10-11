package com.thunv.ecommerceou.repositories;

import com.thunv.ecommerceou.models.pojo.CensorshipAgency;
import com.thunv.ecommerceou.repositories.custom.CensorshipAgencyRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CensorshipAgencyRepository extends JpaRepository<CensorshipAgency,Integer>, CensorshipAgencyRepositoryCustom {
}
