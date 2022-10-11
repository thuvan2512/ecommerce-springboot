package com.thunv.ecommerceou.repositories;

import com.thunv.ecommerceou.models.pojo.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category,Integer> {
}
