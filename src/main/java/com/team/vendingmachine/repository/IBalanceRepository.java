package com.team.vendingmachine.repository;

public interface IBalanceRepository {
    void add(double value);

    double getCurrentBalance();

    void subtract(double value);
}
