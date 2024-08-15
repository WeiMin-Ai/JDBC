package com.java3.bean;

public class Order {
    private String orId;
    private String ordName;

    public Order() {
        super();
    }

    public Order(String orId, String ordName) {
        this.orId = orId;
        this.ordName = ordName;
    }

    public String getOrId() {
        return orId;
    }

    public void setOrId(String orId) {
        this.orId = orId;
    }

    public String getOrdName() {
        return ordName;
    }

    public void setOrdName(String ordName) {
        this.ordName = ordName;
    }

    @Override
    public String toString() {
        return "Order{" +
                "orId='" + orId + '\'' +
                ", ordName='" + ordName + '\'' +
                '}';
    }
}
