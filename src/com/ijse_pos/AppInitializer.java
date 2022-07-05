package com.ijse_pos;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class AppInitializer extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage Dashboard) throws IOException {
        Dashboard.setScene(new Scene(FXMLLoader.load(getClass().getResource("view/dashboard.fxml"))));
        Dashboard.setResizable(false);
        Dashboard.setTitle("Farmeez - Your family partner");
        Dashboard.show();
    }
}
