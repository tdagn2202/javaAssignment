package com.example.assignmentapp;

public class discountObject {
    private String categoryID;
    private String caterogyName;
    private int discountRate;

    public discountObject(String categoryID, String caterogyName, int discountRate) {
        this.categoryID = categoryID;
        this.caterogyName = caterogyName;
        this.discountRate = discountRate;
    }


    public String getCategoryID() {
        return categoryID;
    }

    public void setCategoryID(String categoryID) {
        this.categoryID = categoryID;
    }

    public String getCaterogyName() {
        return caterogyName;
    }

    public void setCaterogyName(String caterogyName) {
        this.caterogyName = caterogyName;
    }

    public int getDiscountRate() {
        return discountRate;
    }

    public void setDiscountRate(int discountRate) {
        this.discountRate = discountRate;
    }


}
