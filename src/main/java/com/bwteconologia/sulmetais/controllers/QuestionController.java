package com.bwteconologia.sulmetais.controllers;

import com.bwteconologia.sulmetais.exceptions.QuestionAlreadyExistsException;
import com.bwteconologia.sulmetais.exceptions.QuestionNotFoundException;
import com.bwteconologia.sulmetais.models.AnswerModel;
import com.bwteconologia.sulmetais.models.QuestionModel;
import com.bwteconologia.sulmetais.models.ResponseObjectModel;
import com.bwteconologia.sulmetais.services.AnswerService;
import com.bwteconologia.sulmetais.services.QuestionService;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api")
public class QuestionController {

    @Autowired
    QuestionService questionService;
    @Autowired
    AnswerService answerService;

    @GetMapping(value = "/questions")
    public ResponseEntity<List<QuestionModel>> getAllQuestions(){
        List<QuestionModel> question = questionService.getAllQuestions();
        return ResponseEntity.ok(question);
    }

    @GetMapping(value = "/questions/{id}")
    public ResponseEntity<QuestionModel> findById(@PathVariable("id") Long id){
        QuestionModel question = questionService.findById(Math.toIntExact(id))
                .orElseThrow(() -> new QuestionNotFoundException("Question with " + id + " is Not Found!"));
                return ResponseEntity.ok().body(question);
    }
    @PostMapping(value = "/questions")
    public ResponseEntity<QuestionModel> addQuestion(@RequestBody QuestionModel question){
        Optional<QuestionModel> existsQuestion = questionService.findByDescription(question.getDescription());
        if(existsQuestion.isPresent()){
            throw new QuestionAlreadyExistsException("Question Already Exists");
        }
        QuestionModel savedQuestion = questionService.save(question);
        return ResponseEntity.ok(savedQuestion);
    }
    @PutMapping(value = "/questions/{id}")
    public ResponseEntity<QuestionModel> updateQuestion(@PathVariable("id") Long id, @RequestBody QuestionModel questionUpdated){
        QuestionModel question = questionService.findById(Math.toIntExact(id))
                .orElseThrow(() -> new QuestionNotFoundException("Question with " + id + " is Not Found!"));
        question.setType(questionUpdated.getType());
        question.setDescription(questionUpdated.getDescription());
        question.setUpdatedAt(new Date());

        QuestionModel updatedQuestion = questionService.save(question);
        return ResponseEntity.ok(updatedQuestion);
    }
    @DeleteMapping(value = "/questions/{id}")
    public ResponseEntity<ResponseObjectModel> deleteQuestion(@PathVariable("id") Long id){
        QuestionModel question = questionService.findById(Math.toIntExact(id))
                .orElseThrow(() ->new QuestionNotFoundException("Question with " + id + " is Not Found!"));
        questionService.deleteById(Math.toIntExact(id));
        ResponseObjectModel responseObjectModel = new ResponseObjectModel(id, "Question with ID :"+id+" is deleted");
        return ResponseEntity.ok(responseObjectModel);
    }

    @GetMapping("/questions/{id}/answers")
    public ResponseEntity<Map<String, Object>> getQuestionAndAnswersById(@PathVariable Long id) {
        Optional<QuestionModel> optionalQuestion = questionService.findById(Math.toIntExact(id));
        if (optionalQuestion.isPresent()) {
            QuestionModel question = optionalQuestion.get();
            List<AnswerModel> answers = question.getAnswers();

            Map<String, Object> result = new HashMap<>();
            result.put("question", question);
            result.put("answers", answers);

            return ResponseEntity.ok(result);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    }

