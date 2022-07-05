package com.ijse_pos.dto;

public class ReportDTO {
    private String itemCode;
    private Double totalEarned;
    private Integer totalItemsSold;

    public ReportDTO(String itemCode, Double totalEarned, Integer totalItemsSold) {
        this.itemCode = itemCode;
        this.totalEarned = totalEarned;
        this.totalItemsSold = totalItemsSold;
    }

    public String getItemCode() {
        return itemCode;
    }

    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }

    public Double getTotalEarned() {
        return totalEarned;
    }

    public void setTotalEarned(Double totalEarned) {
        this.totalEarned = totalEarned;
    }

    public Integer getTotalItemsSold() {
        return totalItemsSold;
    }

    public void setTotalItemsSold(Integer totalItemsSold) {
        this.totalItemsSold = totalItemsSold;
    }

    @Override
    public String toString() {
        return "ReportDTO{" +
                "itemCode='" + itemCode + '\'' +
                ", totalEarned=" + totalEarned +
                ", totalItemsSold=" + totalItemsSold +
                '}';
    }
}
