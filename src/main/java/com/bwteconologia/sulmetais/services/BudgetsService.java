package com.bwteconologia.sulmetais.services;


import com.bwteconologia.sulmetais.models.AnswerModel;
import com.bwteconologia.sulmetais.models.BudgetsModel;
import com.bwteconologia.sulmetais.repositories.BudgetsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service

public class BudgetsService {

    @Autowired
    BudgetsRepository budgetsRepository;

    public List<BudgetsModel> getAllBudgets(){
        return budgetsRepository.findAll();
    }

    public Optional<BudgetsModel> findBudgetById(Long id) {
        return budgetsRepository.findById(id);
    }

    public BudgetsModel saveBudget(BudgetsModel budgetsModel){
        return budgetsRepository.save(budgetsModel);
    }

    public void deleteBudget(Long id){
        budgetsRepository.deleteById(id);
    }
}
