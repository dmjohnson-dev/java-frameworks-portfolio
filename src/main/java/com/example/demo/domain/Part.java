package com.example.demo.domain;

import javax.persistence.*;
import javax.validation.constraints.AssertTrue;
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

    @Min(0)
    private double price;

    @Min(0)
    private int inv;

    @Min(0)
    @Column(name = "min")
    private int min = 0;

    @Min(0)
    @Column(name = "max")
    private int max = Integer.MAX_VALUE;

    // Owning side of many-to-many (matches product_part table)
    @ManyToMany
    @JoinTable(
            name = "product_part",
            joinColumns = @JoinColumn(name = "part_id"),
            inverseJoinColumns = @JoinColumn(name = "product_id")
    )
    private Set<Product> products = new HashSet<>();

    // ----- getters / setters -----

    public long getId() { return id; }
    public void setId(long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }

    public int getInv() { return inv; }
    public void setInv(int inv) {
        this.inv = inv;
        // keep inventory within [min, max]
        if (this.max < this.min) this.max = this.min;
        if (this.inv < this.min) this.inv = this.min;
        if (this.inv > this.max) this.inv = this.max;
    }

    public int getMin() { return min; }
    public void setMin(int min) {
        this.min = Math.max(0, min);
        if (this.max < this.min) this.max = this.min;
        if (this.inv < this.min) this.inv = this.min;
    }

    public int getMax() { return max; }
    public void setMax(int max) {
        this.max = Math.max(0, max);
        if (this.max < this.min) this.max = this.min;
        if (this.inv > this.max) this.inv = this.max;
    }

    public Set<Product> getProducts() { return products; }
    public void setProducts(Set<Product> products) { this.products = products; }

    // ----- bean validation helpers (used in Part H too) -----

    @AssertTrue(message = "Max must be greater than or equal to Min")
    public boolean isMaxGteMin() {
        return max >= min;
    }

    @AssertTrue(message = "Inventory must be between Min and Max")
    public boolean isInvWithinBounds() {
        return inv >= min && inv <= max;
    }

    // ----- utility -----

    @Override
    public String toString() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Part)) return false;
        Part part = (Part) o;
        return id == part.id;
    }

    @Override
    public int hashCode() {
        return (int) (id ^ (id >>> 32));
    }
}
