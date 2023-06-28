package com.bwteconologia.sulmetais.models;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "static_parameter")
public class StaticParameterModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private double deliveryPercentage;

    @Column
    private double comissionPercentage;

    @Column
    private double ipiPercentage;

    @Column
    private double pisPercentage;

    @Column
    private double cofinsPercentage;
}
