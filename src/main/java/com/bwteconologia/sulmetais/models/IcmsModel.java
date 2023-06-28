package com.bwteconologia.sulmetais.models;

import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;

@Entity
@Data
@Table(name = "icms")
public class IcmsModel implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String name;

    @Column
    private double percentage;

}
