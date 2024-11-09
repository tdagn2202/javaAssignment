package com.example.assignmentapp;

import java.sql.Date;
//import java.util.Date;

public class MyData {
    private String ProductCode;
    private Integer Quantity;
    private String ProductType, ProductName;
    private Date SaleDate;
    private double UnitPrice, Amount;

    public MyData(String ProductType, String ProductCode, String ProductName, Date SaleDate, int Quantity, double UnitPrice, double Amount) {
        this.ProductType = ProductType;
        this.ProductCode = ProductCode;
        this.ProductName = ProductName;
        this.SaleDate = SaleDate;
        this.Quantity = Quantity;
        this.UnitPrice = UnitPrice;
        this.Amount = Amount;
    }

    public String getProductCode() {
        return ProductCode;
    }

    public int getQuantity() {
        return Quantity;
    }

    public String getProductType() {
        return ProductType;
    }

    public String getProductName() {
        return ProductName;
    }

    public Date getSaleDate() {
        return SaleDate;
    }

    public double getUnitPrice() {
        return UnitPrice;
    }

    public double getAmount() {
        return Amount;
    }
}
