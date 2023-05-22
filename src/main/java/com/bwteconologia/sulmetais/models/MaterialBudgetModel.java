package com.bwteconologia.sulmetais.models;


import com.bwteconologia.sulmetais.exceptions.materialbudget.MaterialOperandNotExistsException;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;
import java.util.Optional;

@Data
@Entity
@Table(name = "material_budget")
public class MaterialBudgetModel {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String materialName;

    @Column
    private String unity;

    @Column
    private int quantity;

    @Column
    private double price;

    @Column
    private double totalPrice;

    @ManyToOne
    @JoinColumn
    private BudgetsModel budget;

    public MaterialBudgetModel(List<AnswerQuizModel> answers, MaterialListModel materialList) {

        this.materialName = materialList.getProduct().getProductName();
        this.unity = materialList.getProduct().getUnit().getUnitSize();
        this.quantity = materialList.getQuantity();
        this.price = materialList.getProduct().getProductPrice();

        //id off substitute question to change if have answer of question
        if (materialList.getItemSubstitute() != null) {
            Long idQuestionSub = materialList.getItemSubstitute().getId();

            for (AnswerQuizModel answer : answers) {

                Long answerQuestionId = answer.getQuestion().getId();

                if (idQuestionSub == answerQuestionId) {
                    ProductModel substituteProduct = answer.getAnswer().getProduct();

                    this.materialName = substituteProduct.getProductName();
                    this.unity = substituteProduct.getUnit().getUnitSize();
                    this.price = substituteProduct.getProductPrice();

                }
            }
        }


        int quantity = 0;


            for (FormulaMaterialModel formula : materialList.getFormula()) {

                Long idFormulaQuestion1 = formula.getQuestion1().getId();
                Optional<QuestionModel> idFormulaQuestion2 = Optional.ofNullable(formula.getQuestion2());

                double priceQuestion1 = 0;
                double priceQuestion2 = 0;

                    for (AnswerQuizModel answer : answers) {

                        if(answer.getQuestion() != null) {
                            if (idFormulaQuestion1 == answer.getQuestion().getId()) {

                                priceQuestion1 = answer.getAnswer().getValue();

                                if (answer.getAnswer().getProduct() != null) {
                                    priceQuestion1 = answer.getAnswer().getProduct().getProductPrice();
                                }


                            }
                        }

                        else {
                            priceQuestion1 = answer.getAnswer().getValue();
                        }
                    }


                    if (idFormulaQuestion2.isPresent()) {
                        for (AnswerQuizModel answer : answers) {
                            if(answer.getQuestion() != null) {
                            if (formula.getQuestion2().getId() == answer.getQuestion().getId()) {

                                priceQuestion2 = answer.getAnswer().getValue();


                                if (answer.getAnswer().getProduct() != null) {
                                    priceQuestion2 = answer.getAnswer().getProduct().getProductPrice();
                                }

                            }
                        }   else {priceQuestion2 = answer.getAnswer().getValue();}

                        }

                    }


                    if (idFormulaQuestion2.isPresent()) {
                        switch (formula.getOperation()) {
                            case "plus":
                                quantity += priceQuestion1 + priceQuestion2;
                                break;
                            case "times":
                                quantity += priceQuestion1 * priceQuestion2;
                                break;
                            case "minor":
                                quantity += priceQuestion1 - priceQuestion2;
                                break;
                            case "divided":
                                quantity += priceQuestion1 / priceQuestion2;
                                break;
                            default:
                                throw new MaterialOperandNotExistsException("This operand: " + formula.getOperation() + " doesnt exists!");
                        }

                    } else {
                        switch (formula.getOperation()) {
                            case "plus":
                                quantity += priceQuestion1;
                                break;
                            case "times":
                                quantity *= priceQuestion1;
                                break;
                            case "minor":
                                quantity -= priceQuestion1;
                                break;
                            case "divided":
                                quantity /= priceQuestion1;
                            default:
                                throw new MaterialOperandNotExistsException("This operand: " + formula.getOperation() + " doesnt exists!");
                        }
                    }

        }

        if (quantity > 0) this.quantity = quantity;

        this.totalPrice = this.quantity * this.price;
    }


    public MaterialBudgetModel() {

    }
}
