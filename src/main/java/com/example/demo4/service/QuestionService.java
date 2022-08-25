package com.example.demo4.service;

import com.example.demo4.contant.QuestionType;
import com.example.demo4.domain.Category;
import com.example.demo4.domain.Question;
import com.example.demo4.request.question.CreateQuestionRequest;
import com.example.demo4.request.question.UpdateAnswerRequest;
import com.example.demo4.response.QuestionResponse;

import java.util.List;

public interface QuestionService {
    QuestionResponse insertQuestionByAdmin(String token, CreateQuestionRequest questionRequest);
    QuestionResponse insertQuestionByUser(String token, CreateQuestionRequest questionRequest);
    List<Question> findAllQuestion(String token);
    List<Question> getListQuestion();


    QuestionResponse switchInsert(String token, CreateQuestionRequest questionRequest);
    Question answerQuestion(String id, String token, UpdateAnswerRequest updateAnswerRequest);
//    Question findById(String id);
    void deleteQuestionById(String token,String id);
    List<Question> findQuestionsByQuestionType(String token,QuestionType questionType);

    Question findById(String id);

}
