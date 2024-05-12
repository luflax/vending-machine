package com.team.vendingmachine.repository;

import com.team.vendingmachine.model.Product;

import java.util.Collection;

public interface IProductRepository {
    void register(Product product);

    Collection<Product> getAll();

    Product get(int productNumber);

    void removeItem(int productNumber);
}
