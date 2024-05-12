package com.team.vendingmachine.repository;

import org.springframework.stereotype.Repository;

import java.math.BigDecimal;

@Repository
public class BalanceRepositoryImpl implements IBalanceRepository {
    private BigDecimal currentBalance = new BigDecimal(0);

    @Override
    public void add(double value) {
        synchronized (this) {
            this.currentBalance = this.currentBalance.add(BigDecimal.valueOf(value));
        }
    }

    @Override
    public double getCurrentBalance() {
        synchronized (this) {
            return currentBalance.doubleValue();
        }
    }

    @Override
    public void subtract(double value) {
        synchronized (this) {
            this.currentBalance = this.currentBalance.subtract(BigDecimal.valueOf(value));
        }
    }
}
