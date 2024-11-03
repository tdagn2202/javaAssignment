package com.example.assignmentapp;

public class tabData {
    private Double totalPrice;
    private Double miniTotal;
    private Double discount;
    private Double exchangeMoney;

    // Constructor, getters, and setters
    public tabData(Double totalPrice, Double miniTotal, Double discount, Double exchangeMoney) {
        this.totalPrice = totalPrice;
        this.miniTotal = miniTotal;
        this.discount = discount;
        this.exchangeMoney = exchangeMoney;
    }

    public Double getTotalPrice() { return totalPrice; }
    public void setTotalPrice(Double totalPrice) { this.totalPrice = totalPrice; }
    public Double getMiniTotal() { return miniTotal; }
    public void setMiniTotal(Double miniTotal) { this.miniTotal = miniTotal; }
    public Double getDiscount() { return discount; }
    public void setDiscount(Double discount) { this.discount = discount; }
    public Double getExchangeMoney() { return exchangeMoney; }
    public void setExchangeMoney(Double exchangeMoney) { this.exchangeMoney = exchangeMoney; }
}
