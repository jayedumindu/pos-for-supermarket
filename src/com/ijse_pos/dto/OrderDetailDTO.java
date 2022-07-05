package com.ijse_pos.dto;

public class OrderDetailDTO {

    private String OrderID;
    private String ItemCode;
    private Integer orderQty;
    private Double Discount;
    private Double totalPrice;

    public OrderDetailDTO(String orderID, String itemCode, Integer orderQty, Double discount, Double totalPrice) {
        OrderID = orderID;
        ItemCode = itemCode;
        this.orderQty = orderQty;
        Discount = discount;
        this.totalPrice = totalPrice;
    }

    public Double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getOrderID() {
        return OrderID;
    }

    public void setOrderID(String orderID) {
        OrderID = orderID;
    }

    public String getItemCode() {
        return ItemCode;
    }

    public void setItemCode(String itemCode) {
        ItemCode = itemCode;
    }

    public Integer getOrderQty() {
        return orderQty;
    }

    public void setOrderQty(Integer orderQty) {
        this.orderQty = orderQty;
    }

    public Double getDiscount() {
        return Discount;
    }

    public void setDiscount(Double discount) {
        Discount = discount;
    }

}
