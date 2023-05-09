package com.bwteconologia.sulmetais.models;

import jakarta.persistence.*;

import java.io.Serializable;
import java.util.Date;
import java.util.List;


@Entity
@Table(name = "group_colors")
public class GroupColorModel implements Serializable {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @OneToMany()
    @JoinColumn(name = "group_id")
    private List<ColorModel> colors;

    @Column(name = "highest_value")
    private double highestValue;

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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<ColorModel> getColors() {
        return colors;
    }

    public void setColors(List<ColorModel> colors) {
        this.colors = colors;
    }

    public double getHighestValue() {
        return highestValue;
    }

    public void setHighestValue(double highestValue) {
        this.highestValue = highestValue;
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
