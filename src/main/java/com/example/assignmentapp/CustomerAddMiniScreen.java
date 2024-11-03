package com.example.assignmentapp;

import dbConnection.connectionClass;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class CustomerAddMiniScreen implements Initializable {
    @FXML
    private TextField txtCustomerName;
    private String phoneNumber;

    @FXML
    private AnchorPane addCustomerPane;

    private dashboardScreenController dbScreenController;

    public void setDbScreenController(dashboardScreenController dbScreenController) {
        this.dbScreenController = dbScreenController;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb){
        txtCustomerName.setOnAction(event -> {
            try {
                btnAddClicked(event);
            } catch (SQLException | IOException e) {
                throw new RuntimeException(e);
            }
        });
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void btnAddClicked(ActionEvent event) throws SQLException, IOException {
        Connection connection = connectionClass.getConnection();

        String sqLQuery = "INSERT INTO customer(customerPhoneNumber, customerName, points) value (?, ?,?)";
        PreparedStatement preparedStatement = connection.prepareStatement(sqLQuery);
        preparedStatement.setString(1, phoneNumber);
        preparedStatement.setString(2, txtCustomerName.getText());
        preparedStatement.setString(3, "0");

        preparedStatement.executeUpdate();

        if (dbScreenController != null) {
            dbScreenController.customerHandler(phoneNumber);
        }


        Stage currentStage = (Stage) addCustomerPane.getScene().getWindow();
        currentStage.close();

    }
}
