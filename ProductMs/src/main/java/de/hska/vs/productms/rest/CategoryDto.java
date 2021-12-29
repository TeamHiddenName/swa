package de.hska.vs.productms.rest;

public class CategoryDto {

    private final int id;
    private final String name;

    public CategoryDto() {
        this.id = -1;
        this.name = "";
    }

    public CategoryDto(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "CategoryDto{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
