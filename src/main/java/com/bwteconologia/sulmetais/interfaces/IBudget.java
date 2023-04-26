package com.bwteconologia.sulmetais.interfaces;

import com.bwteconologia.sulmetais.models.BudgetModel;

import java.util.List;
import java.util.Optional;

public interface IBudget {
    List<BudgetModel> getAllBudgets();
    Optional<BudgetModel> findById(int id);
    BudgetModel save(BudgetModel Budget);
    void deleteById(int id);
}
