package de.hska.vs.productms.service;

public interface CategoryService {

    void deleteCategory(int id);

    boolean canDelete(int id);
}
