package dao;

import model.Discount;

/**
 WarehouseDao provides CRUD operations for {@link Discount}
 */

public class DiscountDAO extends DAO<Discount> {

    public DiscountDAO() {
        this.setModelClass(Discount.class);
    }

}
