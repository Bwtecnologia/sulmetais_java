package com.bwteconologia.sulmetais.repositories;

import com.bwteconologia.sulmetais.models.QuestionModel;
import com.bwteconologia.sulmetais.models.QuestionPositionModel;
import com.bwteconologia.sulmetais.models.QuizModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

@Repository
public interface QuestionPositionRepository extends JpaRepository<QuestionPositionModel, Long> {
    Optional<List<QuestionPositionModel>> findAllByQuizId(Long quizId);

    void deleteAllByQuiz(QuizModel quiz);
    Optional<QuestionPositionModel> findByPositionAndQuizAndQuestion(int position, QuizModel quiz, QuestionModel question);
}
