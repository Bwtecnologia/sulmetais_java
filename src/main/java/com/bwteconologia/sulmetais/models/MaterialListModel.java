package com.bwteconologia.sulmetais.models;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
public class MaterialListModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn
    ProductModel product;

//    @ManyToOne
//    @JoinColumn(nullable = false)
//    private QuizModel quiz;

    @ManyToOne
    @JoinColumn
    QuizModel quiz;

    @OneToOne
    @JoinColumn
    private QuestionModel itemSubstitute;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn
    private List<FormulaMaterialModel> formula;

    @Column
    int quantity;

    @Column
    int lossPercent;

}
