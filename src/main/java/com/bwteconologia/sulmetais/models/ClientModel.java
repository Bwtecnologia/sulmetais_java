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

    @Column()
    private String address;

    @Column(nullable = false)
    private String obra;

    @ManyToOne
    @JoinColumn(name = "icms_id", nullable = false)
    private IcmsModel icms;

    @Column(nullable = false)
    private String agent;

    @ManyToOne
    @JoinColumn(name = "payment_condition_id", nullable = false)
    private PaymentConditionModel paymentCondition;

    @Column(nullable = false)
    private String phone;

    @Column(nullable = false)
    private String email;
}
