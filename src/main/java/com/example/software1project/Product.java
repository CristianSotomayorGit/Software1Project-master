package com.example.software1project;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;


public class Product {

    private ObservableList<Part> associatedParts = FXCollections.observableArrayList();

    private int id;
    private String name;
    private int stock;
    private double price;
    private int minimum;
    private int maximum;

    public Product(int id, String name, double price, int stock, int minimum, int maximum) {
        this.id = id;
        this.name = name;
        this.stock = stock;
        this.price = price;
        this.minimum = minimum;
        this.maximum = maximum;
    }

    public Product() {
        this(0, null, 0.00, 0, 0, 0);
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public void setMinimum(int minimum) {
        this.minimum = minimum;
    }

    public void setMaximum(int maximum) {
        this.maximum = maximum;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getStock() {
        return stock;
    }

    public double getPrice() {
        return price;
    }

    public int getMinimum() {
        return minimum;
    }

    public int getMaximum() {
        return maximum;
    }


    public void addAssociatedPart(ObservableList<Part> part){
        this.associatedParts.addAll(part);
    }

    public void deleteAssociatedPart(ObservableList<Part> part){
        this.associatedParts.removeAll(part);
    }

    public ObservableList<Part> getAllAssociatedParts(){
        return associatedParts;
    }
}
