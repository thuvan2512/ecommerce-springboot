package com.thunv.ecommerceou.repositories;

import com.thunv.ecommerceou.models.pojo.LikePost;
import com.thunv.ecommerceou.repositories.custom.LikePostRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LikePostRepository extends JpaRepository<LikePost,Integer>, LikePostRepositoryCustom {
}
