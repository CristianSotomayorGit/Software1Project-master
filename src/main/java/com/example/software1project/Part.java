package com.example.software1project;

public abstract class Part {
    private int partId;
    private String name;
    private double price;
    private int stock;
    private int minimum;
    private int maximum;

    public Part(int partId, String name, double price, int stock, int minimum, int maximum) {
        this.partId = partId;
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.minimum = minimum;
        this.maximum = maximum;
    }

    public int getId() {
        return partId;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public int getStock() {
        return stock;
    }

    public int getMinimum() {
        return minimum;
    }

    public int getMaximum() {
        return maximum;
    }

    public void setId(int partId) {
        this.partId = partId;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public void setMinimum(int minimum) {
        this.minimum = minimum;
    }

    public void setMax(int maximum) {
        this.maximum = maximum;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPartCost(double price) {
        this.price = price;
    }
}