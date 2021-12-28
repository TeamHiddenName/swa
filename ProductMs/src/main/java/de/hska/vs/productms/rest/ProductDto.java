package de.hska.vs.productms.rest;

public class ProductDto {

    private final int id;
    private final String name;
    private final double price;
    private final CategoryDto category;
    private final String details;

    public ProductDto() {
        this.id = -1;
        this.name = "";
        this.price = 0;
        this.category = null;
        this.details = "";
    }

    public ProductDto(int id, String name, double price, CategoryDto category, String details) {
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

    public CategoryDto getCategory() {
        return category;
    }

    public String getDetails() {
        return details;
    }
}
