package hska.iwi.eShopMaster.model.businessLogic.manager.impl.newimpl;

import com.google.gson.Gson;
import hska.iwi.eShopMaster.model.businessLogic.manager.CategoryManager;
import hska.iwi.eShopMaster.model.businessLogic.manager.impl.newimpl.dto.CategoryDto;
import hska.iwi.eShopMaster.model.businessLogic.manager.impl.newimpl.dto.ProductDto;
import hska.iwi.eShopMaster.model.database.dataobjects.Category;
import hska.iwi.eShopMaster.model.database.dataobjects.Product;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.*;

public class CategoryManagerNewImpl implements CategoryManager {
    public List<Category> getCategories() {

        CategoryDto[] result = getRequest("http://category-web/category", CategoryDto[].class);

        List<Category> resultList = new ArrayList<Category>();
        for (CategoryDto categoryDto : result) {
            Category category = new Category(categoryDto.getName());
            category.setId(categoryDto.getId());
            category.setProducts(productsForCategory(category));
            resultList.add(category);
        }
        return resultList;
    }

    public Category getCategory(int id) {

    }

    public Category getCategoryByName(String name) {
        return null;
    }

    public void addCategory(String name) {

    }

    public void delCategory(Category cat) {

    }

    public void delCategoryById(int id) {

    }

    private Set<Product> productsForCategory(Category category){
        ProductDto[] productDto = getRequest("http://product-web/product?categoryId=" + category.getId(), ProductDto[].class);

        Set<Product> products = new HashSet<Product>();
        for (ProductDto dto : productDto) {
            products.add(convert(dto, category));
        }
        return products;
    }

    private <T> T getRequest(String urlString, Class<T> type) {
        try {
            URL url = new URL(urlString);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String inputLine;
            StringBuffer content = new StringBuffer();
            while ((inputLine = reader.readLine()) != null) {
                content.append(inputLine);
            }
            reader.close();
            return new Gson().fromJson(content.toString(), type);
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private Product convert(ProductDto dto, Category category) {
        Product product = new Product(dto.getName(), dto.getPrice(), category, dto.getDetails());
        product.setId(dto.getId());
        return product;
    }
}
