package com.ijse_pos.controller;

import javafx.animation.ScaleTransition;
import javafx.animation.TranslateTransition;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;



public class dashBoardController {

    public AnchorPane root;
    public Label topic;
    public Label subTopic;

    Stage primaryStage;
    Parent subRoot;
    Scene subScene;

    public void initialize() {
    }

    public void loadManageItemVIew(MouseEvent mouseEvent) throws IOException {
        fxmlLoader(viewList.STORE);
    }

    public void loadPlaceOrderView(MouseEvent mouseEvent) throws IOException {
        fxmlLoader(viewList.PLACE_ORDER);
    }

    private void fxmlLoader(viewList V) throws IOException {

        switch (V) {
            case PLACE_ORDER : subRoot = FXMLLoader.load(this.getClass().getResource("../view/placeOrders.fxml")); break;
            case STORE : subRoot = FXMLLoader.load(this.getClass().getResource("../view/manageItems.fxml")); break;
            case MANAGE_ORDER: subRoot = FXMLLoader.load(this.getClass().getResource("../view/manageOrders.fxml")); break;
            case REPORT: subRoot =  FXMLLoader.load(this.getClass().getResource("../view/reports.fxml")); break;
        }

        if (subRoot != null) {
            subScene = new Scene(subRoot);
            primaryStage = (Stage) root.getScene().getWindow();
            primaryStage.setScene(subScene);
            primaryStage.centerOnScreen();

            TranslateTransition tt = new TranslateTransition(Duration.millis(200), subScene.getRoot());
            tt.setFromY(-subScene.getHeight());
            tt.setToY(0);
            tt.play();
        }

    }

    public void onMouseExitAnimation(MouseEvent event) {
        if (event.getSource() instanceof ImageView) {
            ImageView icon = (ImageView) event.getSource();
            ScaleTransition scaleT = new ScaleTransition(Duration.millis(200), icon);
            scaleT.setToX(1);
            scaleT.setToY(1);
            scaleT.play();

            icon.setEffect(null);
            topic.setText("Welcome");
            subTopic.setText("Please select one of above main operations to proceed");
        }
    }

    public void onMouseEnteredAnimation(MouseEvent event) {
        if (event.getSource() instanceof ImageView) {
            ImageView icon = (ImageView) event.getSource();

            switch (icon.getId()) {
                case "placeOrders":
                    topic.setText("Place an Order");
                    subTopic.setText("Click to place an order");
                    break;
                case "manageItems":
                    topic.setText("Manage Items");
                    subTopic.setText("Click to add, edit, delete, search or view items");
                    break;
                case "reports":
                    topic.setText("Reports");
                    subTopic.setText("Click to view custom reports");
                    break;
                case "manageOrders":
                    topic.setText("Manage Orders");
                    subTopic.setText("Click to add, edit, delete, search or view orders");
                    break;

            }

            ScaleTransition scaleT = new ScaleTransition(Duration.millis(200), icon);
            scaleT.setToX(1.2);
            scaleT.setToY(1.2);
            scaleT.play();

            DropShadow glow = new DropShadow();
            glow.setColor(Color.CHOCOLATE);
            glow.setWidth(20);
            glow.setHeight(20);
            glow.setRadius(20);
            icon.setEffect(glow);

        }
    }

    public void loadReportView(MouseEvent event) throws IOException {
        fxmlLoader(viewList.REPORT);
    }

    public void loadManageOrderView(MouseEvent event) throws IOException {
        fxmlLoader(viewList.MANAGE_ORDER);
    }

    enum viewList{
        MANAGE_ORDER,STORE,REPORT,PLACE_ORDER;
    }
}