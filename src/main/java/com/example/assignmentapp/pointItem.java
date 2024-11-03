package com.example.assignmentapp;

import javafx.beans.property.SimpleStringProperty;

public class pointItem {

    private SimpleStringProperty phoneNumber;
    private SimpleStringProperty customerName;
    private SimpleStringProperty currentPoint;

    public pointItem(String phoneNumber, String customerName, String currentPoint) {
        this.phoneNumber = new SimpleStringProperty(phoneNumber);
        this.customerName = new SimpleStringProperty(customerName);
        this.currentPoint = new SimpleStringProperty(currentPoint);
    }

    public pointItem() {
        this.phoneNumber = new SimpleStringProperty("");
        this.customerName = new SimpleStringProperty("");
        this.currentPoint = new SimpleStringProperty("");
    }

    public String getPhoneNumber() {
        return phoneNumber.get();
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber.set(phoneNumber);
    }

    public String getCustomerName() {
        return customerName.get();
    }

    public void setCustomerName(String customerName) {
        this.customerName.set(customerName);
    }

    public String getCurrentPoint() {
        return currentPoint.get();
    }

    public void setCurrentPoint(String currentPoint) {
        this.currentPoint.set(currentPoint);
    }

    public SimpleStringProperty phoneNumberProperty() {
        return phoneNumber;
    }

    public SimpleStringProperty customerNameProperty() {
        return customerName;
    }

    public SimpleStringProperty currentPointProperty() {
        return currentPoint;
    }
}
