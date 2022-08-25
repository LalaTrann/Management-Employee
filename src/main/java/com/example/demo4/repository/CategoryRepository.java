package com.example.demo4.repository;

import com.example.demo4.domain.Category;
import com.example.demo4.response.CategoryResponse;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CategoryRepository extends MongoRepository<Category, String> {
    List<CategoryResponse> findAllByOrderByCreateTimeDesc();
  Category findByTitle(String title);
}
