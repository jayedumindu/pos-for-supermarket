package com.ijse_pos.controller;

import com.ijse_pos.bo.BOFactory;
import com.ijse_pos.bo.custom.ItemBO;
import com.ijse_pos.dto.ItemDTO;
import com.ijse_pos.view.tdm.ItemTDM;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTabPane;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;

public class manageItemsController {

    private final ItemBO itemBO = (ItemBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.ITEM);

    public Label itemCodeTxt;
    public Label updateAlertTxt;
    public Button updateAlertBtn;

    private ObservableList<ItemTDM> itemObList = FXCollections.observableArrayList();

    public JFXTabPane mainTabPane;
    public Tab addItemsTab;
    public Tab customizeTab;

    public JFXButton saveItemBtn;
    public JFXTextField qtyOnHandTxt;
    public JFXTextField itemDescriptionTxt;
    public JFXTextField packSizeTxt;
    public JFXTextField discountTxt;
    public JFXTextField uPriceTxt;
    public TableView<ItemTDM> itemsTbl;
    public TableColumn<ItemTDM,String> itemCodeClm;
    public TableColumn<ItemTDM,String> descriptionClm;
    public TableColumn<ItemTDM,String> packSizeClm;
    public TableColumn<ItemTDM,Double> uPriceClm;
    public TableColumn<ItemTDM,Double> discountClm;
    public TableColumn<ItemTDM,Integer> qtyOnHandClm;
    public JFXButton removeItemBtn;
    public JFXButton updateItemBtn;
    public AnchorPane mainPane;

    public void initialize(){
            itemCodeClm.setCellValueFactory(new PropertyValueFactory<>("ItemCode"));
            descriptionClm.setCellValueFactory(new PropertyValueFactory<>("Description"));
            packSizeClm.setCellValueFactory(new PropertyValueFactory<>("PackSize"));
            uPriceClm.setCellValueFactory(new PropertyValueFactory<>("UnitPrice"));
            discountClm.setCellValueFactory(new PropertyValueFactory<>("maxDiscount"));
            qtyOnHandClm.setCellValueFactory(new PropertyValueFactory<>("QtyOnHand"));

            itemsTbl.setItems(itemObList);

            saveItemBtn.setDisable(true);


        try {
            loadAllItems();
            itemCodeTxt.setText(itemBO.generateNewItemCode());
        } catch (SQLException | ClassNotFoundException  e) {
            e.printStackTrace();
        }
    }

    public void saveItem(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        String code = itemCodeTxt.getText();
        String des = itemDescriptionTxt.getText();
        String pack = packSizeTxt.getText();
        Double unitP = Double.parseDouble(uPriceTxt.getText());
        Double dis = Double.parseDouble(discountTxt.getText());
        Integer qty = Integer.parseInt(qtyOnHandTxt.getText());

        ItemTDM tdm = new ItemTDM(code,des,pack,unitP,dis,qty);

        if(itemBO.itemExist(code)){
            try{
                if(itemBO.updateItem(new ItemDTO(code,des,pack,unitP,dis,qty))){
                    new Alert(Alert.AlertType.INFORMATION,"item updated!").show();
                    // updating the obList
                    int index = 0;
                    for (ItemTDM tm : itemObList) {
                        if(tm.getItemCode().equals(tdm.getItemCode())){
                            itemObList.set(index,tdm);
                            break;
                        }
                        index++;
                    }
                    clearAddItemsTab();
                    mainTabPane.getSelectionModel().select(customizeTab);
                    itemCodeTxt.setText(itemBO.generateNewItemCode());
                    updateAlertTxt.setVisible(false);
                    updateAlertBtn.setVisible(false);
                }

            }
            catch (SQLException | ClassNotFoundException e) {
                new Alert(Alert.AlertType.ERROR, "item cannot be updated \n error:" + e.getMessage()).show();
                tdm = null;
            }
        }
        else {
            try {
                if (itemBO.saveItem(new ItemDTO(code, des, pack, unitP, dis, qty))) {
                    new Alert(Alert.AlertType.INFORMATION, "item saved").show();
                    clearAddItemsTab();
                    itemObList.add(tdm);
                    mainTabPane.getSelectionModel().select(customizeTab);
                    itemCodeTxt.setText(itemBO.generateNewItemCode());
                }
            } catch (SQLException | ClassNotFoundException e) {
                new Alert(Alert.AlertType.ERROR, "item cannot be saved \n error:" + e.getMessage()).show();
                tdm = null;
            }
        }
    }

    public void removeItem(ActionEvent actionEvent) {
        ItemTDM tdm = itemsTbl.getSelectionModel().getSelectedItem();
        if(tdm!=null) {
            String code = tdm.getItemCode();
            try {
                if (itemBO.deleteItem(code)) {
                    new Alert(Alert.AlertType.INFORMATION, "item deleted successfully!").show();
                    itemObList.remove(tdm);
                }
            } catch (SQLException | ClassNotFoundException e) {
                new Alert(Alert.AlertType.ERROR, "item cannot be deleted \n error:" + e.getMessage());
            }
        }else new Alert(Alert.AlertType.WARNING, "Please select an item to update!").show();
    }

    public void navigateToItemUpdateView(ActionEvent actionEvent) {

        ItemTDM tdm = itemsTbl.getSelectionModel().getSelectedItem();
        if(tdm!=null) {
            itemCodeTxt.setText(tdm.getItemCode());

            updateAlertTxt.setVisible(true);
            updateAlertBtn.setVisible(true);

            mainTabPane.getSelectionModel().select(addItemsTab);

            itemDescriptionTxt.setText(tdm.getDescription());
            packSizeTxt.setText(tdm.getPackSize());
            uPriceTxt.setText(tdm.getUnitPrice().toString());
            discountTxt.setText(tdm.getMaxDiscount().toString());
            qtyOnHandTxt.setText(tdm.getQtyOnHand().toString());
        }else new Alert(Alert.AlertType.WARNING, "Please select an item to update!").show();
    }

    public void navigateToDashboard(MouseEvent event) throws IOException {
        Stage stage = (Stage) mainPane.getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(this.getClass().getResource("../view/dashboard.fxml"))));
    }

    public void loadAllItems() throws SQLException, ClassNotFoundException {
        if(!itemObList.isEmpty()){
            itemObList.clear();
        }
        for (ItemDTO dto : itemBO.getAllItems()) {
            itemObList.add(new ItemTDM(dto.getItemCode(),dto.getDescription(),dto.getPackSize(),dto.getUnitPrice(),dto.getMaxDiscount(),dto.getQtyOnHand()));
        }
    }

    private void clearAddItemsTab(){
        itemDescriptionTxt.clear();
        packSizeTxt.clear();
        uPriceTxt.clear();
        discountTxt.clear();
        qtyOnHandTxt.clear();
        saveItemBtn.setDisable(true);
    }

    public void cancelUpdate(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        clearAddItemsTab();
        itemCodeTxt.setText(itemBO.generateNewItemCode());

        updateAlertTxt.setVisible(false);
        updateAlertBtn.setVisible(false);
    }

    public void validateItem(KeyEvent actionEvent) {

        boolean isValidated = true;

        if(!itemDescriptionTxt.getText().matches("[A-Za-z ]+")) {isValidated=false;}
        if(!discountTxt.getText().matches("^\\d{1,6}\\.\\d{1,2}$")) {isValidated=false;}
        if(!qtyOnHandTxt.getText().matches("^\\d{1,4}$")) {isValidated=false;}
        if(!packSizeTxt.getText().matches("\\d{1,6}(kg|l|ml)")) {isValidated=false;}
        if(!uPriceTxt.getText().matches("^\\d{1,6}\\.\\d{1,2}$")) {isValidated=false;}

        saveItemBtn.setDisable(!isValidated);

    }

}
