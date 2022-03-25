package com.example.software1project;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Inventory {

    private static ObservableList<Part> allParts = FXCollections.observableArrayList();

    private static ObservableList<Product> allProducts = FXCollections.observableArrayList();

    public static void addPart(Part newPart) {
        allParts.add(newPart);
    }

    public static void addProduct(Product newProduct) {
        allProducts.add(newProduct);
    }

    public static Part lookupPart(int partId) {

        Part temp = null;

        for (Part part : allParts) {
            if (partId == part.getId()) {
                temp = part;
            }
        }
        return temp;
    }

    public static Product lookupProduct(int productId) {

        Product temp = null;

        for (Product product : allProducts) {
            if (productId == product.getId()) {
                temp = product;
            }
        }
        return temp;
    }

    public static ObservableList lookupPart(String partName) {
        ObservableList<Part> retrievedPart = FXCollections.observableArrayList();

        if (partName.length() > 0) {
            for (int i = 0; i < allParts.size(); i++) {
                if (allParts.get(i).getName().toLowerCase().contains(partName.toLowerCase())) {
                    retrievedPart.add(allParts.get(i));
                } else {
                    retrievedPart = allParts;
                }
            }
        }
        else {
            retrievedPart = allParts;
        }
        return retrievedPart;
    }

    public static ObservableList lookupProduct(String productName) {
        ObservableList<Product> retrievedProduct = FXCollections.observableArrayList();

        if (productName.length() > 0) {
            for (int i = 0; i < allProducts.size(); i++) {
                if (allProducts.get(i).getName().toLowerCase().contains(productName.toLowerCase())) {
                    retrievedProduct.add(allProducts.get(i));
                }
            }
        }
        else {
            retrievedProduct = allProducts;
        }
        return retrievedProduct;
    }

    public static void updatePart(int index, Part selectedPart) {
        allParts.set(index, selectedPart);
    }

    public static void updateProduct(int index, Product newProduct) {
        allProducts.set(index, newProduct);
    }

    public static boolean deletePart(Part selectedPart) {
        allParts.remove(selectedPart);
        return true;
    }

    public static boolean deleteProduct(Product selectedProduct) {
        allProducts.remove(selectedProduct);
        return true;
    }

    public static ObservableList<Part> getAllParts() {
        return allParts;
    }

    public static ObservableList<Product> getAllProducts() {
        return allProducts;
    }
}