package com.team.vendingmachine.service;

import com.team.vendingmachine.model.Product;
import com.team.vendingmachine.repository.ProductRepositoryImpl;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@SpringBootTest
class VendingMachineOperatorImplTest {

    @Autowired
    private IVendingMachineOperator vendingMachineOperator;

    @MockBean
    private ProductRepositoryImpl productRepository;

    @Test
    void loadProducts_whenValidProductsAreLoaded_thenCallRepository() {
        // given
        List<Product> products = new ArrayList<>(3);
        Product p1 = new Product(1, "Snickers", 100, 20);
        Product p2 = new Product(2, "Water", 120, 10);
        Product p3 = new Product(3, "Orange Juice", 150, 10);
        products.add(p1);
        products.add(p2);
        products.add(p3);

        // when
        this.vendingMachineOperator.loadProducts(products);

        // then
        verify(productRepository, times(products.size())).register(Mockito.any());
    }

    @Test
    void loadProducts_whenProductsWithPriceNotMultipleOf10_thenThrowsException() {
        // given
        List<Product> products = new ArrayList<>(3);
        Product p1 = new Product(1, "Snickers", 100, 20);
        Product p2 = new Product(2, "Water", 125, 10);
        Product p3 = new Product(3, "Orange Juice", 132, 10);
        products.add(p1);
        products.add(p2);
        products.add(p3);

        // when
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            this.vendingMachineOperator.loadProducts(products);
        });


        // then
        assertEquals("The price of the product must be a multiple of 10.", exception.getMessage());
    }

    @Test
    void loadProducts_whenProductsWithInvalidQuantity_thenThrowsException() {
        // given
        List<Product> products = new ArrayList<>(3);
        Product p1 = new Product(1, "Snickers", 100, 20);
        Product p2 = new Product(2, "Water", 120, 10);
        Product p3 = new Product(3, "Orange Juice", 130, -1);
        products.add(p1);
        products.add(p2);
        products.add(p3);

        // when
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            this.vendingMachineOperator.loadProducts(products);
        });


        // then
        assertEquals("Invalid quantity, it needs to be at least 0.", exception.getMessage());
    }

    @Test
    void loadProducts_whenProductAlreadyRegistered_thenThrowsException() {
        // given
        List<Product> products = new ArrayList<>(3);
        Product p1 = new Product(1, "Snickers", 100, 20);
        Product p2 = new Product(2, "Water", 120, 10);
        Product p3 = new Product(3, "Orange Juice", 130, 2);
        products.add(p1);
        products.add(p2);
        products.add(p3);
        given(this.productRepository.get(2)).willReturn(p2);

        // when
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            this.vendingMachineOperator.loadProducts(products);
        });


        // then
        assertEquals("There's already a product registered with this number.", exception.getMessage());
    }
}