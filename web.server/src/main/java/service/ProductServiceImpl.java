package service;

import dao.ProductDao;
import dao.ProductDaoImpl;
import model.Product;

import java.util.List;

public class ProductServiceImpl implements ProductService {

    private final ProductDao productDao = new ProductDaoImpl();

    public List<Product> getAllProduct(){
        return productDao.getAllProduct();
    }
    public Product getProduct(final int productId){
        return productDao.getProduct(productId);
    }
    public void addProduct(final Product product){
        productDao.addProduct(product);
    }
    public boolean updateProduct(final Product product){
        return productDao.updateProduct(product);
    }
    public boolean deleteProduct(final int productId){
        return productDao.deleteProduct(productId);
    }
}
