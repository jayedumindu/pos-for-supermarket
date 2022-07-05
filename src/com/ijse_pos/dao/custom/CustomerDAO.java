package com.ijse_pos.dao.custom;

import com.ijse_pos.dao.CrudDAO;
import com.ijse_pos.entity.Customer;
import com.ijse_pos.entity.Order;

import java.sql.SQLException;
import java.util.ArrayList;

public interface CustomerDAO extends CrudDAO<Customer,String> {
    ArrayList<Order> getAllOrders(String cid) throws SQLException, ClassNotFoundException;
}
