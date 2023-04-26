package com.bwteconologia.sulmetais.services;

import com.bwteconologia.sulmetais.interfaces.IBudget;
import com.bwteconologia.sulmetais.models.BudgetModel;
import com.bwteconologia.sulmetais.repositories.BudgetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public class BudgetService implements IBudget {

    @Autowired
    BudgetRepository budgetRepository;
    @Override
    public List<BudgetModel> getAllBudgets() {
        return budgetRepository.findAll();
    }

    @Override
    public Optional<BudgetModel> findById(int id) {
        return budgetRepository.findById(id);
    }

    @Override
    public BudgetModel save(BudgetModel budget) {
        return budgetRepository.save(budget);
    }

    @Override
    public void deleteById(int id) {
        budgetRepository.deleteById(id);
    }
}
