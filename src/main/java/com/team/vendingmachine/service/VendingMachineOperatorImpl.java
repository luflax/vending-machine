package com.team.vendingmachine.service;

import com.team.vendingmachine.model.Product;
import com.team.vendingmachine.repository.IProductRepository;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class VendingMachineOperatorImpl implements IVendingMachineOperator {
    private final IProductRepository productRepository;

    public VendingMachineOperatorImpl(IProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public void loadProducts(Collection<Product> products) {
        for (Product product : products) {
            if(product.getPrice() % 10 != 0) {
                throw new RuntimeException("The price of the product must be a multiple of 10.");
            }
            if(product.getCount() < 0) {
                throw new RuntimeException("Invalid quantity, it needs to be at least 0.");
            }
            Product existingProduct = this.productRepository.get(product.getProductNumber());
            if(existingProduct != null) {
                throw new RuntimeException("There's already a product registered with this number.");
            }

            this.productRepository.register(product);
        }
    }
}
