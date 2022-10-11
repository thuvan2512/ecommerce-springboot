package com.thunv.ecommerceou.services.impl;

import com.thunv.ecommerceou.dto.ItemCreateDTO;
import com.thunv.ecommerceou.dto.ItemUpdateDTO;
import com.thunv.ecommerceou.models.pojo.ItemPost;
import com.thunv.ecommerceou.models.pojo.SalePost;
import com.thunv.ecommerceou.repositories.ItemRepository;
import com.thunv.ecommerceou.services.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ItemServiceImpl implements ItemService {
    @Autowired
    private ItemRepository itemRepository;
    @Override
    public ItemPost getItemPostByID(int itemID) throws RuntimeException {
        return this.itemRepository.findById(itemID).orElseThrow(() ->
                new RuntimeException("Can not find item with id = " + itemID));
    }

    @Override
    public List<ItemPost> getAllItemPost() throws RuntimeException {
        try {
            return this.itemRepository.findAll();
        }catch (Exception ex){
            String error_ms = ex.getMessage();
            throw new RuntimeException(error_ms);
        }
    }

    @Override
    public List<ItemPost> getListItemBySalePost(SalePost salePost) throws RuntimeException{
        try {
            return salePost.getItemPostSet().stream().toList();
        }catch (Exception ex){
            String error_ms = ex.getMessage();
            throw new RuntimeException(error_ms);
        }
    }

    @Override
    public ItemPost createItemPost(ItemCreateDTO itemCreateDTO) throws RuntimeException {
        try {
            ItemPost itemPost = new ItemPost();
            itemCreateDTO.loadItemFromDTO(itemPost);
            itemPost.setIsActive(1);
            return this.itemRepository.save(itemPost);
        }catch (Exception ex){
            String error_ms = ex.getMessage();
            throw new RuntimeException(error_ms);
        }
    }

    @Override
    public ItemPost updateItemPost(ItemPost itemPost) throws RuntimeException {
        try {
            return this.itemRepository.save(itemPost);
        }catch (Exception ex){
            String error_ms = ex.getMessage();
            throw new RuntimeException(error_ms);
        }
    }

    @Override
    public boolean deleteItemPost(int itemID) throws RuntimeException {
        try {
            if (this.itemRepository.existsById(itemID) == false){
                throw new RuntimeException("Item does not exist");
            }
            this.itemRepository.deleteById(itemID);
            return true;
        }catch (Exception ex){
            String error_ms = ex.getMessage();
            throw new RuntimeException(error_ms);
        }
    }
}
