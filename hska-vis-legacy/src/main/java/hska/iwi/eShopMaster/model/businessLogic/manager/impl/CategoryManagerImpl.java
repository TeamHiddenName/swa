package hska.iwi.eShopMaster.model.businessLogic.manager.impl;


import hska.iwi.eShopMaster.model.businessLogic.manager.CategoryManager;
import hska.iwi.eShopMaster.model.database.dataobjects.Category;
import hska.iwi.eShopMaster.model.ms.BaseDeleteRequest;
import hska.iwi.eShopMaster.model.ms.BaseGetRequest;
import hska.iwi.eShopMaster.model.ms.BasePostRequest;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class CategoryManagerImpl implements CategoryManager {

    private static final String BASE_URL = "http://category-ms:8080";

    public CategoryManagerImpl() {
    }

    public List<Category> getCategories() {
        System.out.println("Getting categories");
        BaseGetRequest<Category[]> request = new BaseGetRequest<>(BASE_URL + "/category");

        Optional<Category[]> list = request.get(Category[].class);

        List<Category> result = list.map(Arrays::asList).orElse(Collections.emptyList());
        System.out.println("Got categories: " + result.size());
        return result;

    }

    public Category getCategory(int id) {
        System.out.println("Get Category for id " + id);
        BaseGetRequest<Category> request = new BaseGetRequest<>(BASE_URL + "/category/" + id);

        Optional<Category> category = request.get(Category.class);
        System.out.println("Got category " + category.toString());
        return category.orElse(null);
    }

    public Category getCategoryByName(String name) {
        System.out.println("Getting category for name " + name);

        BaseGetRequest<Category> request = new BaseGetRequest<>(BASE_URL + "/category?name=" + name);

        Optional<Category> category = request.get(Category.class);
        System.out.println("Got category " + category.toString());
        return category.orElse(null);
    }

    public void addCategory(String name) {
        System.out.println("Adding category with name " + name);
        BasePostRequest<Category> request = new BasePostRequest<>(BASE_URL + "/category");

        Optional<Category> category = request.post(name, Category.class);

        if (category.isPresent()) {
            System.out.println("Successfully created category: " + category.get());
        } else {
            System.out.println("Failed to create category");
        }

    }

    public void delCategory(Category cat) {

        System.out.println("Deleting category with id " + cat.getId() + " and name " + cat);

        new BaseDeleteRequest(BASE_URL + "/category/" + cat.getId()).delete();
    }

    public void delCategoryById(int id) {

        System.out.println("Deleting category with id " + id);

        new BaseDeleteRequest(BASE_URL + "/category/" + id).delete();
    }
}
