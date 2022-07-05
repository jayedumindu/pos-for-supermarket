package com.ijse_pos.bo.custom.implBO;

import com.ijse_pos.bo.custom.CustomerBO;
import com.ijse_pos.dao.DAOFactory;
import com.ijse_pos.dao.custom.CustomerDAO;
import com.ijse_pos.dao.custom.implDAO.CustomerDAOImpl;
import com.ijse_pos.dto.CustomerDTO;
import com.ijse_pos.dto.OrderDTO;
import com.ijse_pos.entity.Customer;
import com.ijse_pos.entity.Order;

import java.sql.SQLException;
import java.util.ArrayList;

public class CustomerBOImpl implements CustomerBO {

    private final CustomerDAO customerDAO = (CustomerDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.CUSTOMER);

    @Override
    public ArrayList<CustomerDTO> getAllCustomers() throws SQLException, ClassNotFoundException {
        ArrayList<Customer> all = customerDAO.getAll();
        ArrayList<CustomerDTO> allCustomers= new ArrayList<>();
        for (Customer customer : all) {
            allCustomers.add(new CustomerDTO(customer.getCustomerID(),customer.getCusTitle(),customer.getCusName(),customer.getCusAddress(),customer.getCity(),customer.getProvince(),customer.getPostalCode()));
        }
        return allCustomers;
    }

    @Override
    public boolean saveCustomer(CustomerDTO dto) throws SQLException, ClassNotFoundException {
        return customerDAO.save(new Customer(dto.getCustomerID(),dto.getCusTitle(),dto.getCusName(),dto.getCusAddress(),dto.getCity(),dto.getProvince(),dto.getPostalCode()));
    }

    @Override
    public boolean updateCustomer(CustomerDTO dto) throws SQLException, ClassNotFoundException {
        return customerDAO.update(new Customer(dto.getCustomerID(),dto.getCusTitle(),dto.getCusName(),dto.getCusAddress(),dto.getCity(),dto.getProvince(),dto.getPostalCode()));
    }

    @Override
    public boolean customerExist(String id) throws SQLException, ClassNotFoundException {
        return customerDAO.exist(id);
    }

    @Override
    public boolean deleteCustomer(String id) throws SQLException, ClassNotFoundException {
        return customerDAO.delete(id);
    }

    @Override
    public String generateNewCustomerID() throws SQLException, ClassNotFoundException {
        return customerDAO.generateNewID();
    }

    @Override
    public ArrayList<OrderDTO> getAllOrdersByCustomerID(String cid) throws SQLException, ClassNotFoundException {
        ArrayList<Order> orders = customerDAO.getAllOrders(cid);
        ArrayList<OrderDTO> orderDTOs = new ArrayList<>();
        for (Order o : orders) {
            orderDTOs.add(new OrderDTO(o.getOrderID(),o.getCustomerID()));
        }
        return orderDTOs;
    }

}
