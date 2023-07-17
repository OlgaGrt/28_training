package dao;

import model.Product;
import model.Warehouse;

import java.util.List;

/**
 WarehouseDao provides CRUD operations for {@link Warehouse}
 */

public interface WarehouseDao {
    List<Warehouse> getAllWarehouses();
    Warehouse getWarehouse(int warehouseId);
    Warehouse addWarehouse(Warehouse warehouse);
    boolean updateWarehouse(Warehouse warehouse);
    boolean deleteWarehouse(int warehouseId);
    List<Product> findProductsForWarehouse(int warehouseId);
}
