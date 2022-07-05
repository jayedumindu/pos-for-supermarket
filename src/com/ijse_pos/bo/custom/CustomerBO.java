package com.ijse_pos.bo.custom;

import com.ijse_pos.bo.SuperBO;
import com.ijse_pos.dto.CustomerDTO;
import com.ijse_pos.dto.OrderDTO;

import java.sql.SQLException;
import java.util.ArrayList;

public interface CustomerBO extends SuperBO {
    ArrayList<CustomerDTO> getAllCustomers() throws SQLException, ClassNotFoundException;

    boolean saveCustomer(CustomerDTO dto) throws SQLException, ClassNotFoundException;

    boolean updateCustomer(CustomerDTO dto) throws SQLException, ClassNotFoundException;

    boolean customerExist(String id) throws SQLException, ClassNotFoundException;

    boolean deleteCustomer(String id) throws SQLException, ClassNotFoundException;

    String generateNewCustomerID() throws SQLException, ClassNotFoundException;

    ArrayList<OrderDTO> getAllOrdersByCustomerID(String cid) throws SQLException, ClassNotFoundException;
}