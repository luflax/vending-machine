package com.team.vendingmachine.enumeration;


public enum Coin {

    CENTS10(10),

    CENTS20(20),

    CENTS50(50),

    EURO1(100),

    EURO2(200);


    private final int value;


    Coin(int value) {

        this.value = value;

    }


    public int getValue() {

        return value;

    }

}