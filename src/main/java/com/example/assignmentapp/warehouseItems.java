package com.example.assignmentapp;

public class warehouseItems {
    private String productID;
    private String productName;
    private String supplier;
    private int categoryID;
    private String unit;
    private int stock;
    private double price;
    private int quantityIn;
    private String image;
    private String productSupplier;  // New property for productSupplier

    public warehouseItems(String productID, String productName, String supplier, int categoryID, String unit, int stock, double price, int quantityIn, String image) {
        this.productID = productID;
        this.productName = productName;
        this.supplier = supplier;
        this.categoryID = categoryID;
        this.unit = unit;
        this.stock = stock;
        this.price = price;
        this.quantityIn = quantityIn;
        this.image = image;
    }

    public String getProductID() {
        return productID;
    }

    public void setProductID(String productID) {
        this.productID = productID;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getSupplier() {
        return supplier;
    }

    public void setSupplier(String supplier) {
        this.supplier = supplier;
    }

    public int getCategoryID() {
        return categoryID;
    }

    public void setCategoryID(int categoryID) {
        this.categoryID = categoryID;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getQuantityIn() {
        return quantityIn;
    }

    public void setQuantityIn(int quantityIn) {
        this.quantityIn = quantityIn;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getProductSupplier() {
        return productSupplier;
    }

    public void setProductSupplier(String productSupplier) {
        this.productSupplier = productSupplier;
    }
}
