package com.bwteconologia.sulmetais.repositories;

import com.bwteconologia.sulmetais.models.BudgetsModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface BudgetsRepository extends JpaRepository<BudgetsModel, Long> {
}
