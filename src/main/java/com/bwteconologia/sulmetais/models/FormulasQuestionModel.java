package com.bwteconologia.sulmetais.models;

import jakarta.persistence.*;
import org.aspectj.weaver.patterns.TypePatternQuestions;

import java.util.List;

@Entity
@Table(name = "formula_question")
public class FormulasQuestionModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn
    private BodyFormulaQuestionModel question;

    @JoinColumn
    private String type;

    @ManyToMany
    @JoinColumn
    private List<AnswerModel> answer;
}
