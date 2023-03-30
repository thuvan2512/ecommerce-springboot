package com.thunv.ecommerceou.repositories;

import com.thunv.ecommerceou.models.pojo.Agency;
import com.thunv.ecommerceou.models.pojo.SalePost;
import com.thunv.ecommerceou.repositories.custom.SalePostRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SalePostRepository extends JpaRepository<SalePost,Integer>, SalePostRepositoryCustom {

    @Query(value = "SELECT title FROM sale_post WHERE MATCH (title) AGAINST (:keyword) > 0 ORDER BY MATCH (title) AGAINST (:keyword) DESC LIMIT 0,10;", nativeQuery = true)
    List<Object> getSuggestForSearchProducts(@Param("keyword") String keyword);

    List<SalePost> findByAgency(Agency agency);
}
