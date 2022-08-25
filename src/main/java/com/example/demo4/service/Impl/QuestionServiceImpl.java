package com.example.demo4.service.Impl;

import com.example.demo4.contant.QuestionType;
import com.example.demo4.contant.Role;
import com.example.demo4.domain.*;
import com.example.demo4.exception.CustomException;
import com.example.demo4.repository.CategoryRepository;
import com.example.demo4.repository.QuestionRepository;
import com.example.demo4.request.question.CreateQuestionRequest;
import com.example.demo4.request.question.UpdateAnswerRequest;
import com.example.demo4.response.CategoryResponse;
import com.example.demo4.response.ObjectResponse;
import com.example.demo4.response.QuestionResponse;
import com.example.demo4.service.JwtService;
import com.example.demo4.service.QuestionService;
import com.example.demo4.utils.DateTimeUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class QuestionServiceImpl implements QuestionService {
    private final JwtService jwtService;
    private final CategoryRepository categoryRepository;
    private final QuestionRepository questionRepository;

    @Override
    public QuestionResponse insertQuestionByAdmin(String token, CreateQuestionRequest questionRequest) {
        Role role = Role.valueOf(jwtService.testRole(token));
        if (!role.equals(Role.ADMIN)) {
            throw new CustomException(ObjectResponse.STATUS_CODE_UNAUTHORIZED, ObjectResponse.MESSAGE_UNAUTHORIZED);
        }
        String uid = jwtService.parseTokenToId(token);
        String categoryId = questionRequest.getCategoryId();
        Category category = categoryRepository.findById(categoryId).get();
        Question question = new Question();
        question.setUserId(uid);
        question.setCategoryId(questionRequest.getCategoryId());
        question.setQuestionType(QuestionType.BY_ADMIN);
        question.setQuestion(questionRequest.getQuestion());
        question.setAnswer(questionRequest.getAnswer());
        question.setCreateTime(String.valueOf(DateTimeUtil.getCurrentTime()));
        questionRepository.insert(question);
        category.getQuestions().add(question);
        category.setQuestionNumber(category.getQuestions().size());
        categoryRepository.save(category);
        QuestionResponse response = QuestionResponse.builder()
                .createTime(question.getCreateTime())
                .lastUpdateTime(question.getLastUpdateTime())
                .userId(question.getUserId())
                .question(question.getQuestion())
                .questionType(question.getQuestionType())
                .id(question.getId())
                .build();
        return response;
    }

    @Override
    public QuestionResponse insertQuestionByUser(String token, CreateQuestionRequest questionRequest) {
        Role role = Role.valueOf(jwtService.testRole(token));
        if (!role.equals(Role.USER)) {
            throw new CustomException(ObjectResponse.STATUS_CODE_UNAUTHORIZED, ObjectResponse.MESSAGE_UNAUTHORIZED);
        }
        String uid = jwtService.parseTokenToId(token);
        Question question = new Question();
        question.setUserId(uid);
        question.setQuestionType(QuestionType.BY_USER);
        question.setQuestion(questionRequest.getQuestion());
        question.setCreateTime(String.valueOf(DateTimeUtil.getCurrentTime()));
        question.setLastUpdateTime(DateTimeUtil.getCurrentTime());
        questionRepository.insert(question);
        QuestionResponse response = QuestionResponse.builder()
                .createTime(question.getCreateTime())
                .lastUpdateTime(question.getLastUpdateTime())
                .userId(question.getUserId())
                .question(question.getQuestion())
                .questionType(question.getQuestionType())
                .id(question.getId())
                .build();
        return response;
    }

    @Override
    public List<Question> findAllQuestion(String token) {
        Role role = Role.valueOf(jwtService.testRole(token));
        if (role == null) {
            throw new CustomException(ObjectResponse.STATUS_CODE_UNAUTHORIZED, ObjectResponse.MESSAGE_UNAUTHORIZED);
        }
        return questionRepository.findAllByOrderByCreateTimeDesc();
    }

    @Override
    public List<Question> getListQuestion() {
        return questionRepository.findAll();
    }


    public QuestionResponse switchInsert(String token, CreateQuestionRequest questionRequest) {
        Role role = Role.valueOf(jwtService.testRole(token));
        if (role.equals(Role.ADMIN)) {
            return insertQuestionByAdmin(token, questionRequest);
        }
        return insertQuestionByUser(token, questionRequest);
    }

    @Override
    public Question answerQuestion(String id, String token, UpdateAnswerRequest updateAnswerRequest) {
        Question question = questionRepository.findById(id).get();
        if (question == null) {
            throw new CustomException(ObjectResponse.STATUS_CODE_NOT_FOUND, ObjectResponse.MESSAGE_OBJECT_NOT_FOUND + id);
        }
        Role role = Role.valueOf(jwtService.testRole(token));
        String userIdAnswer = jwtService.parseTokenToId(token);
        if (role.equals(Role.ADMIN)) {
            question.setAnswer(updateAnswerRequest.getAnswer());
            question.setAnswerById(userIdAnswer);
            question.setLastUpdateTime(System.currentTimeMillis());
            questionRepository.save(question);
            return question;
        } else {
            throw new CustomException(ObjectResponse.STATUS_CODE_UNAUTHORIZED, ObjectResponse.MESSAGE_UNAUTHORIZED_TO_ANSWER_QUESTION + id);
        }
    }

    @Override
    public void deleteQuestionById(String token, String id) {
        Role role = Role.valueOf(jwtService.testRole(token));
        if (!role.equals(Role.ADMIN)) {
            throw new CustomException(ObjectResponse.STATUS_CODE_UNAUTHORIZED, ObjectResponse.MESSAGE_UNAUTHORIZED);
        }
        Question deleteQuestion = findById(id);
        if (deleteQuestion != null) {
            questionRepository.deleteById(id);
        }

    }

    @Override
    public Question findById(String id) {
        return questionRepository.findById(id).get();
    }

    @Override
    public List<Question> findQuestionsByQuestionType(String token,QuestionType questionType) {
        Role role = Role.valueOf(jwtService.testRole(token));
        if(role == null){
            throw new CustomException(ObjectResponse.STATUS_CODE_UNAUTHORIZED,ObjectResponse.MESSAGE_UNAUTHORIZED);
        }
        return questionRepository.findQuestionsByQuestionType(questionType);
    }


}
