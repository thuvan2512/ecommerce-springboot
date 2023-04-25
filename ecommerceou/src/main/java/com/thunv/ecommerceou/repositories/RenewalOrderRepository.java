package com.thunv.ecommerceou.repositories;

import com.thunv.ecommerceou.models.pojo.RenewalOrder;
import com.thunv.ecommerceou.repositories.custom.RenewalOrderRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RenewalOrderRepository extends JpaRepository<RenewalOrder, Integer>, RenewalOrderRepositoryCustom {
}
