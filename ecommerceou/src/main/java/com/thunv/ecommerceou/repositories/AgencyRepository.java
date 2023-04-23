package com.thunv.ecommerceou.repositories;

import com.thunv.ecommerceou.models.pojo.Agency;
import com.thunv.ecommerceou.models.pojo.User;
import com.thunv.ecommerceou.repositories.custom.AgencyRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface AgencyRepository extends JpaRepository<Agency,Integer>, AgencyRepositoryCustom, JpaSpecificationExecutor<Agency> {
    List<Agency> findByManager(User user);
}
