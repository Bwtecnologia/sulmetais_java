package com.bwteconologia.sulmetais.models;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class FormulaMaterialModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String operation;

    @OneToOne
    @JoinColumn(nullable = false)
    private QuestionModel question1;

    @OneToOne
    @JoinColumn(nullable = true)
    private QuestionModel question2;
}
