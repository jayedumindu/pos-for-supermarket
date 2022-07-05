package com.ijse_pos.dao.custom.implDAO;

import com.ijse_pos.dao.custom.OrderDetailsDAO;
import com.ijse_pos.dao.sqlUtil;
import com.ijse_pos.entity.CustomOrder;
import com.ijse_pos.entity.OrderDetail;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class OrderDetailsDAOImpl implements OrderDetailsDAO {

    @Override
    public ArrayList<CustomOrder> getAllOrdersFiltered(String oid) throws SQLException, ClassNotFoundException {
        ResultSet rst = sqlUtil.executeQuery("select od.itemCode, od.orderQty, it.qtyOnHand, od.discount, od.totalPrice from orderDetails od inner join item it on od.itemCode=it.code where od.oid = ?", oid);
        ArrayList<CustomOrder> orderDetails = new ArrayList<>();
        while(rst.next()){
            orderDetails.add(new CustomOrder(rst.getString(1),rst.getInt(2),rst.getInt(3),rst.getDouble(4), rst.getDouble(5)));
        }
        return orderDetails;
    }

    @Override
    public ArrayList<OrderDetail> getAll() throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public boolean save(OrderDetail entity) throws SQLException, ClassNotFoundException {
        return sqlUtil.executeUpdate("INSERT INTO OrderDetails (oid, itemCode, orderQty, discount, totalPrice) VALUES (?,?,?,?,?)", entity.getOrderID(), entity.getItemCode(), entity.getOrderQty(), entity.getDiscount(), entity.getTotalPrice());
    }

    @Override
    public boolean update(OrderDetail entity) throws SQLException, ClassNotFoundException {
        return sqlUtil.executeUpdate("UPDATE OrderDetails SET orderQty=?, discount=?, totalPrice=? WHERE itemCode=? AND oid=?",entity.getOrderQty(), entity.getDiscount(), entity.getTotalPrice(),entity.getItemCode(), entity.getOrderID());
    }

    @Override
    public OrderDetail search(String s) throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public boolean exist(String s) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public boolean delete(String oid) throws SQLException, ClassNotFoundException {
        return sqlUtil.executeUpdate("DELETE FROM orderDetails WHERE oid=?", oid);
    }

    public boolean deleteCustomOrder(String oid, String itemCode) throws SQLException, ClassNotFoundException {
        return sqlUtil.executeUpdate("DELETE FROM orderDetails WHERE oid=? AND itemCode=?", oid, itemCode);
    }

    @Override
    public String generateNewID() throws SQLException, ClassNotFoundException {
        return null;
    }


    public ArrayList<CustomOrder> generateReport(int code) throws SQLException, ClassNotFoundException {
        ArrayList<CustomOrder> report = new ArrayList<>();
        if(code==0){
            ResultSet rst = sqlUtil.executeQuery("CALL DAILY_REPORT()");
            while(rst.next()){
                report.add(new CustomOrder(rst.getString(1),rst.getDouble(2),rst.getInt(3)));
            }
            return report;
        }
        else if(code==1){
            ResultSet rst = sqlUtil.executeQuery("CALL MONTHLY_REPORT()");
            while(rst.next()){
                report.add(new CustomOrder(rst.getString(1),rst.getDouble(2),rst.getInt(3)));
            }
            return report;
        }
        else if(code==2){
            ResultSet rst = sqlUtil.executeQuery("CALL ANNUAL_REPORT()");
            while(rst.next()){
                report.add(new CustomOrder(rst.getString(1),rst.getDouble(2),rst.getInt(3)));
            }
            return report;
        }
        return null;
    }

}

