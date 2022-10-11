package com.thunv.ecommerceou.repositories;

import com.thunv.ecommerceou.models.pojo.Category;
import com.thunv.ecommerceou.models.pojo.PicturePost;
import com.thunv.ecommerceou.models.pojo.SalePost;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface PicturePostRepository extends JpaRepository<PicturePost,Integer> {
}
