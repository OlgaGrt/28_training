package dao;

import model.Product;
import model.Warehouse;

import java.util.List;

/**
ProductDao provides CRUD operations for {@link Product}
 */

public interface ProductDao {
    List<Product> getAllProduct();
    Product getProduct(int productId);
    Product addProduct(Product product);
    boolean updateProduct(Product product);
    boolean deleteProduct(int productId);
    List<Warehouse> getAvailableWarehousesForProduct(int productId);
    boolean addWarehouseToProduct(int productId, int warehouseId);
}
