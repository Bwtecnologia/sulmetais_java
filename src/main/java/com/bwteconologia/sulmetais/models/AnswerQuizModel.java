package com.bwteconologia.sulmetais.models;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "answers_quiz")
public class AnswerQuizModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    private AnswerModel answer;
    @OneToOne
    private QuestionModel question;

    @ManyToOne
    @JoinColumn
    private QuizModel quiz;

}