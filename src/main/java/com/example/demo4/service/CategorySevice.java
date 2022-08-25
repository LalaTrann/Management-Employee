package com.example.demo4.service;

import com.example.demo4.domain.Category;
import com.example.demo4.request.category.CreateCategoryRequest;
import com.example.demo4.request.category.UpdateCategoryRequest;
import com.example.demo4.response.CategoryResponse;
import com.example.demo4.response.ListCategoryResponse;
import org.springframework.stereotype.Service;

import java.util.List;



public interface CategorySevice {
    Category insertCategory(String token, CreateCategoryRequest createCategoryRequest);

    Category save(String id, String token, UpdateCategoryRequest updateCategoryRequest);

    void deleteCategoryById(String token,String id);
//    Category getEventStatus(Category category);
//    List<Category> getListEvents();
    Category findById(String id);
    List<Category> getListCategory();


    List<CategoryResponse> findAllCategory();
    Category findByTitle(String title);

}
