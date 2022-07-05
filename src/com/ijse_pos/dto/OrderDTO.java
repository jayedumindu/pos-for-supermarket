package com.ijse_pos.dto;

public class OrderDTO {

    private String OrderID;
    private String CustomerID;

    public OrderDTO(String orderID, String customerID) {
        OrderID = orderID;
        CustomerID = customerID;
    }

    public String getOrderID() {
        return OrderID;
    }

    public void setOrderID(String orderID) {
        OrderID = orderID;
    }


    public String getCustomerID() {
        return CustomerID;
    }

    public void setCustomerID(String customerID) {
        CustomerID = customerID;
    }

}
