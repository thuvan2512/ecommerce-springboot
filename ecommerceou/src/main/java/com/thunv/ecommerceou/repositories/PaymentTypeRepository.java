package com.thunv.ecommerceou.repositories;

import com.thunv.ecommerceou.models.pojo.PaymentType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentTypeRepository extends JpaRepository<PaymentType, Integer> {
}
