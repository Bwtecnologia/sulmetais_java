package com.bwteconologia.sulmetais.models;

import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;

@Entity
@Data
public class QuestionPositionModel implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column()
    private int position;
    @Column(name = "question_id", insertable = false, updatable = false)
    private Long questionId;
    @Column(name = "quiz_id", insertable = false, updatable = false)
    private Long quizId;

}
