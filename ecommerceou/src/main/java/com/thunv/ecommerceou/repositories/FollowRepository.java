package com.thunv.ecommerceou.repositories;

import com.thunv.ecommerceou.models.pojo.FollowAgency;
import com.thunv.ecommerceou.repositories.custom.FollowRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;


public interface FollowRepository extends JpaRepository<FollowAgency,Integer> , FollowRepositoryCustom, JpaSpecificationExecutor<FollowAgency> {

}
