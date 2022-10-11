package com.thunv.ecommerceou.services;


import com.thunv.ecommerceou.dto.ItemCreateDTO;
import com.thunv.ecommerceou.dto.ItemUpdateDTO;
import com.thunv.ecommerceou.models.pojo.ItemPost;
import com.thunv.ecommerceou.models.pojo.SalePost;

import java.util.List;

public interface ItemService {
    ItemPost getItemPostByID(int itemID);
    List<ItemPost> getAllItemPost();
    List<ItemPost> getListItemBySalePost(SalePost salePost);
    ItemPost createItemPost(ItemCreateDTO itemCreateDTO);
    ItemPost updateItemPost(ItemPost itemPost);
    boolean deleteItemPost(int itemID);
}
