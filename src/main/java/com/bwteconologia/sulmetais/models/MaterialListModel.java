package com.bwteconologia.sulmetais.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
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

    @OneToOne
    @JoinColumn
    private QuestionModel itemSubstitute;

//    @ManyToOne
//    @JsonIgnore
//    private QuizModel quiz;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn
    private List<FormulaMaterialModel> formula;

    @Column
    int quantity;

    @Column
    int lossPercent;

}
