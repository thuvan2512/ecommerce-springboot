package com.thunv.ecommerceou.services;

import com.thunv.ecommerceou.models.pojo.Category;

import java.util.List;

public interface CategoryService {
    Category getCategoryByID(int categoryID);
    List<Category> getAllCategory();
    Category createCategory(Category category);
    Category updateCategory(Category category);
    boolean deleteCategory(int categoryID);
}
