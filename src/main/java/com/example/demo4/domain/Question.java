package com.example.demo4.domain;

import com.example.demo4.contant.QuestionType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

import java.util.PrimitiveIterator;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Question {
    @Id
    private String id;
    private String categoryId;
    private QuestionType questionType;
    private String userId;
    private String question;
    private String answer;
    private String createTime;
    private String answerById;
    private Long lastUpdateTime;
    private String askById;



    }

