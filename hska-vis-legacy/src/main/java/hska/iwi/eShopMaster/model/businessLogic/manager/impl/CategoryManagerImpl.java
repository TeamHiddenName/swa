package hska.iwi.eShopMaster.model.businessLogic.manager.impl;


import hska.iwi.eShopMaster.model.businessLogic.manager.CategoryManager;
import hska.iwi.eShopMaster.model.database.dataobjects.Category;
import hska.iwi.eShopMaster.model.ms.BaseDeleteRequest;
import hska.iwi.eShopMaster.model.ms.BaseGetRequest;
import hska.iwi.eShopMaster.model.ms.BasePostRequest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class CategoryManagerImpl implements CategoryManager {

    private static final String BASE_URL = "http://category-ms:8080";
    private static final Logger LOG = LogManager.getLogger();

    public CategoryManagerImpl() {
    }

    public List<Category> getCategories() {
        LOG.debug("Getting categories");
        BaseGetRequest<Category[]> request = new BaseGetRequest<>(BASE_URL + "/category");

        Optional<Category[]> list = request.get(Category[].class);

        List<Category> result = list.map(Arrays::asList).orElse(Collections.emptyList());
        LOG.info("Got categories: " + result.size());
        return result;

    }

    public Category getCategory(int id) {
        LOG.debug("Get Category for id " + id);
        BaseGetRequest<Category> request = new BaseGetRequest<>(BASE_URL + "/category/" + id);

        Optional<Category> category = request.get(Category.class);
        LOG.info("Got category " + category.toString());
        return category.orElse(null);
    }

    public Category getCategoryByName(String name) {
        LOG.debug("Getting category for name " + name);

        BaseGetRequest<Category> request = new BaseGetRequest<>(BASE_URL + "/category?name=" + name);

        Optional<Category> category = request.get(Category.class);
        LOG.info("Got category " + category.toString());
        return category.orElse(null);
    }

    public void addCategory(String name) {
        LOG.debug("Adding category with name " + name);
        BasePostRequest<Category> request = new BasePostRequest<>(BASE_URL + "/category");

        Optional<Category> category = request.post(name, Category.class);

        if (category.isPresent()) {
            LOG.info("Successfully created category: " + category.get());
        } else {
            LOG.info("Failed to create category");
        }

    }

    public void delCategory(Category cat) {

        LOG.debug("Deleting category with id " + cat.getId() + " and name " + cat);

        new BaseDeleteRequest(BASE_URL + "/category/" + cat.getId()).delete();
    }

    public void delCategoryById(int id) {

        LOG.debug("Deleting category with id " + id);

        new BaseDeleteRequest(BASE_URL + "/category/" + id).delete();
    }
}
