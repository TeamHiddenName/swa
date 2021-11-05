package de.vs.searchms.rest.dto;

public class SearchResult {

    private final int number;
    private final String name;
    private final double price;
    private final String categoryName;

    public SearchResult(int number, String name, double price, String categoryName) {
        this.number = number;
        this.name = name;
        this.price = price;
        this.categoryName = categoryName;
    }

    public int getNumber() {
        return number;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public String getCategoryName() {
        return categoryName;
    }
}
