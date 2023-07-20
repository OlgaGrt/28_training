package service;

import dao.WarehouseDAO;
import model.Warehouse;

import java.util.List;

public class WarehouseServiceImpl implements WarehouseService {

    private final WarehouseDAO warehouseDAO = new WarehouseDAO();

    @Override
    public List<Warehouse> getAllWarehouses() {
        return warehouseDAO.getAll();
    }

    @Override
    public Warehouse getWarehouse(final int warehouseId) {
        return warehouseDAO.getById(warehouseId);
    }

    @Override
    public Warehouse addWarehouse(final Warehouse warehouse) {
        return warehouseDAO.create(warehouse);
    }

    @Override
    public void updateWarehouse(final Warehouse warehouse) {
        warehouseDAO.update(warehouse);
    }

    @Override
    public void deleteWarehouse(final int warehouseId) {
        warehouseDAO.delete(warehouseId);
    }
}
