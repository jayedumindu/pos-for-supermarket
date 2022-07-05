package com.ijse_pos.view.tdm;

import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;

public class ItemTDM extends RecursiveTreeObject<ItemTDM> {

    private String ItemCode;
    private String Description;
    private String PackSize;
    private Double UnitPrice;
    private Double maxDiscount;
    private Integer QtyOnHand;

    public Double getMaxDiscount() {
        return maxDiscount;
    }

    public void setMaxDiscount(Double maxDiscount) {
        this.maxDiscount = maxDiscount;
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

    public ItemTDM(String itemCode, String description, String packSize, Double unitPrice, Double maxDiscount, Integer qtyOnHand) {
        ItemCode = itemCode;
        Description = description;
        PackSize = packSize;
        UnitPrice = unitPrice;
        this.maxDiscount = maxDiscount;
        QtyOnHand = qtyOnHand;
    }
}
