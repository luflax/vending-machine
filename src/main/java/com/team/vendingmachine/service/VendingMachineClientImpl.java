package com.team.vendingmachine.service;

import com.team.vendingmachine.enumeration.Coin;
import com.team.vendingmachine.model.Product;
import com.team.vendingmachine.repository.IBalanceRepository;
import com.team.vendingmachine.repository.IProductRepository;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class VendingMachineClientImpl implements IVendingMachineClient {
    private final IBalanceRepository balanceRepository;
    private final IProductRepository productRepository;

    public VendingMachineClientImpl(IBalanceRepository balanceRepository, IProductRepository productRepository) {
        this.balanceRepository = balanceRepository;
        this.productRepository = productRepository;
    }

    @Override
    public double getBalance() {
        return this.balanceRepository.getCurrentBalance();
    }

    @Override
    public List<Product> getProducts() {
        return new ArrayList<>(this.productRepository.getAll());
    }

    @Override
    public void insertCoin(Coin coin) {
        this.balanceRepository.add(coin.getValue());
    }

    @Override
    public Collection<Coin> returnMoney() {
        double currentBalance = this.balanceRepository.getCurrentBalance();
        if(currentBalance <= 0) {
            throw new RuntimeException("No money available to return");
        }

        PriorityQueue<Coin> coinsPriority = new PriorityQueue<>((coinA, coinB) -> coinB.getValue() - coinA.getValue());
        coinsPriority.addAll(Arrays.asList(Coin.values()));

        List<Coin> coinsToReturn = new ArrayList<>();
        while(!coinsPriority.isEmpty()) {
            Coin coin = coinsPriority.poll();
            if(currentBalance < coin.getValue()) {
                continue;
            }

            int coinsCount = (int) (currentBalance / coin.getValue());
            for (int i = 0; i < coinsCount; i++) {
                coinsToReturn.add(coin);
            }

            if(currentBalance % coin.getValue() == 0) {
                break;
            }
            currentBalance -= coinsCount * coin.getValue();
        }

        return coinsToReturn;
    }

    @Override
    public Product buy(int productNumber) {
        Product product = this.productRepository.get(productNumber);
        if(product == null) {
            throw new RuntimeException("Product is not registered.");
        }
        if(product.getCount() <= 0) {
            throw new RuntimeException("Product is not available.");
        }

        double currentBalance = this.balanceRepository.getCurrentBalance();
        double productPrice = product.getPrice();
        if(currentBalance < productPrice) {
            throw new RuntimeException("Balance is not enough.");
        }

        this.balanceRepository.subtract(productPrice);
        this.productRepository.removeItem(productNumber);
        return product;
    }
}
