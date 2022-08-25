package com.example.demo4.controller;

import com.example.demo4.domain.Category;
import com.example.demo4.domain.Events;
import com.example.demo4.request.category.CreateCategoryRequest;
import com.example.demo4.request.category.UpdateCategoryRequest;
import com.example.demo4.response.CategoryResponse;
import com.example.demo4.response.ObjectResponse;
import com.example.demo4.service.CategorySevice;
import com.example.demo4.service.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController()
@RequestMapping("demo/v1/category")
@RequiredArgsConstructor
public class CategoryController {
    private final CategorySevice categorySevice;
    private final JwtService jwtService;
    @PostMapping()
    public ResponseEntity<ObjectResponse> createCategory(@RequestHeader("Authorization") String token, @RequestBody CreateCategoryRequest createCategoryRequest) {

        Category insertCategory = categorySevice.insertCategory(token, createCategoryRequest);
        return ResponseEntity.ok(new ObjectResponse(HttpStatus.CREATED.value(),
                "CREAT BOX SUCCESS",
                insertCategory));
    }



    @DeleteMapping("/{id}")
    public ResponseEntity<ObjectResponse> deleteCategory(@PathVariable(name = "id") String id,
                                                     @RequestHeader("Authorization") String token) {
        categorySevice.deleteCategoryById(token,id);
        return ResponseEntity.status(HttpStatus.OK).body(new ObjectResponse(HttpStatus.OK.value(),
                "DELETE CATEGORY WITH ID: " + id + " SUCCESS",
                null));
    }
    @GetMapping
    public ResponseEntity<?> getListCategory(){
        List<CategoryResponse> listCategory = categorySevice.findAllCategory();
        return ResponseEntity.status(HttpStatus.OK).body(new ObjectResponse(HttpStatus.OK.value(), "GET LIST CATEGORY",listCategory));
    }
    @PutMapping("{id}")
    public ResponseEntity<?> updateCategory(@PathVariable(name = "id")String id,
                                            @RequestHeader("Authorization") String token,
                                            @Valid @RequestBody UpdateCategoryRequest updateCategoryRequest) {
        Category category = categorySevice.save(id, token, updateCategoryRequest);
        return ResponseEntity.ok(
                new ObjectResponse(
                        HttpStatus.OK.value(),
                        "Update Category Complete",
                        category)
        );
    }

}
