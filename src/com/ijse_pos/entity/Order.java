package com.ijse_pos.entity;

public class Order {

    private String OrderID;
    private String OrderDate;
    private String CustomerID;

    public Order(String orderID, String orderDate, String customerID) {
        OrderID = orderID;
        OrderDate = orderDate;
        CustomerID = customerID;
    }

    public Order(String orderID, String customerID) {
        OrderID = orderID;
        CustomerID = customerID;
    }

    public String getOrderID() {
        return OrderID;
    }

    public void setOrderID(String orderID) {
        OrderID = orderID;
    }

    public String getOrderDate() {
        return OrderDate;
    }

    public void setOrderDate(String orderDate) {
        OrderDate = orderDate;
    }

    public String getCustomerID() {
        return CustomerID;
    }

    public void setCustomerID(String customerID) {
        CustomerID = customerID;
    }

}
