package com.bwteconologia.sulmetais.controllers;

import com.bwteconologia.sulmetais.exceptions.AnswerNotFoundException;
import com.bwteconologia.sulmetais.exceptions.ProductNotFoundException;
import com.bwteconologia.sulmetais.exceptions.QuestionNotFoundException;
import com.bwteconologia.sulmetais.exceptions.QuizNotFoundException;
import com.bwteconologia.sulmetais.exceptions.group_color.GroupColorNotExistsException;
import com.bwteconologia.sulmetais.models.*;
import com.bwteconologia.sulmetais.services.ProductService;
import com.bwteconologia.sulmetais.services.QuestionService;
import com.bwteconologia.sulmetais.services.QuizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api")
public class QuizController {
    @Autowired
    QuizService quizService;


    @Autowired
    ProductService productService;

    @Autowired
    QuestionService questionService;


    @GetMapping(value = "/quiz")
    public ResponseEntity<List<QuizModel>> getAllQuiz() {
        List<QuizModel> quiz = quizService.getAllQuestionnaires();
        return ResponseEntity.ok(quiz);
    }

    @PostMapping(value = "/quiz")
    public ResponseEntity<QuizModel> createQuestionnaire(@RequestBody QuizModel quiz) {

        int productId = quiz.getProduct().getId();


        Optional<ProductModel> productOptional = productService.findById(Math.toIntExact(productId));


        if (!productOptional.isPresent()) {
            throw new ProductNotFoundException("Product not found");
        }

        Set<QuestionModel> questionModelList = new HashSet<>();

        for (QuestionModel questionModel : quiz.getQuestions()) {
            Optional<QuestionModel> questionModelOptional =
                    questionService.findById(Math.toIntExact(questionModel.getId()));

            if (questionModelOptional.isEmpty())
                throw new QuestionNotFoundException("Question id:" + questionModel.getId() + " not found");

            questionModelList.add(questionModelOptional.get());
        }



        ProductModel product = productOptional.get();
        quiz.setMaterial(quiz.getMaterial());
        quiz.setProduct(product);
        quiz.setQuestions(questionModelList);

        quizService.save(quiz);

        return ResponseEntity.status(HttpStatus.CREATED).body(quiz);
    }

    @GetMapping(value = "/quiz/{id}")
    public ResponseEntity<QuizModel> findQuizById(@PathVariable("id") Long id) {
        QuizModel quiz = quizService.findById(Math.toIntExact(id))
                .orElseThrow(() -> new QuizNotFoundException("Quiz not found"));
        return ResponseEntity.ok().body(quiz);
    }

    @PutMapping("/quiz/{id}")
    public ResponseEntity<QuizModel> updateQuiz(@PathVariable Long id, @RequestBody QuizModel quiz) {
        Optional<QuizModel> quizOptional = quizService.findById(Math.toIntExact(id));

        if (!quizOptional.isPresent()) {
            throw new QuizNotFoundException("Quiz not found");
        }

        QuizModel existingQuiz = quizOptional.get();

        existingQuiz.setProduct(quiz.getProduct());
        existingQuiz.setQuestions(quiz.getQuestions());
        existingQuiz.setUpdatedAt(new Date());

        QuizModel updatedQuiz = quizService.save(existingQuiz);

        return ResponseEntity.ok(updatedQuiz);
    }

    @DeleteMapping("/quiz/{id}")
    public ResponseEntity<ResponseObjectModel> deleteQuiz(@PathVariable("id") Long id) {
        QuizModel quiz = quizService.findById(Math.toIntExact(id))
                .orElseThrow(() -> new QuestionNotFoundException("Question with " + id + " is Not Found!"));
        quizService.deleteById(Math.toIntExact(id));
        ResponseObjectModel responseObjectModel = new ResponseObjectModel(id, "Question with ID :" + id + " is deleted");
        return ResponseEntity.ok(responseObjectModel);
    }
}
