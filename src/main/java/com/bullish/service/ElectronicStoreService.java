package com.bullish.service;

import com.bullish.domain.*;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class ElectronicStoreService {
    private Map<Integer, Product> products;
    private Map<String, Basket> baskets;
    private Map<Integer, DealType> deals;

    public ElectronicStoreService() {
        products = new ConcurrentHashMap<>();
        baskets = new ConcurrentHashMap<>();
        deals = new ConcurrentHashMap<>();
    }

    public Product createProduct(Product product) {
        int productId = generateProductId();
        product.setId(productId);
        products.put(productId, product);
        return product;
    }

    public Map<Integer, Product> getProducts() {
        return products;
    }

    public void removeProduct(int productId) {
        products.remove(productId);
    }

    public void addDeal(Deal deal) {
        deals.put(deal.getProductId(), deal.getDealType());
    }

    public void addToBasket(int productId, String customerId) {
        Product product = products.get(productId);
        if (product != null) {
            Basket basket = baskets.computeIfAbsent(customerId, key -> new Basket(new ArrayList<Product>()));
            basket.addProduct(product);
        }
    }

    public void removeFromBasket(int productId, String customerId) {
        Basket basket = baskets.get(customerId);
        if (basket != null) {
            basket.removeProduct(productId);
        }
    }

    public Receipt calculateReceipt(String customerId) {
        Basket basket = baskets.get(customerId);
        if (basket != null) {
            List<Product> productsInBasket = basket.getProducts();
            double subtotal = calculateSubtotal(productsInBasket);
            double discount = calculateDiscount(productsInBasket);
            double total = subtotal - discount;

            return new Receipt(productsInBasket, subtotal, discount, total);
        }
        return null;
    }

    private double calculateSubtotal(List<Product> productsInBasket) {
        double subtotal = 0;
        for (Product product : productsInBasket) {
            subtotal += product.getPrice();
        }
        return subtotal;
    }

    private double calculateDiscount(List<Product> productsInBasket) {
        double discount = 0;
        for (Product product : productsInBasket) {
            if (deals.containsKey(product.getId())) {
                DealType dealType = deals.get(product.getId());
                discount += dealType.applyDiscount(product.getPrice());
            }
        }
        return discount;
    }

    private int generateProductId() {
        return products.size() + 1;
    }
}
