package com.team.vendingmachine.model;

public class Product {

    private int productNumber;

    private String name;

    private double price;

    private int count;


    // Constructor

    public Product(int productNumber, String name, double price, int count) {

        this.productNumber = productNumber;

        this.name = name;

        this.price = price;

        this.count = count;

    }


    // Getters and Setters

    public int getProductNumber() {

        return productNumber;

    }


    public void setProductNumber(int productNumber) {

        this.productNumber = productNumber;

    }


    public String getName() {

        return name;

    }


    public void setName(String name) {

        this.name = name;

    }


    public double getPrice() {

        return price;

    }


    public void setPrice(double price) {

        this.price = price;

    }


    public int getCount() {

        return count;

    }


    public void setCount(int count) {

        this.count = count;

    }

}