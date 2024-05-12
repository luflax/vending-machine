package com.team.vendingmachine.repository;

import com.team.vendingmachine.model.Product;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@Repository
public class ProductRepositoryImpl implements IProductRepository {
    private final Map<Integer, Product> productsByNumber = Collections.synchronizedMap(new HashMap<>());

    @Override
    public void register(Product product) {
        this.productsByNumber.put(product.getProductNumber(), product);
    }

    @Override
    public Collection<Product> getAll() {
        return this.productsByNumber.values();
    }

    @Override
    public Product get(int productNumber) {
        return this.productsByNumber.get(productNumber);
    }

    @Override
    public void removeItem(int productNumber) {
        Product product = this.productsByNumber.get(productNumber);
        product.setCount(product.getCount() - 1);
    }
}
