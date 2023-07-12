package com.bwteconologia.sulmetais.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.Accessors;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;


@Data
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

    @Column(name = "product_buy")
    private Boolean productBuy;

    @Column(name = "product_fabricated")
    private Boolean productFabricated;

    @Column(name = "product_generic")
    private Boolean productGeneric;

    @Column(name = "product_primary")
    private Boolean productPrimary;
    @Column(name = "product_intermediary")
    private Boolean productIntermediary;
    @Column(name = "product_final")
    private Boolean productFinal;
    @Column(name = "Product_price")
    private Float productPrice;

    @ManyToOne
    @JoinColumn(name = "product_unit_id", nullable = false)
    private UnitModel unit;

    @ManyToOne
    @JoinColumn(name = "group_type_id")
    private ProductGroupModel productGroup;

    @ManyToMany
    @JoinTable(
            name = "group_colors_products",
            joinColumns = @JoinColumn(name = "product_id"),
            inverseJoinColumns =  @JoinColumn(name = "group_color_id")
    )
    private Set<GroupColorModel> groupColors = new HashSet<>();



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
