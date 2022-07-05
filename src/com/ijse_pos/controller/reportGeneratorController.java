package com.ijse_pos.controller;

import com.ijse_pos.bo.BOFactory;
import com.ijse_pos.bo.custom.OrderBO;
import com.ijse_pos.bo.custom.implBO.OrderBOImpl;
import com.ijse_pos.dto.ReportDTO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

public class reportGeneratorController {

    private final OrderBO orderBO = (OrderBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.PURCHASE_ORDER);

    private static ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();
    private static ObservableList<XYChart.Series<String,Number>> barChartData = FXCollections.observableArrayList();

    public BarChart<String,Number> barChartForReports;
    public Label totItemsLbl;
    public Label salesLbl;
    public AnchorPane mainPane;
    public PieChart pieChartForItems;
    public ImageView dailyReport;
    public ImageView monthlyReport;
    public ImageView annualReport;
    public Label reportLbl;

    public void initialize(){
        pieChartForItems.setData(pieChartData);
        pieChartData.clear();
        barChartForReports.setData(barChartData);
        barChartData.clear();
        barChartForReports.setAnimated(false);
    }

    public void navigateToDashBoard(MouseEvent event) throws IOException {
        Stage stage = (Stage) mainPane.getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(this.getClass().getResource("../view/dashboard.fxml"))));
    }

    public void generateDailyReport(MouseEvent event) throws SQLException, ClassNotFoundException {
        reportLbl.setText(" - Daily");
        generateReport(0);
//        onMouseClickedAnimation((ImageView) event.getSource());
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

    public void generateMonthlyReport(MouseEvent event) throws SQLException, ClassNotFoundException {
        reportLbl.setText(" - Monthly");
        generateReport(1);
//        onMouseClickedAnimation((ImageView) event.getSource());

    }

    private void generateReport(int n) throws SQLException, ClassNotFoundException {

        int Items = 0;
        double Price = 0.0;

        ArrayList<ReportDTO> report =  orderBO.generateReport(n);
        if(!report.isEmpty()){
            pieChartData.clear();
            barChartData.clear();
            XYChart.Series sales = new XYChart.Series<String,Number>();
            sales.setName("sales");
            barChartData.add(sales);
            for (ReportDTO dto : report) {
                pieChartData.add(new PieChart.Data(dto.getItemCode(),dto.getTotalItemsSold()));
                barChartData.get(0).getData().add(new XYChart.Data<>(dto.getItemCode(), dto.getTotalEarned()));
                Items+=dto.getTotalItemsSold();
                Price+=dto.getTotalEarned();
            }
        }else {
            new Alert(Alert.AlertType.WARNING, "no records to generate a report!").showAndWait();
            reportLbl.setText("");
        }
        totItemsLbl.setText(String.valueOf(Items));
        salesLbl.setText(String.valueOf(Price));
    }

    public void generateAnnualReport(MouseEvent event) throws SQLException, ClassNotFoundException {
        reportLbl.setText(" - Annual");
        generateReport(2);
//        onMouseClickedAnimation((ImageView) event.getSource());

    }


}
