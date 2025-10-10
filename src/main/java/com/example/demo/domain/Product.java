package com.example.demo.domain;

import javax.persistence.*;
import javax.validation.constraints.Min;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "products")
public class Product implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String name;

    @Min(value = 0, message = "Price must be ≥ 0")
    private double price;

    @Min(value = 0, message = "Inventory must be ≥ 0")
    private int inv;

    @ManyToMany(mappedBy = "products", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private Set<Part> parts = new HashSet<>();

    public Product() {}
    public Product(String name, double price, int inv) {
        this.name = name; this.price = price; this.inv = inv;
    }

    // getters/setters
    public long getId() { return id; }
    public void setId(long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }

    public int getInv() { return inv; }
    public void setInv(int inv) { this.inv = inv; }

    public Set<Part> getParts() { return parts; }
    public void setParts(Set<Part> parts) { this.parts = parts; }

    @Override public String toString() { return name; }
    @Override public int hashCode() { return Long.hashCode(id); }
    @Override public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Product)) return false;
        return id == ((Product) o).id;
    }
}
