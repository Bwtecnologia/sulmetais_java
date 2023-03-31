package com.bwteconologia.sulmetais.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.Date;

@Getter
@Setter
@Accessors(chain=true)
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "products")
public class ProductModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "product_name", nullable = false)
    private String productName;

    @Column(name = "product_buy", nullable = false)
    private Boolean productBuy;

    @Column(name = "product_primary", nullable = false)
    private Boolean productPrimary;
    @Column(name = "product_intermediary", nullable = false)
    private Boolean productIntermediary;
    @Column(name = "product_final", nullable = false)
    private Boolean productFinal;
    @Column(name = "Product_price", nullable = false)
    private Float productPrice;

    @ManyToOne
    @JoinColumn(name = "product_unit_id")
    private UnitModel unit;

    @ManyToOne
    @JoinColumn(name = "group_type_id")
    private GroupModel group;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_at")
    private Date createdAt;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "updated_at")
    private Date updatedAt;

    @PrePersist
    protected void onCreate() {
        createdAt = new Date();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = new Date();
    }
}
