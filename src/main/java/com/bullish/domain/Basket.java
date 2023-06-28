package com.bullish.domain;

import java.util.List;

public class Basket {
    private List<Product> products;

    public Basket(List<Product> products) {
        this.products = products;
    }

    public Basket(){

    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public void addProduct(Product product) {
        products.add(product);
    }

    public void removeProduct(int productId) {
        products.removeIf(p -> p.getId() == productId);
    }
}