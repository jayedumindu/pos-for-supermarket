package com.ijse_pos.controller;

import com.ijse_pos.bo.BOFactory;
import com.ijse_pos.bo.custom.CustomerBO;
import com.ijse_pos.bo.custom.ItemBO;
import com.ijse_pos.bo.custom.OrderBO;
import com.ijse_pos.bo.custom.implBO.CustomerBOImpl;
import com.ijse_pos.bo.custom.implBO.ItemBOImpl;
import com.ijse_pos.bo.custom.implBO.OrderBOImpl;
import com.ijse_pos.dto.CustomerDTO;
import com.ijse_pos.dto.ItemDTO;
import com.ijse_pos.dto.OrderDTO;
import com.ijse_pos.dto.OrderDetailDTO;
import com.ijse_pos.view.tdm.CartTDM;
import com.ijse_pos.view.tdm.ChooseItemTDM;
import com.jfoenix.controls.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.regex.Pattern;

public class placeOrderController {

    private final CustomerBO customerBO = (CustomerBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.CUSTOMER);
    private final ItemBO itemBO = (ItemBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.ITEM);
    private final OrderBO purchaseOrderBO = (OrderBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.PURCHASE_ORDER);

    public JFXButton addCustomerBtn;


    private ObservableList<ChooseItemTDM> itemList = FXCollections.observableArrayList();
    private ObservableList<CartTDM> cartList = FXCollections.observableArrayList();
    private ObservableList<String> cusIds = FXCollections.observableArrayList();

    public Label maxDiscountLbl;
    public Label oidLbl;
    public Label totalLbl;
    public JFXTextField cNameTxt;
    public JFXTextField cityTxt;
    public JFXTextField pCodeTxt;
    public JFXTextField provinceTxt;
    public JFXTextArea addressTxt;
    public ImageView toSelectItemsTabBtn;
    public JFXComboBox<String> cTitleCmb;
    public TableView<ChooseItemTDM> itemTbl;
    public TableColumn<ChooseItemTDM,String> itemCodeClm;
    public TableColumn<ChooseItemTDM,String> descriptionClm;
    public TableColumn<ChooseItemTDM,String> packSizeClm;
    public TableColumn<ChooseItemTDM,Double> uPriceClm;
    public TableColumn<ChooseItemTDM,Integer> qOnHandClm;
    public JFXTextField qtyTxt;
    public JFXTextField discountTxt;
    public JFXButton addToCartBtn;
    public ImageView toCheckoutTabBtn;
    public TableView<CartTDM> cartTbl;
    public TableColumn<CartTDM,String> cartItemCodeClm;
    public TableColumn<CartTDM,String> totPackSizeClm;
    public TableColumn<CartTDM,Integer> totQtyClm;
    public TableColumn<CartTDM,Double> totPriceClm;
    public TableColumn<CartTDM,Double> discountClm;
    public JFXButton placeOrderBtn;
    public JFXButton removeFromCartBtn;
    public JFXComboBox<String> customerCmb;
    public ImageView navigateToHomePageBtn;
    public Tab selectItemsTab;
    public Tab checkoutTab;
    public AnchorPane mainPane;
    public JFXTabPane placeOrderTabPane;

    private final ObservableList<String> titles = FXCollections.observableArrayList("Mr","Mrs");

    private final Pattern pattern1 = Pattern.compile("^\\d{1,6}\\.\\d{0,2}$|^\\d{1,6}$");
    private final Pattern pattern2 = Pattern.compile("^[0-9]{1,5}$");

    public void initialize() throws SQLException, ClassNotFoundException {

        itemCodeClm.setCellValueFactory(new PropertyValueFactory<>("ItemCode"));
        descriptionClm.setCellValueFactory(new PropertyValueFactory<>("Description"));
        packSizeClm.setCellValueFactory(new PropertyValueFactory<>("PackSize"));
        uPriceClm.setCellValueFactory(new PropertyValueFactory<>("UnitPrice"));
        qOnHandClm.setCellValueFactory(new PropertyValueFactory<>("QtyOnHand"));

        cartItemCodeClm.setCellValueFactory(new PropertyValueFactory<>("ItemCode"));
        totPackSizeClm.setCellValueFactory(new PropertyValueFactory<>("totPackSize"));
        totPriceClm.setCellValueFactory(new PropertyValueFactory<>("totPrice"));
        totQtyClm.setCellValueFactory(new PropertyValueFactory<>("totQty"));
        discountClm.setCellValueFactory(new PropertyValueFactory<>("Discount"));

        cTitleCmb.setItems(titles);

        itemTbl.setItems(itemList);

        cartTbl.setItems(cartList);

        loadItemsToChoose();

        loadCustomerIds();

        addToCartBtn.setDisable(true);

        addCustomerBtn.setDisable(true);

        itemTbl.getSelectionModel().selectedItemProperty().addListener((obs,oldSelection,newSelection)->{
            maxDiscountLbl.setText(newSelection.getDiscount().toString());
            qtyTxt.clear();
            discountTxt.clear();
            if(!addToCartBtn.isDisable()){
                addToCartBtn.setDisable(true);
            }
        });

        oidLbl.setText(purchaseOrderBO.generateNewOrderID());

    }

    public void addCustomer(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {

        String id = customerBO.generateNewCustomerID();
        String title = cTitleCmb.getValue().toString();
        String name = cNameTxt.getText();
        String address = addressTxt.getText();
        String city = cityTxt.getText();
        String province = provinceTxt.getText();
        String pCode = pCodeTxt.getText();

        try {
            if(customerBO.saveCustomer(new CustomerDTO(id,title,name,address,city,province,pCode))){
                new Alert(Alert.AlertType.INFORMATION,"customer added successfully").show();
                loadCustomerIds();
                clearUI();
        }
        } catch (SQLException | ClassNotFoundException e) {
            new Alert(Alert.AlertType.ERROR,"cannot add customer! \n error : " + e.getMessage()).show();
        }

    }

    public void toSelect_ItemsTab(MouseEvent event) {
        placeOrderTabPane.getSelectionModel().selectNext();
    }

    public void onMouseEnteredAnimation(MouseEvent event) {
        if (event.getSource() instanceof ImageView) {
            ImageView icon = (ImageView) event.getSource();
            DropShadow glow = new DropShadow();
            glow.setColor(Color.DARKORANGE);
            glow.setWidth(20);
            glow.setHeight(20);
            glow.setRadius(20);
            icon.setEffect(glow);
        }
    }

    public void onMouseExitAnimation(MouseEvent event) {
        if (event.getSource() instanceof ImageView) {
            ImageView icon = (ImageView) event.getSource();
            icon.setEffect(null);
        }
    }

    public void addItemsToCart(ActionEvent actionEvent) {

        ChooseItemTDM tdm = itemTbl.getSelectionModel().getSelectedItem();
        Integer totItems = Integer.parseInt(qtyTxt.getText());
        Double discount = Double.parseDouble(discountTxt.getText());
        Double totPrice = (tdm.getUnitPrice()*totItems) - (discount*totItems);
        String totPack;

        String unit = tdm.getPackSize().replaceAll("[^A-Za-z]", "");
        String value = tdm.getPackSize().replaceAll("[^0-9]", "");

        totPack =  Integer.parseInt(value)*totItems + unit;


        // update if the same item exists in cart
        boolean found = false;
        for (CartTDM cartTDM : cartList ) {
            if(cartTDM.getItemCode().equals(tdm.getItemCode())){
                found = true;
                // validating discount
                if(!discount.equals(cartTDM.getDiscount())){
                    new Alert(Alert.AlertType.WARNING,"Discount for an item should be fixed!").show();
                    return;
                }
                // prev value
                String preValue = cartTDM.getTotPackSize().replaceAll("[^0-9]", "");
                cartTDM.setTotPackSize((Integer.parseInt(value)*totItems + Integer.parseInt(preValue)) + unit);
                cartTDM.setTotPrice(cartTDM.getTotPrice() + totPrice);
                cartTDM.setTotQty(cartTDM.getTotQty() + totItems);
                cartTDM.setDiscount(discount);
                cartTbl.refresh();
                // updating net total
                if(!totalLbl.getText().isEmpty()){
                    String updatedTotal = String.valueOf(Double.parseDouble(totalLbl.getText())+totPrice);
                    totalLbl.setText(updatedTotal);
                }else {
                    totalLbl.setText(String.valueOf(totPrice));
                }
                break;
            }
        }

        if (!found) {
            cartList.add(new CartTDM(tdm.getItemCode(), totPack, totItems, totPrice, discount));
            if(!totalLbl.getText().isEmpty()){
                String updatedTotal = String.valueOf(Double.parseDouble(totalLbl.getText())+totPrice);
                totalLbl.setText(updatedTotal);
            }else {
                totalLbl.setText(String.valueOf(totPrice));
            }
        }

        for (ChooseItemTDM tm : itemList) {
            if (tdm.getItemCode().equals(tm.getItemCode())){
                tm.setQtyOnHand(tdm.getQtyOnHand()-totItems);
                itemTbl.refresh();
                break;
            }
        }

    }

    public void toCheckoutTab(MouseEvent event) {
        placeOrderTabPane.getSelectionModel().selectNext();
    }

    public void placeOrder(ActionEvent actionEvent) {
        if(!cartList.isEmpty() & customerCmb.getSelectionModel().getSelectedIndex()!=-1) {
            String oId = oidLbl.getText();
            String cId = customerCmb.getValue();
            OrderDTO orderDTO = new OrderDTO(oId, cId);
            ArrayList<OrderDetailDTO> dtoLst = new ArrayList<>();
            for (CartTDM tdm : cartList) {
                dtoLst.add(new OrderDetailDTO(oId, tdm.getItemCode(), tdm.getTotQty(), tdm.getDiscount(), tdm.getTotPrice()));
            }
            try {
                if (purchaseOrderBO.purchaseOrder(orderDTO, dtoLst)) {
                    new Alert(Alert.AlertType.INFORMATION, "order saved successfully!").show();
                    oidLbl.setText(purchaseOrderBO.generateNewOrderID());
                    cartList.clear();
                    totalLbl.setText("");
                } else {
                    new Alert(Alert.AlertType.WARNING, "interrupted").show();
                }
            } catch (SQLException | ClassNotFoundException e) {
                new Alert(Alert.AlertType.ERROR, "order cannot be saved \n error : " + e.getMessage());
            }
        }else{
            new Alert(Alert.AlertType.WARNING, "please check whether\n-> Customer is selected\n-> If the cart is empty").show();
        }
    }

    public void removeFromCart(ActionEvent actionEvent) {
        CartTDM tdm = cartTbl.getSelectionModel().getSelectedItem();
        if(tdm!=null) {
            for (ChooseItemTDM itemTDM : itemList) {
                if (itemTDM.getItemCode().equals(tdm.getItemCode())) {
                    itemTDM.setQtyOnHand(itemTDM.getQtyOnHand() + tdm.getTotQty());
                    itemTbl.refresh();
                    break;
                }
            }
            // updating net total
            String updatedTotal = String.valueOf(Double.parseDouble(totalLbl.getText()) - tdm.getTotPrice());
            totalLbl.setText(updatedTotal);
            cartList.remove(tdm);
        }else  new Alert(Alert.AlertType.WARNING, "Please select an item to remove!").show();
    }

    public void navigateToHomePage(MouseEvent event) throws IOException {
        Stage stage = (Stage) mainPane.getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(this.getClass().getResource("../view/dashboard.fxml"))));
    }

    private void loadItemsToChoose() throws SQLException, ClassNotFoundException {
        if(!itemList.isEmpty()) {
            itemList.clear();
        }
        for (ItemDTO dto :itemBO.getAllItems()) {
            itemList.add(new ChooseItemTDM(dto.getItemCode(), dto.getDescription(), dto.getPackSize(), dto.getUnitPrice(), dto.getQtyOnHand(), dto.getMaxDiscount()));
        }
    }

    private void loadCustomerIds() throws SQLException, ClassNotFoundException {
        if(!customerCmb.getItems().isEmpty()){
            customerCmb.getItems().clear();
        }
        for (CustomerDTO dto : customerBO.getAllCustomers()) {
            cusIds.add(dto.getCustomerID());
        }
        customerCmb.setItems(cusIds);
    }

    public void validateATC(KeyEvent keyEvent) {

        boolean qtyValidated = true;
        boolean disValidated = true;

        if(pattern1.matcher(discountTxt.getText()).find()){
            double disToCheck = Double.parseDouble(discountTxt.getText());
            ChooseItemTDM tdm = itemTbl.getSelectionModel().getSelectedItem();
            if(tdm!=null){
                Double dis = tdm.getDiscount();
                if(disToCheck>dis | disToCheck<0){
                    disValidated = false;
                }
            }else disValidated = false;
        }else disValidated = false;

        if (pattern2.matcher(qtyTxt.getText()).find()){
            int qtyToCheck = Integer.parseInt(qtyTxt.getText());
            ChooseItemTDM tdm = itemTbl.getSelectionModel().getSelectedItem();
            Integer qty = tdm.getQtyOnHand();
            if(tdm!=null) {
                if (qtyToCheck > qty | qtyToCheck == 0 | qtyToCheck < 0) {
                    qtyValidated = false;
                }
            }else qtyValidated = false;
        }else qtyValidated = false;


        if (qtyValidated & disValidated){
            if(addToCartBtn.isDisable()){addToCartBtn.setDisable(false);}
        }else {
            if(!addToCartBtn.isDisable()){
                addToCartBtn.setDisable(true);
            }
        }
    }

    public void validateCustomer(){

        boolean isValidated = true;

        if(!addressTxt.getText().matches(".{3,}")){
            isValidated = false;
        }
        if(!provinceTxt.getText().matches(".{3,15}")){
            isValidated = false;
        }
        if(!cNameTxt.getText().matches("[A-Za-z ]+")){
            isValidated = false;
        }
        if(!pCodeTxt.getText().matches("^\\d{5}$")){
            isValidated = false;
        }
        if(!cityTxt.getText().matches(".{3,15}")){
            isValidated = false;
        }
        if(cTitleCmb.getSelectionModel().getSelectedIndex()==-1){
            isValidated = false;
        }

        addCustomerBtn.setDisable(!isValidated);

    }

    private void clearUI(){
        cityTxt.clear();
        pCodeTxt.clear();
        cNameTxt.clear();
        provinceTxt.clear();
        addressTxt.clear();
        cTitleCmb.getSelectionModel().clearSelection();
        addCustomerBtn.setDisable(true);
    }
}
