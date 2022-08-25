package com.example.demo4.request.question;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateQuestionRequest {
    private String question;
    private String answer;
    private String createTime;
    private String categoryId;
}
