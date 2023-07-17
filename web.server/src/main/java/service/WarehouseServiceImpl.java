package service;

import dao.WarehouseDao;
import dao.WarehouseDaoImpl;
import model.Warehouse;

import java.util.List;

public class WarehouseServiceImpl implements WarehouseService {

    private final WarehouseDao warehouseDao = new WarehouseDaoImpl();

    @Override
    public List<Warehouse> getAllWarehouses() {
        return warehouseDao.getAllWarehouses();
    }

    @Override
    public Warehouse getWarehouse(final int warehouseId) {
        return warehouseDao.getWarehouse(warehouseId);
    }

    @Override
    public Warehouse addWarehouse(final Warehouse warehouse) {
        return warehouseDao.addWarehouse(warehouse);
    }

    @Override
    public boolean updateWarehouse(final Warehouse warehouse) {
        return warehouseDao.updateWarehouse(warehouse);
    }

    @Override
    public boolean deleteWarehouse(final int warehouseId) {
        return warehouseDao.deleteWarehouse(warehouseId);
    }
}
