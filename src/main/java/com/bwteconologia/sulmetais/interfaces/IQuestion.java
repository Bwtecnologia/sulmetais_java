package com.bwteconologia.sulmetais.interfaces;

import com.bwteconologia.sulmetais.models.QuestionModel;

import java.util.List;
import java.util.Optional;

public interface IQuestion {

    List<QuestionModel> getAllQuestions();

    Optional<QuestionModel> findById(int id);

    QuestionModel save(QuestionModel product);
    Optional<QuestionModel> findByDescription(String description);

    void deleteById(int id);
}
