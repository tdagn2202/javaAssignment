package com.example.assignmentapp;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;


public class main extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(main.class.getResource("loginScreen.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Đăng nhập");
        stage.setScene(scene);
        stage.initStyle(StageStyle.UNDECORATED);
        stage.show();

        stage.setOnCloseRequest(event -> {
            event.consume();
            onCloseEvent(stage);
        });

        FXMLLoader dashboardLoader = new FXMLLoader(getClass().getResource("dashboardScreen.fxml"));
        Parent dashboardRoot = dashboardLoader.load();
        dashboardScreenController dashboardController = dashboardLoader.getController();


        FXMLLoader loginLoader = new FXMLLoader(getClass().getResource("loginScreen.fxml"));
        Parent loginRoot = loginLoader.load();
        loginScreenController loginController = loginLoader.getController();
    }

    public void onCloseEvent(Stage stage){
        stage.close();
    }

    public static void main(String[] args) {

        launch();
    }
}