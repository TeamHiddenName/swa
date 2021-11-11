package hska.iwi.eShopMaster.model.businessLogic.manager.impl.newimpl.dto;

import hska.iwi.eShopMaster.model.database.dataobjects.Category;

import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

public class ProductDto {

    private int id;

    private String name;

    private double price;

    private int category;

    private String details;

    public ProductDto(int id, String name, double price, int category, String details) {
        this.id = id;
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
