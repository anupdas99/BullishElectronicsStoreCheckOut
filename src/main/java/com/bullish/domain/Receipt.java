package com.bullish.domain;

import java.util.List;

import java.util.List;

public class Receipt {
    private List<Product> products;
    private double subtotal;
    private double discount;
    private double total;

    public Receipt(List<Product> products, double subtotal, double discount, double total) {
        this.products = products;
        this.subtotal = subtotal;
        this.discount = discount;
        this.total = total;
    }

    public List<Product> getProducts() {
        return products;
    }

    public double getSubtotal() {
        return subtotal;
    }

    public double getDiscount() {
        return discount;
    }

    public double getTotal() {
        return total;
    }
}


