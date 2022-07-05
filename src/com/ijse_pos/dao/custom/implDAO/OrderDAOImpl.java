package com.ijse_pos.dao.custom.implDAO;

import com.ijse_pos.dao.custom.OrderDAO;
import com.ijse_pos.dao.sqlUtil;
import com.ijse_pos.entity.Order;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class OrderDAOImpl implements OrderDAO{

    @Override
    public ArrayList<Order> getAll() throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public boolean save(Order entity) throws SQLException, ClassNotFoundException {
        return sqlUtil.executeUpdate("INSERT INTO `Orders` (oid, date, customerID) VALUES (?,NOW(),?)", entity.getOrderID(), entity.getCustomerID());
    }

    @Override
    public boolean update(Order dto) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public Order search(String s) throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public boolean exist(String oid) throws SQLException, ClassNotFoundException {
        return sqlUtil.executeQuery("SELECT oid FROM `Orders` WHERE oid=?", oid).next();
    }

    @Override
    public boolean delete(String oid) throws SQLException, ClassNotFoundException {
        return sqlUtil.executeUpdate("DELETE FROM `Orders` WHERE oid=?", oid);
    }

    @Override
    public String generateNewID() throws SQLException, ClassNotFoundException {
        ResultSet rst = sqlUtil.executeQuery("SELECT oid FROM `Orders` ORDER BY oid DESC LIMIT 1;");
        return rst.next() ? String.format("OID-%03d", (Integer.parseInt(rst.getString("oid").replace("OID-", "")) + 1)) : "OID-001";
    }
}

