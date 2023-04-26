package com.bwteconologia.sulmetais.repositories;


import com.bwteconologia.sulmetais.models.BudgetModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BudgetRepository extends JpaRepository<BudgetModel, Integer> {
}
