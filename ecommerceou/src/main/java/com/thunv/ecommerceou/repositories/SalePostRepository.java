package com.thunv.ecommerceou.repositories;

import com.thunv.ecommerceou.models.pojo.Agency;
import com.thunv.ecommerceou.models.pojo.SalePost;
import com.thunv.ecommerceou.repositories.custom.SalePostRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SalePostRepository extends JpaRepository<SalePost,Integer>, SalePostRepositoryCustom {
}
