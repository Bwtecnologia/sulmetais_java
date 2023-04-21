package com.bwteconologia.sulmetais.interfaces;

import com.bwteconologia.sulmetais.models.QuizModel;

import java.util.List;
import java.util.Optional;

public interface IQuiz {
    List<QuizModel> getAllQuestionnaires();

    Optional<QuizModel> findById(int id);

    QuizModel save(QuizModel quiz);

    void deleteById(int id);
}
