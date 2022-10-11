package com.thunv.ecommerceou.repositories;

import com.thunv.ecommerceou.models.pojo.CommentPost;
import com.thunv.ecommerceou.repositories.custom.CommentRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<CommentPost,Integer>, CommentRepositoryCustom {
}
