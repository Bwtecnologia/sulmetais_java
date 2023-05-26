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

    @Column(nullable = false)
    double valor1;

    @Column
    double valor2;

    @OneToOne
    @JoinColumn(nullable = true)
    private QuestionModel question1;

    @OneToOne
    @JoinColumn(nullable = true)
    private QuestionModel question2;
}
