package com.ijse_pos.dao;

import com.ijse_pos.dao.custom.implDAO.CustomerDAOImpl;
import com.ijse_pos.dao.custom.implDAO.ItemDAOImpl;
import com.ijse_pos.dao.custom.implDAO.OrderDAOImpl;
import com.ijse_pos.dao.custom.implDAO.OrderDetailsDAOImpl;

public class DAOFactory {
    private static DAOFactory daoFactory;

    private DAOFactory() {
    }

    //singleton
    public static DAOFactory getDaoFactory() {
        if (daoFactory == null) {
            daoFactory = new DAOFactory();
        }
        return daoFactory;
    }

    //public final static integer values
    public enum DAOTypes {
        CUSTOMER, ITEM, ORDER, ORDER_DETAILS
    }

    //method for hide the object creation logic and return what client wants
    public SuperDAO getDAO(DAOTypes types) {
        switch (types) {
            case CUSTOMER:
                return new CustomerDAOImpl(); //SuperDAO superDAO=new CustomerDAOImpl();
            case ITEM:
                return new ItemDAOImpl(); //SuperDAO superDAO=new ItemDAOImpl();
            case ORDER:
                return  new OrderDAOImpl(); //SuperDAO superDAO = new OrderDAOImpl();
            case ORDER_DETAILS:
                return new OrderDetailsDAOImpl(); //SuperDAO superDAO = new OrderDetailsDAOImpl();
            default:
                return null;
        }
    }


}
