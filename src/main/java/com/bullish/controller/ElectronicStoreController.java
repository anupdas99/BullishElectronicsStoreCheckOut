package com.bullish.controller;

import com.bullish.domain.Deal;
import com.bullish.domain.Product;
import com.bullish.domain.Receipt;
import com.bullish.service.ElectronicStoreService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
public class ElectronicStoreController {

    private ElectronicStoreService electronicStoreService;

    public ElectronicStoreController(ElectronicStoreService electronicStoreService) {
        this.electronicStoreService = electronicStoreService;
    }

    @PostMapping("/products")
    public ResponseEntity<Product> createProduct(@RequestBody Product product) {
        Product createdProduct = electronicStoreService.createProduct(product);
        return new ResponseEntity<>(createdProduct, HttpStatus.OK);
    }

    @GetMapping("/getProducts")
    public ResponseEntity<Map<Integer, Product>> getProducts() {
        Map<Integer, Product> product = electronicStoreService.getProducts();
        if (!product.isEmpty()) {
            return new ResponseEntity<>(product, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/products/{productId}")
    public ResponseEntity<Void> removeProduct(@PathVariable int productId) {
        electronicStoreService.removeProduct(productId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/deals")
    public ResponseEntity<Void> addDeal(@RequestBody Deal deal) {
        electronicStoreService.addDeal(deal);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/basket/{productId}")
    public ResponseEntity<Void> addToBasket(@PathVariable int productId, @RequestParam String customerId) {
        electronicStoreService.addToBasket(productId, customerId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/basket/{productId}")
    public ResponseEntity<Void> removeFromBasket(@PathVariable int productId, @RequestParam String customerId) {
        electronicStoreService.removeFromBasket(productId, customerId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/receipt")
    public ResponseEntity<Receipt> calculateReceipt(@RequestParam String customerId) {
        Receipt receipt = electronicStoreService.calculateReceipt(customerId);
        if (receipt != null) {
            return new ResponseEntity<>(receipt, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}

