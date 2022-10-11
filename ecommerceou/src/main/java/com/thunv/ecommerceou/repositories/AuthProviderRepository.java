package com.thunv.ecommerceou.repositories;

import com.thunv.ecommerceou.models.pojo.AuthProvider;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthProviderRepository extends JpaRepository<AuthProvider,Integer> {
}
