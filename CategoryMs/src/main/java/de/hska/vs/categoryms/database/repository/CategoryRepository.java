package de.hska.vs.categoryms.database.repository;

import de.hska.vs.categoryms.database.entity.CategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CategoryRepository extends JpaRepository<CategoryEntity, Integer> {

    Optional<CategoryEntity> findCategoryEntityByName(String name);
}
