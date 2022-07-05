package com.ijse_pos.entity;

public class Customer {

    private String CustomerID;
    private String CusTitle;
    private String CusName;
    private String CusAddress;
    private String City;
    private String Province;
    private String PostalCode;

    public Customer(String customerID, String cusTitle, String cusName, String cusAddress, String city, String province, String postalCode) {
        CustomerID = customerID;
        CusTitle = cusTitle;
        CusName = cusName;
        CusAddress = cusAddress;
        City = city;
        Province = province;
        PostalCode = postalCode;
    }

    public String getCustomerID() {
        return CustomerID;
    }

    public void setCustomerID(String customerID) {
        CustomerID = customerID;
    }

    public String getCusTitle() {
        return CusTitle;
    }

    public void setCusTitle(String cusTitle) {
        CusTitle = cusTitle;
    }

    public String getCusName() {
        return CusName;
    }

    public void setCusName(String cusName) {
        CusName = cusName;
    }

    public String getCusAddress() {
        return CusAddress;
    }

    public void setCusAddress(String cusAddress) {
        CusAddress = cusAddress;
    }

    public String getCity() {
        return City;
    }

    public void setCity(String city) {
        City = city;
    }

    public String getProvince() {
        return Province;
    }

    public void setProvince(String province) {
        Province = province;
    }

    public String getPostalCode() {
        return PostalCode;
    }

    public void setPostalCode(String postalCode) {
        PostalCode = postalCode;
    }
}
