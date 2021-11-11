package de.hska.vs.categoryms.database.repository;

import de.hska.vs.categoryms.database.entity.CategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<CategoryEntity, Integer> {
}
