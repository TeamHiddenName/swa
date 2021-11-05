package de.vs.searchms.database.dao;

import de.vs.searchms.database.entity.ProductEntity;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

@Component
public class ProductDao {

    private final EntityManager entityManager;

    public ProductDao(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public List<ProductEntity> findByCriteria(double minPrice, double maxPrice, String description) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<ProductEntity> query = builder.createQuery(ProductEntity.class);

        Root<ProductEntity> product = query.from(ProductEntity.class);
        List<Predicate> predicates = new ArrayList<>();

        if (description != null && description.length() > 0) {
            predicates.add(builder.like(product.get("details"), "%" + description + "%"));
        }

        if (minPrice >= 0 && maxPrice >= 0) {
            predicates.add(builder.between(product.get("price"), minPrice, maxPrice));
        } else if (minPrice >= 0) {
            predicates.add(builder.ge(product.get("price"), minPrice));
        } else if(maxPrice >= 0){
            predicates.add(builder.le(product.get("price"), maxPrice));
        }

        query.where(predicates.toArray(new Predicate[0]));
        return entityManager.createQuery(query).getResultList();
    }
}
