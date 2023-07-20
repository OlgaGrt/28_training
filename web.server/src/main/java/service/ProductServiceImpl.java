package service;

import dao.ProductDAO;
import model.Product;

import java.util.List;

public class ProductServiceImpl implements ProductService {

    private final ProductDAO productDAO = new ProductDAO();

    public List<Product> getAllProduct() {
        return productDAO.getAll();
    }

    public Product getProduct(final int productId) {
        return productDAO.getById(productId);
    }

    public void addProduct(final Product product) {
        productDAO.create(product);
    }

    public void updateProduct(final Product product) {
        productDAO.update(product);
    }

    public void deleteProduct(final int productId) {
        productDAO.delete(productId);
    }
}
