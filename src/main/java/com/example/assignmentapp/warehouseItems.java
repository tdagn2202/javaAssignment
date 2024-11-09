package com.example.assignmentapp;

public class warehouseItems {
    private Integer id;
    private String productID;
    private String productName;
    private int categoryID;
    private String unit;
    private int stock;
    private double price;
    private int quantityIn;
    private String image;
    private String productSupplier;  // New property for productSupplier

    // Modified constructor to include productSupplier
    public warehouseItems(String productID, String productName, int categoryID, String unit, int stock, double price, int quantityIn, String image, String productSupplier) {
        this.productID = productID;
        this.productName = productName;
        this.categoryID = categoryID;
        this.unit = unit;
        this.stock = stock;
        this.price = price;
        this.quantityIn = quantityIn;
        this.image = image;
        this.productSupplier = productSupplier;  // Initialize productSupplier
    }

    public String getProductID() {
        return productID;
    }

    public String getProductName() {
        return productName;
    }

    public int getCategoryID() {
        return categoryID;
    }

    public String getUnit() {
        return unit;
    }

    public int getStock() {
        return stock;
    }

    public double getPrice() {
        return price;
    }

    public int getQuantityIn() {
        return quantityIn;
    }

    public String getImage() {
        return image;
    }

    public String getProductSupplier() {  // Getter for productSupplier
        return productSupplier;
    }
}
