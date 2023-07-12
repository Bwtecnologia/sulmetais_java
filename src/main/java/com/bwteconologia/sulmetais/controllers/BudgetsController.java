package com.bwteconologia.sulmetais.controllers;


import com.bwteconologia.sulmetais.exceptions.BudgetsNotFoundException;
import com.bwteconologia.sulmetais.exceptions.QuizNotFoundException;
import com.bwteconologia.sulmetais.models.*;
import com.bwteconologia.sulmetais.services.BudgetsService;
import com.bwteconologia.sulmetais.services.QuizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class BudgetsController {

    @Autowired
    BudgetsService budgetsService;

    @Autowired
    QuizService quizService;

    @GetMapping("/budgets")
    List<BudgetsModel> findAllBudgets(){
        return budgetsService.getAllBudgets();
    }

    @GetMapping("/budgets/{id}")
    BudgetsModel findBudgetsById(@PathVariable Long id){
        Optional<BudgetsModel> budgetsModelOptional = budgetsService.findBudgetById(id);

        if(budgetsModelOptional.isEmpty()) throw new BudgetsNotFoundException("Budget with ID:" + id + " doesnt exist!");

        return budgetsModelOptional.get();
    }

    @PostMapping("/budgets")
    BudgetsModel getPreviewOfBudget(@RequestBody List<AnswerQuizModel> answers, @RequestParam(required = true) Long quizId){

        Optional<QuizModel> quizOptional = quizService.findById(Math.toIntExact(quizId));
        if(quizOptional.isEmpty()) throw new QuizNotFoundException("Quiz with ID:" + quizId + "doesnt exists");
        List<MaterialListModel> listMaterial = quizOptional.get().getMaterial();

        BudgetsModel budgetsModel = new BudgetsModel();
        List<MaterialBudgetModel> materialBudgetModelList = new ArrayList<MaterialBudgetModel>();

        for(MaterialListModel material : listMaterial) {
            MaterialBudgetModel materialBudgetModel = new MaterialBudgetModel(answers, material);

            materialBudgetModelList.add(materialBudgetModel);
        }

        budgetsModel.setMaterials(materialBudgetModelList);
        budgetsModel.setQuizId(quizOptional.get());
        return budgetsModel;
    }

    @PostMapping("/budgets/save")
    BudgetsModel setSaveOfBudget(@RequestBody BudgetsModel budgetModel, @RequestParam(required = true) Long quizId){
        BudgetsModel budgets = new BudgetsModel();
        Optional<QuizModel> quizOptional = quizService.findById(Math.toIntExact(quizId));
        if(quizOptional.isEmpty()) throw new QuizNotFoundException("Quiz with ID:" + quizId + "doesnt exists");
        List<MaterialListModel> listMaterial = quizOptional.get().getMaterial();

        budgets = budgetModel;

        budgets.setQuizId(quizOptional.get());

        return budgetsService.saveBudget(budgetModel);
    }

    @DeleteMapping("/budgets/{id}")
    String deleteBudget(@PathVariable Long id){

        Optional<BudgetsModel> budgetsModelOptional = budgetsService.findBudgetById(id);
        if(budgetsModelOptional.isEmpty()) throw new BudgetsNotFoundException("Budget with ID:" + id + " doesnt exist!");

        budgetsService.deleteBudget(id);

        return "Budget with ID: " + id + " has been removed!";
    }
}
