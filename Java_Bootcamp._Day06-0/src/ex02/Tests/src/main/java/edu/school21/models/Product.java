package edu.school21.models;

import java.util.Objects;

public class Product {
    private Long identifier;
    private String name;
    private Long price;

    public Product(Long identifier, String name, Long price) {
        this.identifier = identifier;
        this.name = name;
        this.price = price;
    }

    public Long getIdentifier() {
        return identifier;
    }

    public void setIdentifier(Long identifier) {
        this.identifier = identifier;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getPrice() {
        return price;
    }

    public void setPrice(Long price) {
        this.price = price;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Product product = (Product) obj;
        return Objects.equals(identifier, product.identifier)
                && Objects.equals(name, product.name) && Objects.equals(price, product.price);
    }

    @Override
    public int hashCode() {
        return Objects.hash(identifier, name, price);
    }

    @Override
    public String toString() {
        return "Product{" + "identifier=" + identifier +
                ", name='" + name + '\'' + ", price=" + price + '}';
    }
}