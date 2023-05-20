package com.bwteconologia.sulmetais.controllers;


import com.bwteconologia.sulmetais.exceptions.ProductNotFoundException;
import com.bwteconologia.sulmetais.exceptions.QuestionNotFoundException;
import com.bwteconologia.sulmetais.exceptions.QuizNotFoundException;
import com.bwteconologia.sulmetais.exceptions.material.MaterialListNotFoundException;
import com.bwteconologia.sulmetais.exceptions.material.MaterialListQuestion2IsNullException;
import com.bwteconologia.sulmetais.models.*;
import com.bwteconologia.sulmetais.services.MaterialListService;
import com.bwteconologia.sulmetais.services.ProductService;
import com.bwteconologia.sulmetais.services.QuestionService;
import com.bwteconologia.sulmetais.services.QuizService;
import jakarta.websocket.server.PathParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class MaterialListController {

    @Autowired
    MaterialListService materialListService;

    @Autowired
    QuizService quizService;

    @Autowired
    ProductService productService;

    @Autowired
    QuestionService questionService;

    @GetMapping("/material")
    List<MaterialListModel> findAll() {
        return materialListService.findAll();
    }

    @GetMapping("/material/{id}")
    MaterialListModel findById(@PathVariable Long id){
        Optional<MaterialListModel> materialListModelOptional = materialListService.findById(id);
        if(materialListModelOptional.isEmpty()) throw new MaterialListNotFoundException("Material List with id: " + id + " doesnt exist!");

        return materialListModelOptional.get();
    }

    @PostMapping("/material")
    MaterialListModel save(
            @RequestBody MaterialListModel material,
//            @RequestParam Long quizId,
            @RequestParam Long productId,
            @RequestParam(required = false) Long itemSubId) {

//        Optional<QuizModel> quizModelOptional = quizService.findById(Math.toIntExact(quizId));
//        if(quizModelOptional.isEmpty()) throw new QuizNotFoundException("Quiz doesnt exists!");
//        material.setQuiz(quizModelOptional.get());


        Optional<ProductModel> productModelOptional = productService.findById(Math.toIntExact(productId));
        if(productModelOptional.isEmpty()) throw new ProductNotFoundException("Product doesnt exists!");
        material.setProduct(productModelOptional.get());


        if (itemSubId!=null){
            Optional<QuestionModel> questionModelOptional = questionService.findById(Math.toIntExact(itemSubId));
            if (questionModelOptional.isEmpty()) throw new QuestionNotFoundException("Questions doesnt exists!");
            material.setItemSubstitute(questionModelOptional.get());
        }

        List<FormulaMaterialModel>formulaList = material.getFormula();

//        if(formulaList.size() == 1){
//            if(formulaList.get(0).getQuestion2() == null) throw new MaterialListQuestion2IsNullException
//                    ("If you have only 1 formula you cannot have only one question!");
//        }


        return materialListService.save(material);
    }

    @PutMapping("/material/{id}")
    MaterialListModel update(@RequestBody MaterialListModel material,
                             @PathVariable Long id,
//                             @RequestParam Long quizId,
                             @RequestParam Long productId,
                             @RequestParam(required = false) Long itemSubId){

        Optional<MaterialListModel> materialListModelOptional = materialListService.findById(id);
        if(materialListModelOptional.isEmpty()) throw new MaterialListNotFoundException("Material List with id: " + id + " doesnt exist!");

//        Optional<QuizModel> quizModelOptional = quizService.findById(Math.toIntExact(quizId));
//        if(quizModelOptional.isEmpty()) throw new QuizNotFoundException("Quiz doesnt exists!");
//        material.setQuiz(quizModelOptional.get());

        Optional<ProductModel> productModelOptional = productService.findById(Math.toIntExact(productId));
        if(productModelOptional.isEmpty()) throw new ProductNotFoundException("Product doesnt exists!");
        material.setProduct(productModelOptional.get());


        if (itemSubId!=null){
            Optional<QuestionModel> questionModelOptional = questionService.findById(Math.toIntExact(itemSubId));
            if (questionModelOptional.isEmpty()) throw new QuestionNotFoundException("Questions doesnt exists!");
            material.setItemSubstitute(questionModelOptional.get());
        }

        List<FormulaMaterialModel>formulaList = material.getFormula();

        if(formulaList.size() == 1){
            if(formulaList.get(0).getQuestion2() == null) throw new MaterialListQuestion2IsNullException
                    ("If you have only 1 formula you cannot have only one question!");
        }

        material.setId(id);
        return materialListService.save(material);
    }

    @DeleteMapping("/materia/{id}")
    String deleteById(@PathVariable Long id){

        Optional<MaterialListModel> materialListModelOptional = materialListService.findById(id);
        if(materialListModelOptional.isEmpty()) throw new MaterialListNotFoundException("Material List with id: " + id + " doesnt exist!");

        materialListService.deleteById(id);
        return "Material List with id: " + id + " has been deleted!";
    }
}
