package com.ijse_pos.dao.custom.implDAO;

import com.ijse_pos.dao.custom.ItemDAO;
import com.ijse_pos.dao.sqlUtil;
import com.ijse_pos.entity.Item;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ItemDAOImpl implements ItemDAO {

    @Override
    public ArrayList<Item> getAll() throws SQLException, ClassNotFoundException {
        ResultSet rst = sqlUtil.executeQuery("SELECT * FROM Item");
        ArrayList<Item> allItems = new ArrayList<>();
        while (rst.next()) {
            allItems.add(new Item(rst.getString(1), rst.getString(2), rst.getString(3), rst.getDouble(4),rst.getDouble(5),rst.getInt(6)));
        }
        return allItems;
    }

    @Override
    public boolean save(Item entity) throws SQLException, ClassNotFoundException {
        return sqlUtil.executeUpdate("INSERT INTO Item VALUES (?,?,?,?,?,?)", entity.getItemCode(), entity.getDescription(), entity.getPackSize(), entity.getUnitPrice(), entity.getMaxDiscount(), entity.getQtyOnHand());
    }

    @Override
    public boolean update(Item entity) throws SQLException, ClassNotFoundException {
        return sqlUtil.executeUpdate("UPDATE Item SET description=?, packSize=?, unitPrice=?, discount=?, qtyOnHand=? WHERE code=?", entity.getDescription(), entity.getPackSize(), entity.getUnitPrice(), entity.getMaxDiscount(), entity.getQtyOnHand(), entity.getItemCode());
    }

    @Override
    public Item search(String code) throws SQLException, ClassNotFoundException {
        ResultSet rst = sqlUtil.executeQuery("SELECT * FROM Item WHERE code=?", code);
        if (rst.next()) {
            return new Item(rst.getString(1), rst.getString(2), rst.getString(3), rst.getDouble(4),rst.getDouble(5),rst.getInt(6) );
        }
        return null;
    }

    @Override
    public boolean exist(String code) throws SQLException, ClassNotFoundException {
        return sqlUtil.executeQuery("SELECT code FROM Item WHERE code=?", code).next();
    }

    @Override
    public boolean delete(String code) throws SQLException, ClassNotFoundException {
        return sqlUtil.executeUpdate("DELETE FROM Item WHERE code=?", code);
    }

    @Override
    public String generateNewID() throws SQLException, ClassNotFoundException {
        ResultSet rst = sqlUtil.executeQuery("SELECT code FROM Item ORDER BY code DESC LIMIT 1;");
        if (rst.next()) {
            String id = rst.getString("code");
            int newItemId = Integer.parseInt(id.replace("I00-", "")) + 1;
            return String.format("I00-%03d", newItemId);
        } else {
            return "I00-001";
        }
    }

}
