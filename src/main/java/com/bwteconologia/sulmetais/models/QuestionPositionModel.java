package com.bwteconologia.sulmetais.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;

@Entity
@Data
@Table(name = "question_position")
public class QuestionPositionModel implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private int position;
    @ManyToOne
    @JoinColumn(name = "question_id")
    private QuestionModel question;
    @ManyToOne
    @JoinColumn(name = "quiz_id")
    @JsonIgnore
    private QuizModel quiz;

//    @Column(name = "question_id")
//    private Long questionId;
//    @Column(name = "quiz_id")
//    private Long quizId;

}
