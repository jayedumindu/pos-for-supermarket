package com.ijse_pos.view.tdm;

public class manageOrderTDM {

    private String itemCode;
    private Integer orderQty;
    private Integer qtyOnHand;
    private Double Discount;
    private Double totalPrice;

    public manageOrderTDM(String itemCode, Integer orderQty, Integer qtyOnHand, Double discount, Double totalPrice) {
        this.itemCode = itemCode;
        this.orderQty = orderQty;
        this.qtyOnHand = qtyOnHand;
        Discount = discount;
        this.totalPrice = totalPrice;
    }

    public String getItemCode() {
        return itemCode;
    }

    public void setItemCode(String itemName) {
        this.itemCode = itemName;
    }

    public Integer getOrderQty() {
        return orderQty;
    }

    public void setOrderQty(Integer orderQty) {
        this.orderQty = orderQty;
    }

    public Integer getQtyOnHand() {
        return qtyOnHand;
    }

    public void setQtyOnHand(Integer qtyOnHand) {
        this.qtyOnHand = qtyOnHand;
    }

    public Double getDiscount() {
        return Discount;
    }

    public void setDiscount(Double discount) {
        Discount = discount;
    }

    public Double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
    }

}
