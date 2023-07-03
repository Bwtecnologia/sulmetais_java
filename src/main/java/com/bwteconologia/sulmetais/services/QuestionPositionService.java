package com.bwteconologia.sulmetais.services;

import com.bwteconologia.sulmetais.models.QuestionModel;
import com.bwteconologia.sulmetais.models.QuestionPositionModel;
import com.bwteconologia.sulmetais.models.QuizModel;
import com.bwteconologia.sulmetais.repositories.QuestionPositionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class QuestionPositionService {

    @Autowired
    QuestionPositionRepository questionPositionRepository;

    public QuestionPositionModel save(QuestionPositionModel questionPositionModel) {
        return questionPositionRepository.save(questionPositionModel);
    }

    public Optional<List<QuestionPositionModel>> findAllQuestionsByQuizId(Long quizId) {
        return questionPositionRepository.findAllByQuizId(quizId);
    }

    public void deleteById(Long id) {
        questionPositionRepository.deleteById(id);
    }

    public List<QuestionPositionModel> findAll() {
        return questionPositionRepository.findAll();
    }

    public Optional<QuestionPositionModel> findByPositionAndQuizAndQuestion(int position, QuizModel quiz) {
        return questionPositionRepository.findByPositionAndQuiz(position, quiz);
    }
}
