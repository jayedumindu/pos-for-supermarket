package com.ijse_pos.view.tdm;

public class CartTDM {

    private String ItemCode;
    private String totPackSize;
    private Integer totQty;
    private Double totPrice;
    private Double Discount;

    public CartTDM( String ItemCode, String totPackSize, Integer totQty, Double totPrice, Double discount) {
        this.ItemCode = ItemCode;
        this.totPackSize = totPackSize;
        this.totQty = totQty;
        this.totPrice = totPrice;
        this.Discount = discount;
    }

    public String getItemCode() {
        return ItemCode;
    }

    public void setItemCode(String itemCode) {
        ItemCode = itemCode;
    }

    public Double getDiscount() {
        return Discount;
    }

    public void setDiscount(Double discount) {
        Discount = discount;
    }

    public String getTotPackSize() {
        return totPackSize;
    }

    public void setTotPackSize(String totPackSize) {
        this.totPackSize = totPackSize;
    }

    public Integer getTotQty() {
        return totQty;
    }

    public void setTotQty(Integer totQty) {
        this.totQty = totQty;
    }

    public Double getTotPrice() {
        return totPrice;
    }

    public void setTotPrice(Double totPrice) {
        this.totPrice = totPrice;
    }
}
