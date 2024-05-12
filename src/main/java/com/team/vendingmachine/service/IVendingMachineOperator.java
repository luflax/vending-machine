package com.team.vendingmachine.service;

import com.team.vendingmachine.model.Product;

import java.util.Collection;

public interface IVendingMachineOperator {

    void loadProducts(Collection<Product> products);

}