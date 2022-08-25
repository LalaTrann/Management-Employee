package com.example.demo4.response;

import com.example.demo4.contant.QuestionType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class QuestionResponse {

    private String id;

    private QuestionType questionType;
    private String userId;
    private String question;
    private String createTime;
    private Long lastUpdateTime;
}
