package com.ijse_pos.bo.custom;

import com.ijse_pos.bo.SuperBO;
import com.ijse_pos.dto.*;
import com.ijse_pos.entity.CustomOrder;

import java.sql.SQLException;
import java.util.ArrayList;

public interface OrderBO extends SuperBO {
    boolean purchaseOrder(OrderDTO ODto, ArrayList<OrderDetailDTO> ODDto) throws SQLException, ClassNotFoundException;

    CustomerDTO searchCustomer(String id) throws SQLException, ClassNotFoundException;

    ItemDTO searchItem(String code) throws SQLException, ClassNotFoundException;

    boolean checkItemIsAvailable(String code) throws SQLException, ClassNotFoundException;

    boolean checkCustomerIsAvailable(String id) throws SQLException, ClassNotFoundException;

    String generateNewOrderID() throws SQLException, ClassNotFoundException;

    ArrayList<CustomerDTO> getAllCustomers() throws SQLException, ClassNotFoundException;

    ArrayList<ItemDTO> getAllItems() throws SQLException, ClassNotFoundException;

    public ArrayList<CustomOrderDTO> getOrderDetailsFiltered(String oid) throws SQLException, ClassNotFoundException;

    public boolean cancelOrder(String oid, ArrayList<CustomOrderDTO> orderDTOS) throws SQLException, ClassNotFoundException;

    public boolean updateOrder(ArrayList<CustomOrderDTO> orderDTOS,ArrayList<CustomOrderDTO> removeOrderDTOS, String id) throws SQLException, ClassNotFoundException;

    public ArrayList<ReportDTO> generateReport(int code) throws  SQLException, ClassNotFoundException;
}
