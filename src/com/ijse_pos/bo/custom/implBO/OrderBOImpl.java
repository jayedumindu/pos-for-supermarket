package com.ijse_pos.bo.custom.implBO;

import com.ijse_pos.bo.custom.OrderBO;
import com.ijse_pos.dao.DAOFactory;
import com.ijse_pos.dao.custom.ItemDAO;
import com.ijse_pos.dao.custom.OrderDAO;
import com.ijse_pos.dao.custom.OrderDetailsDAO;
import com.ijse_pos.dao.custom.implDAO.ItemDAOImpl;
import com.ijse_pos.dao.custom.implDAO.OrderDAOImpl;
import com.ijse_pos.dao.custom.implDAO.OrderDetailsDAOImpl;
import com.ijse_pos.db.DBConnection;
import com.ijse_pos.dto.*;
import com.ijse_pos.entity.CustomOrder;
import com.ijse_pos.entity.Item;
import com.ijse_pos.entity.Order;
import com.ijse_pos.entity.OrderDetail;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

public class OrderBOImpl implements OrderBO {

    private final OrderDAO orderDAO = (OrderDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.ORDER);
    private final OrderDetailsDAO orderDetailsDAO = (OrderDetailsDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.ORDER_DETAILS);
    private final ItemDAO itemDAO = (ItemDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.ITEM);

    @Override
    public boolean purchaseOrder(OrderDTO orderDTO, ArrayList<OrderDetailDTO> orderDetailDTO) throws SQLException, ClassNotFoundException {
        Connection connection = DBConnection.getDbConnection().getConnection();
        connection.setAutoCommit(false);
        boolean isOrderAdded = orderDAO.save(new Order(orderDTO.getOrderID(),orderDTO.getCustomerID()));

        if(!isOrderAdded){
            connection.rollback();
            connection.setAutoCommit(true);
            return false;
        }
        for (OrderDetailDTO dto : orderDetailDTO) {
            boolean isOderDetailsSaved = orderDetailsDAO.save(new OrderDetail(dto.getOrderID(),dto.getItemCode(),dto.getOrderQty(),dto.getDiscount(),dto.getTotalPrice()));
            if (!isOderDetailsSaved){
                connection.rollback();
                connection.setAutoCommit(true);
                return false;
            }

            //Search & Update Item
            ItemDTO item = searchItem(dto.getItemCode());
            if(item==null){
                connection.rollback();
                connection.setAutoCommit(true);
                return false;
            }
            item.setQtyOnHand(item.getQtyOnHand() - dto.getOrderQty());

            //update item
            boolean update = itemDAO.update(new Item(item.getItemCode(), item.getDescription(),  item.getPackSize(), item.getUnitPrice(), item.getMaxDiscount(), item.getQtyOnHand()));

            if (!update) {
                connection.rollback();
                connection.setAutoCommit(true);
                return false;
            }

        }
        connection.commit();
        connection.setAutoCommit(true);
        return true;
    }

    @Override
    public CustomerDTO searchCustomer(String id) throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public ItemDTO searchItem(String code) throws SQLException, ClassNotFoundException {
        Connection connection = DBConnection.getDbConnection().getConnection();
        connection.setAutoCommit(true);
        Item ent = itemDAO.search(code);
        if(ent!=null){
            connection.setAutoCommit(false);
            return new ItemDTO(ent.getItemCode(), ent.getDescription(), ent.getPackSize(), ent.getUnitPrice(), ent.getMaxDiscount(), ent.getQtyOnHand());
        }
        connection.setAutoCommit(false);
       return null;
    }

    @Override
    public boolean checkItemIsAvailable(String code) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public boolean checkCustomerIsAvailable(String id) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public String generateNewOrderID() throws SQLException, ClassNotFoundException {
        return orderDAO.generateNewID();
    }

    @Override
    public ArrayList<CustomerDTO> getAllCustomers() throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public ArrayList<ItemDTO> getAllItems() throws SQLException, ClassNotFoundException {
        return null;
    }

    public ArrayList<CustomOrderDTO> getOrderDetailsFiltered(String oid) throws SQLException, ClassNotFoundException{
        ArrayList<CustomOrder> orderDetails = orderDetailsDAO.getAllOrdersFiltered(oid);
        ArrayList<CustomOrderDTO> dtOs = new ArrayList<>();
        for (CustomOrder cod : orderDetails) {
            dtOs.add(new CustomOrderDTO(cod.getItemCode(),cod.getOrderQty(),cod.getQtyOnHand(),cod.getDiscount(),cod.getTotalPrice()));
        }
        return dtOs;
    }

    public boolean cancelOrder(String oid, ArrayList<CustomOrderDTO> orderDTOS) throws SQLException, ClassNotFoundException {
        Connection connection = DBConnection.getDbConnection().getConnection();
        connection.setAutoCommit(false);
        boolean save;
        // updating qtyOnHand in items table
        for (CustomOrderDTO dto : orderDTOS) {
            Item item = itemDAO.search(dto.getItemCode());
            item.setQtyOnHand(item.getQtyOnHand()+dto.getOrderQty());
            save = itemDAO.update(item);
            if(!save){
                connection.rollback();
                connection.setAutoCommit(true);
                return false;
            }
        }
        save = orderDAO.delete(oid);
        if(!save){
            connection.rollback();
            connection.setAutoCommit(true);
            return false;
        }
        connection.commit();
        connection.setAutoCommit(true);
        return true;
    }

    @Override
    public boolean updateOrder(ArrayList<CustomOrderDTO> orderDTOS,ArrayList<CustomOrderDTO> removeOrderDTOS, String id) throws SQLException, ClassNotFoundException {
        Connection connection = DBConnection.getDbConnection().getConnection();
        connection.setAutoCommit(false);
        for (CustomOrderDTO dto : orderDTOS) {
            if(!orderDetailsDAO.update(new OrderDetail(id,dto.getItemCode(),dto.getOrderQty(),dto.getDiscount(),dto.getTotalPrice()))){
                connection.rollback();
                connection.setAutoCommit(true);
                return false;
            }
        }
        for (CustomOrderDTO dto : removeOrderDTOS) {
            if(!orderDetailsDAO.deleteCustomOrder(id,dto.getItemCode())){
                connection.rollback();
                connection.setAutoCommit(true);
                return false;
            }
        }
        connection.commit();
        connection.setAutoCommit(true);
        return true;
    }

    public ArrayList<ReportDTO> generateReport(int code) throws  SQLException, ClassNotFoundException{
        ArrayList<CustomOrder> rpt = orderDetailsDAO.generateReport(code);
        ArrayList<ReportDTO> report = new ArrayList<>();
        if(rpt!=null){
            for (CustomOrder customOrder : rpt) {
                report.add(new ReportDTO(customOrder.getItemCode(),customOrder.getTotalPrice(),customOrder.getOrderQty()));
            }
            return report;
        }
        return null;
    }

}
