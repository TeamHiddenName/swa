package hska.iwi.eShopMaster.model.businessLogic.manager.impl;

import hska.iwi.eShopMaster.model.businessLogic.manager.ProductManager;
import hska.iwi.eShopMaster.model.database.dataobjects.Category;
import hska.iwi.eShopMaster.model.database.dataobjects.Product;
import hska.iwi.eShopMaster.model.ms.BaseDeleteRequest;
import hska.iwi.eShopMaster.model.ms.BaseGetRequest;
import hska.iwi.eShopMaster.model.ms.BasePostRequest;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class ProductManagerImpl implements ProductManager {

    private static final String BASE_URL = "http://product-ms:8080";

    public ProductManagerImpl() {

    }

    public List<Product> getProducts() {
        System.out.println("Getting all products");
        BaseGetRequest<Product[]> request = new BaseGetRequest<>(BASE_URL + "/product");

        Optional<Product[]> list = request.get(Product[].class);
        List<Product> result = list.map(Arrays::asList).orElse(Collections.emptyList());
        System.out.println("Got " + result.size() + " products");
        return result;
    }

    public List<Product> getProductsForSearchValues(String searchDescription,
                                                    Double searchMinPrice, Double searchMaxPrice) {
        System.out.println("Filtering products for search values " + searchDescription + " minPrice: " + searchMinPrice + " maxPrice: " + searchMaxPrice);
        List<Product> productList = getProducts();

        return productList.stream().filter(product -> {
            if (searchDescription != null && searchDescription.length() > 0) {
                return product.getDetails().contains(searchDescription);
            }
            return true;
        }).filter(product -> {
            if (searchMinPrice != null && searchMaxPrice != null) {
                return product.getPrice() >= searchMinPrice && product.getPrice() <= searchMaxPrice;
            } else if (searchMinPrice != null) {
                return product.getPrice() >= searchMinPrice;
            } else if (searchMaxPrice != null) {
                return product.getPrice() <= searchMaxPrice;
            }
            return true;
        }).collect(Collectors.toList());
    }

    public Product getProductById(int id) {
        System.out.println("Getting product by id " + id);
        BaseGetRequest<Product> request = new BaseGetRequest<>(BASE_URL + "/product/" + id);

        Optional<Product> product = request.get(Product.class);
        System.out.println("Got product " + product);
        return product.orElse(null);
    }

    public Product getProductByName(String name) {
        System.out.println("Getting product by name " + name);
        BaseGetRequest<Product> request = new BaseGetRequest<>(BASE_URL + "/product?name=" + name);

        Optional<Product> product = request.get(Product.class);
        System.out.println("Got product " + product);
        return product.orElse(null);
    }

    public int addProduct(String name, double price, int categoryId, String details) {
        System.out.println("Adding product with name: " + name + " price: " + price + " categoryId: " + categoryId + " details:" + details);
        Category category = new CategoryManagerImpl().getCategory(categoryId);
        if (category != null) {
            System.out.println("Found category for id " + categoryId);
            Product product = new Product(name, price, category, details);

            BasePostRequest<Product> request = new BasePostRequest<>(BASE_URL + "/product");
            Optional<Product> result = request.postAsJson(product, Product.class);
            System.out.println("Created product " + result);
            return result.map(Product::getId).orElse(-1);
        } else {
            System.out.println("No Category with id " + categoryId + " found");
            return -1;
        }
    }


    public void deleteProductById(int id) {
        System.out.println("Deleting product with id " + id);
        new BaseDeleteRequest(BASE_URL + "/product/" + id).delete();
    }

    public boolean deleteProductsByCategoryId(int categoryId) {
        // TODO Auto-generated method stub
        return false;
    }


}
