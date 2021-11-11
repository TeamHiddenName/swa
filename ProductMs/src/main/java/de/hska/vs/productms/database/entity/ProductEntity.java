package de.hska.vs.productms.database.entity;

import org.springframework.data.annotation.Id;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;

public class ProductEntity {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private int id;

    @Column(name = "name")
    private String name;

    @Column(name = "price")
    private double price;

    @Column(name = "category")
    private int category;

    @Column(name = "details")
    private String details;

    public ProductEntity() {
    }

    public ProductEntity(String name, double price, int category) {
        this.name = name;
        this.price = price;
        this.category = category;
    }

    public ProductEntity(String name, double price, int category, String details) {
        this.name = name;
        this.price = price;
        this.category = category;
        this.details = details;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public int getCategory() {
        return category;
    }

    public String getDetails() {
        return details;
    }
}
