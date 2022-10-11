package com.thunv.ecommerceou.repositories;

import com.thunv.ecommerceou.models.pojo.Agency;
import com.thunv.ecommerceou.repositories.custom.AgencyRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

public interface AgencyRepository extends JpaRepository<Agency,Integer>, AgencyRepositoryCustom {
}
