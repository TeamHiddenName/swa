package de.hska.vs.productms.database.repository;

import de.hska.vs.productms.database.entity.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<ProductEntity, Integer> {
     void deleteByCategory(int categoryId);

     List<ProductEntity> findAllByCategory(int categoryId);
}
