package com.bwteconologia.sulmetais.repositories;

import com.bwteconologia.sulmetais.models.AnswerModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AnswerRepository extends JpaRepository<AnswerModel, Integer> {
    Optional<AnswerModel> findByDescription(String description);

    Optional<AnswerModel> findByQuestion(String question);

}
