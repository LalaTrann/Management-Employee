package com.example.demo4.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Document(collection = "category")
public class Category {
    private String id;
    private String title;
    private String banner;
    @JsonProperty("create_time")
    @Field("create_time")
    private Long createTime;
    @JsonProperty("question_number")
    @Field("question_Number")
    private int questionNumber;
    private List<Question> questions = new ArrayList<>();
}
