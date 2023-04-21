package com.bwteconologia.sulmetais.services;

import com.bwteconologia.sulmetais.interfaces.IQuiz;
import com.bwteconologia.sulmetais.models.QuizModel;
import com.bwteconologia.sulmetais.repositories.QuizRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class QuizService implements IQuiz {

    @Autowired
    QuizRepository quizRepository;
    @Override
    public List<QuizModel> getAllQuestionnaires() {
        return quizRepository.findAll();
    }

    @Override
    public Optional<QuizModel> findById(int id) {
        return quizRepository.findById(id);
    }

    @Override
    public QuizModel save(QuizModel quiz) {
        return quizRepository.save(quiz);
    }

    @Override
    public void deleteById(int id) {
        quizRepository.deleteById(id);
    }
}
