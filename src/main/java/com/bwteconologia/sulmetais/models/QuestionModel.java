package com.bwteconologia.sulmetais.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.Accessors;

import java.util.Date;
import java.util.List;

@Data
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "questions")
public class QuestionModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "question_position",
            joinColumns = { @JoinColumn(name = "question_id") },
            inverseJoinColumns = { @JoinColumn(name = "position_id") }
    )
    private List<QuestionPositionModel> position;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "question_body_formula",
            joinColumns = { @JoinColumn(name = "question_id") },
            inverseJoinColumns = { @JoinColumn(name = "body_formula_id") }
    )
    private List<BodyFormulaQuestionModel> bodyFormula;

    @Column(name = "question_type")
    private String type;

    @Column(name = "question_description")
    private String description;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_at")
    private Date createdAt;

    @Column
    @JoinColumn
    private boolean formula;

    @OneToMany(mappedBy = "question")
    @JsonIgnore
    private List<AnswerModel> answers;

    @ManyToMany(mappedBy = "questions")
    @JsonIgnore
    private List<QuizModel> quiz;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "updated_at")
    private Date updatedAt;

    @PrePersist
    protected void onCreate() {
        createdAt = new Date();
        formula = false;
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = new Date();
    }


    public void settBodyFormula(List<BodyFormulaQuestionModel> bodyFormula) {
        if(!this.bodyFormula.isEmpty()) this.bodyFormula.clear();
        if(bodyFormula != null) {
            this.bodyFormula.addAll(bodyFormula);
        }
    }
}
