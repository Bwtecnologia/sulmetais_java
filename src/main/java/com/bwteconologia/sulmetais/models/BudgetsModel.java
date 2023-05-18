package com.bwteconologia.sulmetais.models;

import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data

@Entity
@Table(name = "budgets")
public class BudgetsModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany
    private List<MaterialBudgetModel> materials;

    @ManyToOne
    @JoinColumn(name = "quiz_id")
    private QuizModel quizId;

}
