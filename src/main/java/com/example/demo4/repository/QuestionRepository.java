package com.example.demo4.repository;

import com.example.demo4.contant.QuestionType;
import com.example.demo4.domain.Category;
import com.example.demo4.domain.Question;
import com.example.demo4.domain.SubComment;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface QuestionRepository extends MongoRepository<Question, String> {
    List<Question> findQuestionBycategoryId(String categoryId);
    List<Question> findAllByOrderByCreateTimeDesc();
    List<Question> findQuestionsByQuestionType(QuestionType questionType);


}
