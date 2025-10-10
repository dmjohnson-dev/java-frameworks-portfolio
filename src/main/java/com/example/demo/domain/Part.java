package com.example.demo.domain;

import javax.persistence.*;
import javax.validation.constraints.Min;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "parts")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "part_type")
public abstract class Part implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String name;

    @Min(value = 0, message = "Price must be ≥ 0")
    private double price;

    @Min(value = 0, message = "Inventory must be ≥ 0")
    private int inv;

    @Min(value = 0, message = "Min must be ≥ 0")
    @Column(name = "min")
    private int min = 2;   // default to 2 (Part G)

    @Min(value = 0, message = "Max must be ≥ 0")
    @Column(name = "max")
    private int max = 10;  // reasonable default

    @ManyToMany
    @JoinTable(
            name = "product_part",
            joinColumns = @JoinColumn(name = "part_id"),
            inverseJoinColumns = @JoinColumn(name = "product_id")
    )
    private Set<Product> products = new HashSet<>();

    // --- lifecycle: ensure min default at persist ---
    @PrePersist
    public void prePersist() {
        if (min <= 0) min = 2; // per requirement: minimums are 2
        if (max < min) max = min;
        if (inv < 0) inv = 0;
    }

    // --- getters/setters ---
    public long getId() { return id; }
    public void setId(long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }

    public int getInv() { return inv; }
    public void setInv(int inv) { this.inv = inv; }

    public int getMin() { return min; }
    public void setMin(int min) { this.min = min; }

    public int getMax() { return max; }
    public void setMax(int max) { this.max = max; }

    public Set<Product> getProducts() { return products; }
    public void setProducts(Set<Product> products) { this.products = products; }
}
