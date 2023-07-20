package service;

import model.Product;

import java.util.List;

/**
 ProductService implements business logic for {@link Product}
 */
public interface ProductService {
    List<Product> getAllProduct();
    Product getProduct(int productId);
    void addProduct(Product product);
    void updateProduct(Product product);
    void deleteProduct(int productId);
}
