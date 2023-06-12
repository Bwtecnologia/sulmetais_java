package com.bwteconologia.sulmetais.models;


import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Data
@Table(name = "body_formula")
public class BodyFormulaQuestionModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private QuestionModel questionForm;

    @OneToMany
    private List<FormulasQuestionModel> formulasQuestion;

}
