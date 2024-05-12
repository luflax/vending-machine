package com.team.vendingmachine.service;

import com.team.vendingmachine.enumeration.Coin;
import com.team.vendingmachine.model.Product;
import com.team.vendingmachine.repository.BalanceRepositoryImpl;
import com.team.vendingmachine.repository.ProductRepositoryImpl;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@SpringBootTest
class VendingMachineClientImplTest {
    @Autowired
    private IVendingMachineClient vendingMachineClient;

    @MockBean
    private BalanceRepositoryImpl balanceRepository;

    @MockBean
    private ProductRepositoryImpl productRepository;


    @Test
    void getBalance_whenCalled_thenReturnCurrentBalance() {
        // given
        given(balanceRepository.getCurrentBalance()).willReturn(500.0);

        // when
        double balance = this.vendingMachineClient.getBalance();

        // then
        assertEquals(500.0, balance);
    }

    @Test
    void getProducts() {
        // given
        List<Product> mockProducts = new ArrayList<>(2);
        Product p1 = new Product(1, "Snickers", 1.0, 20);
        Product p2 = new Product(2, "Water", 1.25, 10);
        mockProducts.add(p1);
        mockProducts.add(p2);
        given(productRepository.getAll()).willReturn(mockProducts);

        // when
        List<Product> products = this.vendingMachineClient.getProducts();
        // then
        verify(productRepository, times(1)).getAll();
        assertEquals(2, products.size());

    }

    @Test
    void insertCoin_whenInsert50Cents_thenIncreaseBalance() {
        // when
        this.vendingMachineClient.insertCoin(Coin.CENTS50);

        // then
        verify(balanceRepository, times(1)).add(Mockito.eq(50.0));
    }

    @Test
    void returnMoney_whenBalanceIs200Cents_thenReturn2EuroCoin() {
        // given
        given(balanceRepository.getCurrentBalance()).willReturn(200.0);

        // when
        Collection<Coin> coins = this.vendingMachineClient.returnMoney();
        // then
        assertEquals(1, coins.size());
        for (Coin coin : coins) {
            assertEquals(coin, Coin.EURO2);
        }
    }

    @Test
    void returnMoney_whenBalanceIs240Cents_thenReturn2EuroAnd20CentsCoin() {
        // given
        given(balanceRepository.getCurrentBalance()).willReturn(240.0);

        // when
        Collection<Coin> coins = this.vendingMachineClient.returnMoney();

        // then
        assertEquals(3, coins.size());
        Iterator<Coin> iterator = coins.iterator();
        assertEquals(iterator.next(), Coin.EURO2);
        assertEquals(iterator.next(), Coin.CENTS20);
        assertEquals(iterator.next(), Coin.CENTS20);
    }

    @Test
    void returnMoney_whenBalanceIs230Cents_thenReturn2EuroAnd20CentsAnd10CentsCoin() {
        // given
        given(balanceRepository.getCurrentBalance()).willReturn(230.0);

        // when
        Collection<Coin> coins = this.vendingMachineClient.returnMoney();

        // then
        assertEquals(3, coins.size());
        Iterator<Coin> iterator = coins.iterator();
        assertEquals(iterator.next(), Coin.EURO2);
        assertEquals(iterator.next(), Coin.CENTS20);
        assertEquals(iterator.next(), Coin.CENTS10);
    }

    @Test
    void returnMoney_whenBalanceIsZero_thenThrowsException() {
        // given
        given(balanceRepository.getCurrentBalance()).willReturn(0.0);

        // when
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            this.vendingMachineClient.returnMoney();
        });


        // then
        assertEquals("No money available to return", exception.getMessage());
    }

    @Test
    void buy_whenProductIsRegistered_thenSubtractMoneyAndReturnProduct() {
        // given
        given(balanceRepository.getCurrentBalance()).willReturn(200.0);
        Product mockProduct = new Product(45, "Orange Juice", 100.0, 5);
        given(productRepository.get(45)).willReturn(mockProduct);

        // when
        Product product = this.vendingMachineClient.buy(45);

        // then
        verify(balanceRepository, times(1)).subtract(Mockito.eq(100.0));
        verify(productRepository, times(1)).removeItem(Mockito.eq(45));
        assertEquals(45, product.getProductNumber());
        assertEquals("Orange Juice", product.getName());
        assertEquals(5, product.getCount());
        assertEquals(100.0, product.getPrice());
    }
}