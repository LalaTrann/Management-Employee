package com.example.demo4.service.Impl;

import com.example.demo4.contant.Role;
import com.example.demo4.domain.Category;
import com.example.demo4.domain.Question;
import com.example.demo4.domain.SubComment;
import com.example.demo4.exception.CustomException;
import com.example.demo4.repository.CategoryRepository;
import com.example.demo4.repository.QuestionRepository;
import com.example.demo4.request.category.CreateCategoryRequest;
import com.example.demo4.request.category.UpdateCategoryRequest;
import com.example.demo4.response.CategoryResponse;
import com.example.demo4.response.ListCategoryResponse;
import com.example.demo4.response.ObjectResponse;
import com.example.demo4.response.QuestionResponse;
import com.example.demo4.service.CategorySevice;
import com.example.demo4.service.JwtService;
import com.example.demo4.utils.DateTimeUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategorySevice {
    private final JwtService jwtService;
    private final CategoryRepository categoryRepository;
    private final QuestionRepository questionRepository;

    @Override
    public Category insertCategory(String token, CreateCategoryRequest createCategoryRequest) {
        Role role = Role.valueOf(jwtService.testRole(token));
        if (isTitleDuplicate(createCategoryRequest.getTitle())) {
            throw new CustomException(ObjectResponse.STATUS_CODE_BAD_REQUEST, ObjectResponse.MESSAGE_TITLE_EXISTED);
        }
        if (role.equals(Role.ADMIN)) {
            Category category = new Category();
            category.setTitle(createCategoryRequest.getTitle());
            category.setBanner(createCategoryRequest.getBanner());
            category.setCreateTime(DateTimeUtil.getCurrentTime());
            return categoryRepository.insert(category);
        }
        throw new CustomException(ObjectResponse.STATUS_CODE_UNAUTHORIZED, ObjectResponse.MESSAGE_UNAUTHORIZED);
    }

    @Override
    public Category save(String id, String token, UpdateCategoryRequest updateCategoryRequest) {
        Category categoryToUpdate = categoryRepository.findById(id).get();
        if (categoryToUpdate == null) {
            throw new CustomException(ObjectResponse.STATUS_CODE_NOT_FOUND, ObjectResponse.MESSAGE_EVENT_NOT_FOUND + id);
        }
        Role role = Role.valueOf(jwtService.testRole(token));
        if (!role.equals(Role.ADMIN)) {
            throw new CustomException(ObjectResponse.STATUS_CODE_UNAUTHORIZED, ObjectResponse.MESSAGE_UNAUTHORIZED);
        }
        if (!updateCategoryRequest.getBanner().isBlank()) {
            categoryToUpdate.setBanner(updateCategoryRequest.getBanner());
        }
        if (!updateCategoryRequest.getTitle().isBlank()) {
            categoryToUpdate.setTitle(updateCategoryRequest.getTitle());
        }
        String userIdUpdate = jwtService.parseTokenToId(token);

        categoryRepository.save(categoryToUpdate);
        return categoryToUpdate;
    }


    @Override
    public void deleteCategoryById(String token, String id) {
        Role role = Role.valueOf(jwtService.testRole(token));
        if (!role.equals(Role.ADMIN)) {
            throw new CustomException(ObjectResponse.STATUS_CODE_UNAUTHORIZED, ObjectResponse.MESSAGE_UNAUTHORIZED);
        }
        Category deleteCategory = findById(id);
        if (deleteCategory != null) {
            List<Question> questions = deleteCategory.getQuestions();
            questionRepository.deleteAll(questions);
            categoryRepository.deleteById(id);
        }

    }
    @Override
    public Category findById(String id) {
        return categoryRepository.findById(id).get();
    }

    @Override
    public List<Category> getListCategory() {
        return categoryRepository.findAll();
    }



    @Override
    public List<CategoryResponse> findAllCategory() {
        return categoryRepository.findAllByOrderByCreateTimeDesc();
    }


    @Override
    public Category findByTitle(String title) {
        return categoryRepository.findByTitle(title);
    }

    private boolean isTitleDuplicate(String title){
        Category category = findByTitle(title);
        if(category != null){
            return true;
        }else {
            return false;
        }
    }


}
