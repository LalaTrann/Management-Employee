package com.example.demo4.request.question;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateAnswerRequest {
    @NotBlank(message = "NOT BLANK")
    private String answer;
}
