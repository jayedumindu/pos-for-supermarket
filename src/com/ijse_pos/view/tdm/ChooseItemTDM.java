package com.ijse_pos.view.tdm;

public class ChooseItemTDM {

    private String ItemCode;
    private String Description;
    private String PackSize;
    private Double UnitPrice;
    private Integer QtyOnHand;
    private Double Discount;

    public ChooseItemTDM(String ItemCode, String description, String packSize, Double unitPrice, Integer qtyOnHand, Double discount) {
        this.ItemCode = ItemCode;
        Description = description;
        PackSize = packSize;
        UnitPrice = unitPrice;
        QtyOnHand = qtyOnHand;
        Discount = discount;
    }

    public Double getDiscount() {
        return Discount;
    }

    public void setDiscount(Double discount) {
        Discount = discount;
    }

    public String getItemCode() {
        return ItemCode;
    }

    public void setItemCode(String itemCode) {
        ItemCode = itemCode;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getPackSize() {
        return PackSize;
    }

    public void setPackSize(String packSize) {
        PackSize = packSize;
    }

    public Double getUnitPrice() {
        return UnitPrice;
    }

    public void setUnitPrice(Double unitPrice) {
        UnitPrice = unitPrice;
    }

    public Integer getQtyOnHand() {
        return QtyOnHand;
    }

    public void setQtyOnHand(Integer qtyOnHand) {
        QtyOnHand = qtyOnHand;
    }

}

