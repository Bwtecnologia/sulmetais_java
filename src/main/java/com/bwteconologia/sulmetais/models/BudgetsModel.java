package com.bwteconologia.sulmetais.models;

import jakarta.persistence.ManyToOne;

public class BudgetsModel {
    @ManyToOne
    MaterialListModel materialList;

}
