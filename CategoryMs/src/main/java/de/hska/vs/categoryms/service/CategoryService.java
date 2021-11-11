package de.hska.vs.categoryms.service;

import de.hska.vs.categoryms.database.entity.CategoryEntity;

import java.util.List;
import java.util.Optional;

public interface CategoryService {

    Optional<CategoryEntity> createCategory(CategoryEntity category);

    void deleteCategory(int id);

    boolean updateCategory(CategoryEntity category);

    Optional<CategoryEntity> getCategory(int id);

    List<CategoryEntity> getAll();
}
