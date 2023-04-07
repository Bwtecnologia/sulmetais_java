package com.bwteconologia.sulmetais.interfaces;

import com.bwteconologia.sulmetais.models.AnswerModel;
import java.util.List;
import java.util.Optional;

public interface IAnswer {

    List<AnswerModel> getAllAnswers();

    Optional<AnswerModel> findById(int id);

    AnswerModel save(AnswerModel group);
    Optional<AnswerModel> findByDescription(String description);

    Optional<AnswerModel> findByQuestion(String question);

    void deleteById(int id);
}
