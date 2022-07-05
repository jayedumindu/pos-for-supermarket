package com.ijse_pos.controller;

import com.ijse_pos.bo.BOFactory;
import com.ijse_pos.bo.custom.CustomerBO;
import com.ijse_pos.bo.custom.ItemBO;
import com.ijse_pos.bo.custom.OrderBO;
import com.ijse_pos.bo.custom.implBO.CustomerBOImpl;
import com.ijse_pos.bo.custom.implBO.OrderBOImpl;
import com.ijse_pos.dto.CustomOrderDTO;
import com.ijse_pos.dto.CustomerDTO;
import com.ijse_pos.dto.ItemDTO;
import com.ijse_pos.dto.OrderDTO;
import com.ijse_pos.view.tdm.CustomerTDM;
import com.ijse_pos.view.tdm.manageOrderTDM;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTabPane;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Tab;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

public class manageOrdersController {

    private final CustomerBO customerBO = (CustomerBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.CUSTOMER);
    private final OrderBO orderBO = (OrderBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.PURCHASE_ORDER);
    private final ItemBO itemBO = (ItemBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.ITEM);

    public ObservableList<CustomerTDM> cusList = FXCollections.observableArrayList();
    public ObservableList<String> idList = FXCollections.observableArrayList();
    public ObservableList<manageOrderTDM> itemList = FXCollections.observableArrayList();
    public ObservableList<manageOrderTDM> removedItemList = FXCollections.observableArrayList();

    public JFXTextField searchOrdersByCustomerIDTxt;

    public TableView<manageOrderTDM> itemsTbl;
    public TableColumn<manageOrderTDM, String> itemCodeClm;
    public TableColumn<manageOrderTDM, Integer> orderQtyClm;
    public TableColumn<manageOrderTDM, Integer> qtyOnHandClm;
    public TableColumn<manageOrderTDM, Double> discountClm;
    public TableColumn<manageOrderTDM, Double> totalPriceClm;
    public JFXTabPane mainTabPane;
    public Tab customerTab;
    public JFXComboBox<String> ordersCmb;
    public JFXButton updateOrderBtn;
    public JFXButton saveItemBtn;
    public JFXTextField discountTxt;
    public JFXTextField qtyTxt;
    public JFXButton removeItemBtn;
    public JFXButton cancelOrderBtn;

    public TableView<CustomerTDM> customerTbl;
    public TableColumn<CustomerTDM,String> cusIDClm;
    public TableColumn<CustomerTDM,String> titleClm;
    public TableColumn<CustomerTDM,String> nameClm;
    public TableColumn<CustomerTDM,String> addressClm;
    public TableColumn<CustomerTDM,String> cityClm;
    public TableColumn<CustomerTDM,String> provinceClm;
    public TableColumn<CustomerTDM,String> pCodeClm;

    public JFXButton removeCustomerBtn;
    public JFXButton updateCustomerBtn;
    public AnchorPane mainPane;

    public void initialize(){

        itemCodeClm.setCellValueFactory(new PropertyValueFactory<>("itemCode"));
        orderQtyClm.setCellValueFactory(new PropertyValueFactory<>("orderQty"));
        qtyOnHandClm.setCellValueFactory(new PropertyValueFactory<>("qtyOnHand"));
        discountClm.setCellValueFactory(new PropertyValueFactory<>("Discount"));
        totalPriceClm.setCellValueFactory(new PropertyValueFactory<>("totalPrice"));

        cusIDClm.setCellValueFactory(new PropertyValueFactory<>("CustomerID"));
        titleClm.setCellValueFactory(new PropertyValueFactory<>("CusTitle"));
        nameClm.setCellValueFactory(new PropertyValueFactory<>("CusName"));
        addressClm.setCellValueFactory(new PropertyValueFactory<>("CusAddress"));
        cityClm.setCellValueFactory(new PropertyValueFactory<>("City"));
        provinceClm.setCellValueFactory(new PropertyValueFactory<>("Province"));
        pCodeClm.setCellValueFactory(new PropertyValueFactory<>("PostalCode"));

        customerTbl.setItems(cusList);
        itemsTbl.setItems(itemList);
        ordersCmb.setItems(idList);

        mainTabPane.getSelectionModel().select(customerTab);

        try {
            loadAllCustomers();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

    }

    public void searchOrdersByCustomerID(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        if(!idList.isEmpty()){
            idList.clear();
        }
        if(!removedItemList.isEmpty()){
            removedItemList.clear();
        }
        String id = searchOrdersByCustomerIDTxt.getText();
        for (OrderDTO dto : customerBO.getAllOrdersByCustomerID(id)) {
            idList.add(dto.getOrderID());
        };
        if(idList.isEmpty()){
            new Alert(Alert.AlertType.WARNING,"no orders available for the id : " + id).show();
        }
    }

    public void navigateToDashboard(MouseEvent event) throws IOException {
        Stage stage = showStage();
        stage.setScene(new Scene(FXMLLoader.load(this.getClass().getResource("../view/dashboard.fxml"))));
    }

    public void removeCustomer(ActionEvent actionEvent) {
        CustomerTDM tdm = customerTbl.getSelectionModel().getSelectedItem();
        if(tdm!=null) {
            try {
                if (customerBO.deleteCustomer(tdm.getCustomerID())) {
                    new Alert(Alert.AlertType.INFORMATION, "customer deleted successfully").show();
                    cusList.remove(tdm);
                }
            } catch (SQLException | ClassNotFoundException e) {
                new Alert(Alert.AlertType.ERROR, "cannot delete customer \n error:" + e.getMessage()).show();
            }
        }else new Alert(Alert.AlertType.WARNING,"Please select a customer!").show();
    }

    public void updateCustomer(ActionEvent actionEvent) throws IOException {
        CustomerTDM tdm = customerTbl.getSelectionModel().getSelectedItem();
        if(tdm!=null) {
            Stage newStage = new Stage();
            newStage.setUserData(new CustomerDTO(tdm.getCustomerID(), tdm.getCusTitle(), tdm.getCusName(), tdm.getCusAddress(), tdm.getCity(), tdm.getProvince(), tdm.getPostalCode()));
            Scene subScene = new Scene(FXMLLoader.load(this.getClass().getResource("../view/updateCustomer.fxml")));
            newStage.setScene(subScene);
            newStage.setResizable(false);
            newStage.show();
            showStage().close();
        }else new Alert(Alert.AlertType.WARNING,"Please select a customer!").show();
    }

    public void cancelOrder(ActionEvent actionEvent) {
        String oid = ordersCmb.getValue();
        if(oid!=null) {
            ArrayList<CustomOrderDTO> orderDTOS = new ArrayList<>();
            for (manageOrderTDM tdm : itemList) {
                orderDTOS.add(new CustomOrderDTO(tdm.getItemCode(), tdm.getOrderQty()));
            }
            try {
                if (orderBO.cancelOrder(oid, orderDTOS)) {
                    new Alert(Alert.AlertType.INFORMATION, "order deleted successfully").show();
                    itemList.clear();
                    removedItemList.clear();
                    ordersCmb.getSelectionModel().clearSelection();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }else new Alert(Alert.AlertType.WARNING,"Select an order first!").show();
    }

    public void removeItem(ActionEvent actionEvent) {
        manageOrderTDM tdm = itemsTbl.getSelectionModel().getSelectedItem();
        if(tdm!=null) {
            itemList.remove(tdm);
            removedItemList.add(tdm);
        } else new Alert(Alert.AlertType.WARNING,"Please select an item").show();
    }

    public void saveItem(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        manageOrderTDM tdm =  itemsTbl.getSelectionModel().getSelectedItem();
        Integer qty = 0;
        Double dis = 0.0;
        if(tdm!=null) {
            try {
                qty = Integer.parseInt(qtyTxt.getText());
                dis = Double.parseDouble(discountTxt.getText());
            } catch (Throwable f) {
                if (f instanceof NumberFormatException) {
                    new Alert(Alert.AlertType.WARNING, "Please enter a valid amount").showAndWait();
                } else {
                    new Alert(Alert.AlertType.WARNING, f.getMessage()).showAndWait();
                }
                return;
            }
            Double maxDiscount = itemBO.find(tdm.getItemCode()).getMaxDiscount();
            if (dis > maxDiscount | dis < 0) {
                new Alert(Alert.AlertType.WARNING, "Discount has exceeded maximum or invalid!").showAndWait();
                return;
            }
            if (qty > tdm.getQtyOnHand() | qty < 0) {
                new Alert(Alert.AlertType.WARNING, "Quantity has exceeded maximum or uncountable!").showAndWait();
                return;
            }
            for (manageOrderTDM item : itemList) {
                if (item.getItemCode().equals(tdm.getItemCode())) {
                    if (qty < item.getOrderQty()) {
                        item.setQtyOnHand(item.getQtyOnHand() + (item.getOrderQty() - qty));
                    } else {
                        item.setQtyOnHand(item.getQtyOnHand() - (qty - item.getOrderQty()));
                    }
                    item.setOrderQty(qty);
                    // updating the total price
                    ItemDTO dto = itemBO.find(tdm.getItemCode());
                    // u-price
                    System.out.println(dto.getUnitPrice());
                    item.setTotalPrice((qty * dto.getUnitPrice()) - (dis * qty));
                    item.setDiscount(dis);
                    new Alert(Alert.AlertType.INFORMATION, "updated!").show();
                    break;
                }
            }
            itemsTbl.refresh();
        }else new Alert(Alert.AlertType.WARNING,"Please select an item!").show();
    }

    public void updateOrder(ActionEvent actionEvent) {
        String oId = ordersCmb.getValue();
        if(oId!=null) {
            ArrayList<CustomOrderDTO> orderDTOS = new ArrayList<>();
            ArrayList<CustomOrderDTO> removeOrderDTOS = new ArrayList<>();
            if (!itemList.isEmpty()) {
                for (manageOrderTDM tdm : itemList) {
                    orderDTOS.add(new CustomOrderDTO(tdm.getItemCode(), tdm.getOrderQty(), tdm.getQtyOnHand(), tdm.getDiscount(), tdm.getTotalPrice()));
                }
            }
            if (!removedItemList.isEmpty()) {
                for (manageOrderTDM rmvTDM : removedItemList) {
                    removeOrderDTOS.add(new CustomOrderDTO(rmvTDM.getItemCode(), rmvTDM.getOrderQty(), rmvTDM.getQtyOnHand(), rmvTDM.getDiscount(), rmvTDM.getTotalPrice()));
                }
            }
            try {
                if (orderBO.updateOrder(orderDTOS, removeOrderDTOS, oId)) {
                    new Alert(Alert.AlertType.INFORMATION, "updated!").show();
                    itemList.clear();
                    removedItemList.clear();
                    ordersCmb.getSelectionModel().clearSelection();
                } else {
                    new Alert(Alert.AlertType.ERROR, "not updated properly! try again....").show();
                }
            } catch (SQLException | ClassNotFoundException e) {
                new Alert(Alert.AlertType.WARNING, "interrupted\nerror : " + e.getMessage()).show();
            }
        }else new Alert(Alert.AlertType.WARNING,"Select an order first!").show();
    }

    public void loadItemsForOrder(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        if(!itemList.isEmpty()){
            itemList.clear();
        }
        if(!removedItemList.isEmpty()){
            removedItemList.clear();
        }
        for (CustomOrderDTO dto : orderBO.getOrderDetailsFiltered(ordersCmb.getValue())) {
            itemList.add(new manageOrderTDM(dto.getItemCode(),dto.getOrderQty(),dto.getQtyOnHand(),dto.getDiscount(),dto.getTotalPrice()));
        };
    }

    public void loadAllCustomers() throws SQLException, ClassNotFoundException {
        System.out.println("loading");
        if(!cusList.isEmpty()){
            cusList.clear();
        }
        for (CustomerDTO dto : customerBO.getAllCustomers()) {
            cusList.add(new CustomerTDM(dto.getCustomerID(), dto.getCusTitle(), dto.getCusName(), dto.getCusAddress(), dto.getCity(), dto.getProvince(), dto.getPostalCode()));
        }
    }

    public Stage showStage(){
        Stage stage = (Stage) mainPane.getScene().getWindow();
        return stage;
    }

}
