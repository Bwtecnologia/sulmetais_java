package com.bwteconologia.sulmetais.controllers;

import com.bwteconologia.sulmetais.exceptions.AnswerAlreadyExistsException;
import com.bwteconologia.sulmetais.exceptions.AnswerNotFoundException;
import com.bwteconologia.sulmetais.exceptions.GroupNotFoundException;
import com.bwteconologia.sulmetais.exceptions.QuestionNotFoundException;
import com.bwteconologia.sulmetais.models.AnswerModel;
import com.bwteconologia.sulmetais.models.QuestionModel;
import com.bwteconologia.sulmetais.models.ResponseObjectModel;
import com.bwteconologia.sulmetais.services.AnswerService;
import com.bwteconologia.sulmetais.services.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class AnswerController {
    @Autowired
    AnswerService answerService;
    @Autowired
    QuestionService questionService;


    @GetMapping(value = "/answers")
    public ResponseEntity<List<AnswerModel>> getAllAnswers() {
        List<AnswerModel> answers = answerService.getAllAnswers();
        return ResponseEntity.ok(answers);
    }

    @GetMapping(value = "/answers/{id}")
    public ResponseEntity<AnswerModel> findById(@PathVariable("id") Long id) {
        AnswerModel answer = answerService.findById(Math.toIntExact(id))
                .orElseThrow(() -> new GroupNotFoundException("Answer with " + id + " is Not Found!"));
        return ResponseEntity.ok().body(answer);
    }

    @PostMapping(value = "/answers")
    public ResponseEntity<AnswerModel> addAnswer(@RequestBody AnswerModel answer, @RequestParam int questionId) {
        Optional<QuestionModel> optionalQuestion = questionService.findById(Math.toIntExact(questionId));
        if (!optionalQuestion.isPresent()) {
            throw new QuestionNotFoundException("Question not found");
        }
        QuestionModel question = optionalQuestion.get();
        answer.setQuestion(question);
        AnswerModel savedAnswers = answerService.save(answer);
        return ResponseEntity.ok(savedAnswers);
    }

    @PutMapping(value = "/answers/{id}")
    public ResponseEntity<AnswerModel> updateAnswer(@PathVariable("id") Long id, @RequestBody AnswerModel answersUpdate) {
        AnswerModel answer = answerService.findById(Math.toIntExact(id))
                .orElseThrow(() -> new AnswerNotFoundException("Answer with " + id + " is Not Found!"));
        answer.setFormula(answersUpdate.getFormula());
        answer.setDescription(answersUpdate.getDescription());
        answer.setValue(answersUpdate.getValue());
        answer.setUpdatedAt(new Date());

        AnswerModel updatedAnswer = answerService.save(answer);
        return ResponseEntity.ok(updatedAnswer);
    }
    @DeleteMapping(value = "/answers/{id}")
    public ResponseEntity<ResponseObjectModel> deleteAnswer(@PathVariable("id") Long id){
        AnswerModel answer = answerService.findById(Math.toIntExact(id))
                .orElseThrow(() -> new GroupNotFoundException("Answer with " + id + " is Not Found!"));
        answerService.deleteById(Math.toIntExact(answer.getId()));
        ResponseObjectModel responseObjectModel = new ResponseObjectModel(id, "Answer with ID :"+id+" is deleted");
        return ResponseEntity.ok(responseObjectModel);
    }
}
