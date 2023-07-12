package com.bwteconologia.sulmetais.models;


import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Data
@Table(name = "body_formula_question")
public class BodyFormulaQuestionModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "question_id", insertable = false, updatable = false)
    private Long questionId;

    @OneToMany(cascade = CascadeType.ALL,  orphanRemoval = true)
    @JoinColumn(name = "body_formula_question_id")
    private List<FormulasQuestionModel> formulas;

    @Column(name = "quiz_id", insertable = false, updatable = false)
    private Long quizId;

    @ManyToMany
    @JoinColumn
    private List<AnswerModel> answersIfTrue;

}
