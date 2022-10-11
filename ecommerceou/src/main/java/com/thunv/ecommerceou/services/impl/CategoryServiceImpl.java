package com.thunv.ecommerceou.services.impl;

import com.thunv.ecommerceou.models.pojo.Category;
import com.thunv.ecommerceou.repositories.CategoryRepository;
import com.thunv.ecommerceou.services.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public Category getCategoryByID(int categoryID) throws RuntimeException{
        return this.categoryRepository.findById(categoryID).orElseThrow(() ->
                new RuntimeException("Can not find category with id = " + categoryID));
    }

    @Override
    public List<Category> getAllCategory() throws RuntimeException{
        try {
            return this.categoryRepository.findAll();
        }catch (Exception ex){
            String error_ms = ex.getMessage();
            throw new RuntimeException(error_ms);
        }
    }

    @Override
    public Category createCategory(Category category) throws RuntimeException{
        try {
            return this.categoryRepository.save(category);
        }catch (Exception ex){
            String error_ms = ex.getMessage();
            throw new RuntimeException(error_ms);
        }
    }

    @Override
    public Category updateCategory(Category category)  throws RuntimeException{
        try {
            return this.categoryRepository.save(category);
        }catch (Exception ex){
            String error_ms = ex.getMessage();
            throw new RuntimeException(error_ms);
        }
    }

    @Override
    public boolean deleteCategory(int categoryID) throws RuntimeException{
        try {
            if (this.categoryRepository.existsById(categoryID) == false){
                throw new RuntimeException("Category does not exist");
            }
            this.categoryRepository.deleteById(categoryID);
            return true;
        }catch (Exception ex){
            String error_ms = ex.getMessage();
            throw new RuntimeException(error_ms);
        }
    }
}
