package com.bwteconologia.sulmetais.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

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
    @Column(name = "Product_price", nullable = false)
    private Float productPrice;

    @ManyToOne
    @JoinColumn(name = "product_unit_id", nullable = false)
    private UnitModel unit;

    @ManyToOne
    @JoinColumn(name = "product_color_id")
    private ColorModel color;

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

    @ManyToMany(mappedBy = "product")
    private Set<QuizModel> quiz;


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

    //getters and setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Boolean getProductBuy() {
        return productBuy;
    }

    public void setProductBuy(Boolean productBuy) {
        this.productBuy = productBuy;
    }

    public Boolean getProductFabricated() {
        return productFabricated;
    }

    public void setProductFabricated(Boolean productFabricated) {
        this.productFabricated = productFabricated;
    }

    public Boolean getProductGeneric() {
        return productGeneric;
    }

    public void setProductGeneric(Boolean productGeneric) {
        this.productGeneric = productGeneric;
    }

    public Boolean getProductPrimary() {
        return productPrimary;
    }

    public void setProductPrimary(Boolean productPrimary) {
        this.productPrimary = productPrimary;
    }

    public Boolean getProductIntermediary() {
        return productIntermediary;
    }

    public void setProductIntermediary(Boolean productIntermediary) {
        this.productIntermediary = productIntermediary;
    }

    public Boolean getProductFinal() {
        return productFinal;
    }

    public void setProductFinal(Boolean productFinal) {
        this.productFinal = productFinal;
    }

    public Float getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(Float productPrice) {
        this.productPrice = productPrice;
    }

    public UnitModel getUnit() {
        return unit;
    }

    public void setUnit(UnitModel unit) {
        this.unit = unit;
    }

    public ColorModel getColor() {
        return color;
    }

    public void setColor(ColorModel color) {
        this.color = color;
    }

    public ProductGroupModel getGroup() {
        return productGroup;
    }

    public void setGroup(ProductGroupModel group) {
        this.productGroup = group;
    }

    public Set<GroupColorModel> getGroupColors() {
        return groupColors;
    }

    public void setGroupColors(Set<GroupColorModel> groupColors) {
        this.groupColors = groupColors;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }
}
