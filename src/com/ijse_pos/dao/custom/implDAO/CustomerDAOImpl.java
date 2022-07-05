package com.ijse_pos.dao.custom.implDAO;

import com.ijse_pos.dao.custom.CustomerDAO;
import com.ijse_pos.dao.sqlUtil;
import com.ijse_pos.entity.Customer;
import com.ijse_pos.entity.Order;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class CustomerDAOImpl implements CustomerDAO {

    public ArrayList<Customer> getAll() throws SQLException, ClassNotFoundException {
        ResultSet rst = sqlUtil.executeQuery("SELECT * FROM Customer");
        ArrayList<Customer> allCustomers = new ArrayList<>();
        while (rst.next()) {
            allCustomers.add(new Customer(rst.getString(1), rst.getString(2), rst.getString(3),rst.getString(4),rst.getString(5),rst.getString(6),rst.getString(7)));
        }
        return allCustomers;
    }

    public boolean save(Customer entity) throws SQLException, ClassNotFoundException {
        return sqlUtil.executeUpdate("INSERT INTO Customer VALUES (?,?,?,?,?,?,?)", entity.getCustomerID(), entity.getCusTitle(), entity.getCusName(), entity.getCusAddress(), entity.getCity(), entity.getProvince(), entity.getPostalCode());
    }



    public boolean update(Customer entity) throws SQLException, ClassNotFoundException {
        return sqlUtil.executeUpdate("UPDATE Customer SET title=?, name=?, address=?, city=?, province=?, PostalCode=?  WHERE id=?", entity.getCusTitle(), entity.getCusName(), entity.getCusAddress(), entity.getCity(), entity.getProvince(), entity.getPostalCode(), entity.getCustomerID());
    }


    public Customer search(String id) throws SQLException, ClassNotFoundException {
        ResultSet rst = sqlUtil.executeQuery("SELECT * FROM Customer WHERE id=?", id);
        if (rst.next()) {
            return new Customer(rst.getString(1), rst.getString(2), rst.getString(3),rst.getString(4),rst.getString(5),rst.getString(6),rst.getString(7));
        }
        return null;
    }

    @Override
    public boolean exist(String id) throws SQLException, ClassNotFoundException {
        return sqlUtil.executeQuery("SELECT id FROM Customer WHERE id=?", id).next();
    }


    public boolean delete(String id) throws SQLException, ClassNotFoundException {
        return sqlUtil.executeUpdate("DELETE FROM Customer WHERE id=?", id);
    }


    public String generateNewID() throws SQLException, ClassNotFoundException {
        ResultSet rst = sqlUtil.executeQuery("SELECT id FROM Customer ORDER BY id DESC LIMIT 1;");
        if (rst.next()) {
            String id = rst.getString("id");
            int newCustomerId = Integer.parseInt(id.replace("C00-", "")) + 1;
            return String.format("C00-%03d", newCustomerId);
        } else {
            return "C00-001";
        }
    }


    @Override
    public ArrayList<Order> getAllOrders(String cid) throws SQLException, ClassNotFoundException {
        ResultSet rst = sqlUtil.executeQuery("SELECT * FROM Orders WHERE customerID = ?;",cid);
        ArrayList<Order> allOrders = new ArrayList<>();
        while (rst.next()){
            allOrders.add(new Order(rst.getString(1),rst.getString(2),rst.getString(3)));
        }
        return allOrders;
    }
}

