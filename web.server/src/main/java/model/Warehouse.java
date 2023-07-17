package model;

import org.postgresql.geometric.PGpoint;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;

public class Warehouse {
    int warehouseId;
    String name;
    PGpoint point;
    List<Product> products;

    public Warehouse() {
        products = new ArrayList<>();
    }

    public int getWarehouseId() {
        return warehouseId;
    }

    public void setWarehouseId(int warehouseId) {
        this.warehouseId = warehouseId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public PGpoint getPoint() {
        return point;
    }

    public void setPoint(PGpoint point) {
        this.point = point;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Warehouse warehouse = (Warehouse) o;
        return warehouseId == warehouse.warehouseId && Objects.equals(name, warehouse.name) && Objects.equals(point, warehouse.point);
    }

    @Override
    public int hashCode() {
        return Objects.hash(warehouseId, name, point);
    }

    @Override
    public String toString() {
        return "Warehouse{" +
                "warehouseId=" + warehouseId +
                ", name='" + name + '\'' +
                ", point=" + point +
                ", products=" + products +
                '}';
    }
}
