package com.bwteconologia.sulmetais.repositories;

import com.bwteconologia.sulmetais.models.QuestionModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface QuestionRepository extends JpaRepository<QuestionModel, Integer> {

    Optional<QuestionModel> findByDescription(String description);
}
