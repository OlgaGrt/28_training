package model;

import jakarta.persistence.*;

import java.util.*;


@Entity(name="Warehouse")
@Table(name = "warehouses")
public class Warehouse extends BaseEntity {

    @Column(name = "name")
    private String name;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "x", column = @Column(name = "lat")),
            @AttributeOverride(name = "y", column = @Column(name = "long"))
    })
    private Point location;


    @ManyToMany(cascade = {
            CascadeType.PERSIST,
            CascadeType.MERGE
    }, fetch = FetchType.EAGER)
    @JoinTable(name = "product_warehouse",
            joinColumns = @JoinColumn(name = "warehouse_id"),
            inverseJoinColumns = @JoinColumn(name = "product_id")
    )
    private Set<Product> products = new HashSet<>();

    public Warehouse() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Point getLocation() {
        return location;
    }

    public void setLocation(Point location) {
        this.location = location;
    }

    public Set<Product> getProducts() {
        return products;
    }

    public void setProducts(Set<Product> products) {
        this.products = products;
    }

    public boolean addProduct(final Product product){
        products.add(product);
        product.getWarehouses().add(this);
        return true;
    }

    public boolean deleteProduct(final Product product){
        products.remove(product);
        product.getWarehouses().remove(this);
        return true;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Warehouse warehouse = (Warehouse) o;
        return Objects.equals(name, warehouse.name) && Objects.equals(location, warehouse.location);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, location);
    }

    @Override
    public String toString() {
        return "Warehouse{" +
                "name='" + name + '\'' +
                ", location=" + location +
                ", products=" + products +
                '}';
    }
}
