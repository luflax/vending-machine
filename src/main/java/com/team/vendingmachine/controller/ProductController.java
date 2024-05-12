package com.team.vendingmachine.controller;

import com.team.vendingmachine.enumeration.Coin;
import com.team.vendingmachine.model.Product;
import com.team.vendingmachine.service.IVendingMachineClient;
import com.team.vendingmachine.service.IVendingMachineOperator;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/v1/product")
public class ProductController {
    private final IVendingMachineClient vendingMachineClient;

    private final IVendingMachineOperator vendingMachineOperator;

    public ProductController(IVendingMachineClient vendingMachineClient, IVendingMachineOperator vendingMachineOperator) {
        this.vendingMachineClient = vendingMachineClient;
        this.vendingMachineOperator = vendingMachineOperator;
    }

    @GetMapping("")
    public List<Product> getAll() {
        return this.vendingMachineClient.getProducts();
    }

    @PostMapping("/buy")
    public void buy(int productNumber) {
        this.vendingMachineClient.buy(productNumber);
    }

    @PostMapping("/loadProducts")
    public void loadProducts(List<Product> products) {
        this.vendingMachineOperator.loadProducts(products);
    }
}
