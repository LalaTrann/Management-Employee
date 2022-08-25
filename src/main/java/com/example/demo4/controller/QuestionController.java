package com.example.demo4.controller;

import com.example.demo4.contant.QuestionType;
import com.example.demo4.contant.Role;
import com.example.demo4.domain.Category;
import com.example.demo4.domain.Question;
import com.example.demo4.request.question.CreateQuestionRequest;
import com.example.demo4.request.question.UpdateAnswerRequest;
import com.example.demo4.response.ObjectResponse;
import com.example.demo4.response.QuestionResponse;
import com.example.demo4.service.JwtService;
import com.example.demo4.service.QuestionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController()
@RequestMapping("demo/v1/question")
@RequiredArgsConstructor
public class QuestionController {
    private final JwtService jwtService;
    private final QuestionService questionService;
    @PostMapping()
    public ResponseEntity<ObjectResponse> createQuestion(@RequestHeader("Authorization") String token,
                                                           @Valid @RequestBody CreateQuestionRequest createQuestionRequest){
        Role role = Role.valueOf(jwtService.testRole(token));
        if(role != null){
            QuestionResponse question = questionService.switchInsert(token,createQuestionRequest);

            return ResponseEntity.ok(new ObjectResponse(HttpStatus.CREATED.value(),
                    "CREATE QUESTION SUCCESS",
                    question));
        }
        return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE)
                .body(new ObjectResponse(HttpStatus.NO_CONTENT.value(),
                        "CREATE QUESTION FAILED",
                        null));
    }
    @GetMapping
    public ResponseEntity<?> getListQuestion(@RequestHeader("Authorization") String token){
        List<Question> listQuestion = questionService.findAllQuestion(token);
        return ResponseEntity.status(HttpStatus.OK).body(new ObjectResponse(HttpStatus.OK.value(), "GET LIST QUESTION",listQuestion));
    }
    @GetMapping("/user")
    public List<Question> getListQuestionType(@RequestHeader("Authorization") String token,@RequestParam(value = "questionType",defaultValue = "BY_USER") QuestionType questionType){
        List<Question> questionList = questionService.findQuestionsByQuestionType(token,questionType);
        return questionList;
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<ObjectResponse> deleteQuestion(@PathVariable(name = "id") String id,
                                                         @RequestHeader("Authorization") String token) {
        questionService.deleteQuestionById(token,id);
        return ResponseEntity.status(HttpStatus.OK).body(new ObjectResponse(HttpStatus.OK.value(),
                "DELETE QUESTION WITH ID: " + id + " SUCCESS",
                null));
    }
    @PutMapping("answer/{id}")
    public ResponseEntity<ObjectResponse> answerQuestion(@PathVariable(name = "id") String id ,
                                                         @RequestHeader("Authorization") String token,
                                                         @Valid @RequestBody UpdateAnswerRequest updateAnswerRequest) {
        Question question = questionService.answerQuestion(id, token, updateAnswerRequest);
        return ResponseEntity.ok(new ObjectResponse(HttpStatus.OK.value(), "Answer question success", question));
    }

}
