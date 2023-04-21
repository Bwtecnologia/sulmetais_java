package com.bwteconologia.sulmetais.repositories;

import com.bwteconologia.sulmetais.models.QuestionModel;
import com.bwteconologia.sulmetais.models.QuizModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuizRepository extends JpaRepository<QuizModel, Integer> {

}
