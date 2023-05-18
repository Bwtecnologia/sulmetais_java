package com.bwteconologia.sulmetais.models;


import com.bwteconologia.sulmetais.exceptions.materialbudget.MaterialOperandNotExistsException;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

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
        Long idQuestionSub = materialList.getItemSubstitute().getId();

        for (AnswerQuizModel answer : answers) {

            Long answerQuestionId = answer.getQuestion().getId();
            System.out.println(answer);
            if (idQuestionSub == answerQuestionId) {
                ProductModel substituteProduct = answer.getAnswer().getProduct();

                this.materialName = substituteProduct.getProductName();
                this.unity = substituteProduct.getUnit().getUnitSize();
                this.price = substituteProduct.getProductPrice();

            }
        }

        int quantity = 0;

        for (FormulaMaterialModel formula : materialList.getFormula()) {
            Long idFormulaQuestion1 = formula.getQuestion1().getId();
            Long idFormulaQuestion2 = formula.getQuestion2().getId();

            double priceQuestion1 = 0;
            double priceQuestion2 = 0;

            for (AnswerQuizModel answer : answers) {
                if (idFormulaQuestion1 == answer.getQuestion().getId()) {
                    priceQuestion1 = answer.getAnswer().getValue();
                }
            }

            for (AnswerQuizModel answer : answers) {
                if (idFormulaQuestion2 == answer.getQuestion().getId()) {
                    priceQuestion2 = answer.getAnswer().getValue();
                }
            }

            if(idFormulaQuestion2 != null) {
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
                    default: throw new MaterialOperandNotExistsException("This operand: " + formula.getOperation() + " doesnt exists!");
                }
            }

            else{
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
                    default: throw new MaterialOperandNotExistsException("This operand: " + formula.getOperation() + " doesnt exists!");
                }
            }

        }

        if(quantity > 0) this.quantity = quantity;

        this.totalPrice = this.quantity * this.price;


    }

    public MaterialBudgetModel() {

    }
}
