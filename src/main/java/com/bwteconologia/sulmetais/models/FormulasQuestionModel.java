package com.bwteconologia.sulmetais.models;

import jakarta.persistence.*;
import lombok.Data;
import org.aspectj.weaver.patterns.TypePatternQuestions;

import java.util.List;

@Entity
@Data
@Table(name = "formula_question")
public class FormulasQuestionModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "body_formula_question_id", insertable = false, updatable = false)
    private Long bodyFormulaId;

    @JoinColumn
    private String type;

    @OneToOne
    @JoinColumn
    private QuestionModel question;

    @OneToOne
    @JoinColumn
    private AnswerModel answer;


}
