package com.thunv.ecommerceou.repositories;

import com.thunv.ecommerceou.models.pojo.FollowAgency;
import com.thunv.ecommerceou.repositories.custom.FollowRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FollowRepository extends JpaRepository<FollowAgency,Integer> , FollowRepositoryCustom {
}
