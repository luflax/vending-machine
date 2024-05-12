package com.team.vendingmachine.enumeration;

public class CoinFactory {
    public static Coin buildCoin(int value) {
        for (Coin coin : Coin.values()) {
            if (coin.getValue() == value) {
                return coin;
            }
        }

        return null;
    }
}
