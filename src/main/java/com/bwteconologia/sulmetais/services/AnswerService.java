package com.bwteconologia.sulmetais.services;

import com.bwteconologia.sulmetais.interfaces.IAnswer;
import com.bwteconologia.sulmetais.models.AnswerModel;
import com.bwteconologia.sulmetais.repositories.AnswerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public class AnswerService implements IAnswer {

    @Autowired
    AnswerRepository answerRepository;
    @Override
    public List<AnswerModel> getAllAnswers() {
        return answerRepository.findAll();
    }

    @Override
    public Optional<AnswerModel> findById(int id) {
        return answerRepository.findById(id);
    }

    @Override
    public AnswerModel save(AnswerModel answer) {
        return answerRepository.save(answer);
    }

    @Override
    public Optional<AnswerModel> findByDescription(String description) {
        return answerRepository.findByDescription(description);
    }

    @Override
    public Optional<AnswerModel> findByQuestion(String question) {
        return answerRepository.findByQuestion(question);
    }

    @Override
    public void deleteById(int id) {
        answerRepository.deleteById(id);
    }
}
