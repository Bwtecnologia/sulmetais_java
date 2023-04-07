package com.bwteconologia.sulmetais.services;

import com.bwteconologia.sulmetais.interfaces.IQuestion;
import com.bwteconologia.sulmetais.models.QuestionModel;
import com.bwteconologia.sulmetais.repositories.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class QuestionService implements IQuestion {

    @Autowired
    QuestionRepository questionRepository;
    @Override
    public List<QuestionModel> getAllQuestions() {
        return questionRepository.findAll();
    }

    @Override
    public Optional<QuestionModel> findById(int id) {
        return questionRepository.findById(id);
    }

    @Override
    public QuestionModel save(QuestionModel question) {
        return questionRepository.save(question);
    }

    @Override
    public Optional<QuestionModel> findByDescription(String description) {
        return questionRepository.findByDescription(description);
    }



    @Override
    public void deleteById(int id) {
        questionRepository.deleteById(id);
    }
}
