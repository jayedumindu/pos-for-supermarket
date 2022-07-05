package com.ijse_pos.dao.custom;

import com.ijse_pos.dao.CrudDAO;
import com.ijse_pos.entity.CustomOrder;
import com.ijse_pos.entity.OrderDetail;

import java.sql.SQLException;
import java.util.ArrayList;

public interface OrderDetailsDAO extends CrudDAO<OrderDetail,String> {
    public ArrayList<CustomOrder> getAllOrdersFiltered(String id) throws SQLException, ClassNotFoundException;
    public boolean deleteCustomOrder(String oid, String itemCode) throws SQLException, ClassNotFoundException;
    public ArrayList<CustomOrder> generateReport(int code) throws SQLException, ClassNotFoundException;
}
