package com.ijse_pos.controller;

import com.ijse_pos.bo.BOFactory;
import com.ijse_pos.bo.custom.CustomerBO;
import com.ijse_pos.bo.custom.implBO.CustomerBOImpl;
import com.ijse_pos.dto.CustomerDTO;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;

public class updateCustomerController {

    private final CustomerBO customerBO = (CustomerBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.CUSTOMER);

    private final ObservableList<String> titles = FXCollections.observableArrayList("Mr","Mrs");

    public JFXTextField nameTxt;
    public JFXTextField cityTxt;
    public JFXComboBox<String> titleCmb;
    public JFXTextField provinceTxt;
    public JFXTextField pCodeTxt;
    public JFXTextArea addressTxt;
    public Label cidLbl;
    public AnchorPane mainPane;
    public JFXButton updateCustomerBtn;

    public void initialize() throws IOException {

        titleCmb.setItems(titles);

        Platform.runLater(this::populate);

        updateCustomerBtn.setDisable(true);

    }

    public void updateCustomer(ActionEvent actionEvent){

        String id = cidLbl.getText();
        String title = titleCmb.getValue();
        String name = nameTxt.getText();
        String address = addressTxt.getText();
        String city = cityTxt.getText();
        String province = provinceTxt.getText();
        String pCode = pCodeTxt.getText();

        try {
            if(customerBO.updateCustomer(new CustomerDTO(id,title,name,address,city,province,pCode))){
                new Alert(Alert.AlertType.INFORMATION,"customer updated successfully").showAndWait();
               setManageOrdersController();
            }
        } catch (SQLException | ClassNotFoundException | IOException e) {
            new Alert(Alert.AlertType.ERROR,"cannot update customer! \n error : " + e.getMessage()).show();
        }

    }

    public void populate(){
        Stage st = (Stage) mainPane.getScene().getWindow();
        if(st!=null) {
            CustomerDTO dto = (CustomerDTO) st.getUserData();
            cidLbl.setText(dto.getCustomerID());
            titleCmb.setValue(dto.getCusTitle());
            nameTxt.setText(dto.getCusName());
            cityTxt.setText(dto.getCity());
            addressTxt.setText(dto.getCusAddress());
            provinceTxt.setText(dto.getProvince());
            pCodeTxt.setText(dto.getPostalCode());
        }
        st.setOnCloseRequest((event)->{
            try {
                Stage stage =  new Stage();
                stage.setScene(new Scene(FXMLLoader.load(this.getClass().getResource("../view/manageOrders.fxml"))));
                stage.setTitle("Farmeez - Your family partner");
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    public void setManageOrdersController() throws IOException {
        Stage st = getStage();
        st.setScene(new Scene(FXMLLoader.load(this.getClass().getResource("../view/manageOrders.fxml"))));
        st.setTitle("Farmeez - Your family partner");
        st.show();
    }

    private Stage getStage(){
        Stage stage = (Stage) mainPane.getScene().getWindow();
        return stage;
    }

    public void validateCustomer(KeyEvent keyEvent) {

        boolean isValidated = true;

        if(!addressTxt.getText().matches(".{3,}")){
            isValidated = false;
        }
        if(!provinceTxt.getText().matches(".{3,15}")){
            isValidated = false;
        }
        if(!nameTxt.getText().matches("[A-Za-z ]+")){
            isValidated = false;
        }
        if(!pCodeTxt.getText().matches("^\\d{5}$")){
            isValidated = false;
        }
        if(!cityTxt.getText().matches(".{3,15}")){
            isValidated = false;
        }

        updateCustomerBtn.setDisable(!isValidated);
    }
}
