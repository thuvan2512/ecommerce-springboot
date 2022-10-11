package com.thunv.ecommerceou.repositories;

import com.thunv.ecommerceou.models.pojo.ItemPost;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemRepository extends JpaRepository<ItemPost,Integer> {
}
