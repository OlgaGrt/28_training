package service;

import model.Warehouse;

import java.util.List;

/**
 WarehouseService implements business logic for {@link Warehouse}
 */
public interface WarehouseService {
    List<Warehouse> getAllWarehouses();
    Warehouse getWarehouse(int warehouseId);
    Warehouse addWarehouse(Warehouse warehouse);
    boolean updateWarehouse(Warehouse warehouse);
    boolean deleteWarehouse(int warehouseId);
}
