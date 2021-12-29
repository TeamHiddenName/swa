package de.hska.vs.categoryms.service;

import de.hska.vs.categoryms.database.entity.CategoryEntity;
import de.hska.vs.categoryms.database.repository.CategoryRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryServiceImpl implements CategoryService {

    private static final Logger LOG = LoggerFactory.getLogger(CategoryServiceImpl.class);
    private final CategoryRepository categoryRepository;

    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public Optional<CategoryEntity> createCategory(CategoryEntity category) {
        LOG.debug("Creating category with name " + category.getName());
        if (validateCategory(category)) {
            LOG.info("Successfully created category");
            return Optional.of(categoryRepository.save(category));
        } else {
            LOG.info("Failed to create category. Validation failed");
            return Optional.empty();
        }
    }

    @Override
    public boolean deleteCategory(int id) {
        LOG.debug("Deleting category with id " + id);
        RestTemplate restTemplate = new RestTemplate();
        Object[] result = restTemplate.getForObject("http://product-ms:8080/product?categoryId=" + id, Object[].class);
        if (result == null || result.length == 0) {
            categoryRepository.deleteById(id);
            LOG.info("Successfully deleted category with id " + id);
            return true;
        }
        LOG.info("Failed to delete category with id " + id + ". Category referenced by products");
        return false;

    }

    @Override
    public boolean updateCategory(CategoryEntity category) {
        LOG.debug("Updating category");
        if (validateCategory(category) && category.getId() >= 0) {
            categoryRepository.save(category);
            LOG.info("Successfully updated category");
            return true;
        }
        LOG.info("Failed to update category");
        return false;
    }

    @Override
    public Optional<CategoryEntity> getCategory(int id) {
        LOG.info("Getting category with id " + id);
        return categoryRepository.findById(id);
    }

    @Override
    public Optional<CategoryEntity> getCategory(String name) {
        LOG.info("Getting category with name " + name);
        return categoryRepository.findCategoryEntityByName(name);
    }

    @Override
    public List<CategoryEntity> getAll() {
        LOG.info("Getting all categories");
        return categoryRepository.findAll();
    }

    private boolean validateCategory(CategoryEntity category) {
        return category.getName() != null && !category.getName().isEmpty();
    }
}
