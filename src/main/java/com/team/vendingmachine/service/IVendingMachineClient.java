package com.team.vendingmachine.service;

import com.team.vendingmachine.enumeration.Coin;
import com.team.vendingmachine.model.Product;

import java.util.Collection;
import java.util.List;


public interface IVendingMachineClient {

    double getBalance();

    List<Product> getProducts();


    void insertCoin(Coin coin);

    Collection<Coin> returnMoney();

    Product buy(int productNumber);

}