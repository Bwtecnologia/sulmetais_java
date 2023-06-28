package com.bwteconologia.sulmetais.models;

import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;

@Entity
@Table(name = "clients")
@Data
public class ClientModel implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String cnpj;

    @Column(nullable = false)
    private String address;

    @Column(nullable = false)
    private String obra;

    @OneToOne(optional = false)
    private IcmsModel icms;

    @Column(nullable = false)
    private String agent;

    @OneToOne(optional = false)
    private PaymentConditionModel paymentCondition;

    @Column(nullable = false)
    private String phone;

    @Column(nullable = false)
    private String email;
}
