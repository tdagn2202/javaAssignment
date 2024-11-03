package com.example.assignmentapp;

import dbConnection.connectionClass;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class modifyConversionController implements Initializable {
    @FXML
    private Label lblCurrentPoint;

    @FXML
    private TextField txtNewConversion;

    private dashboardScreenController dbController = new dashboardScreenController();

    @Override
    public void initialize(URL url, ResourceBundle rb){
        try {
            loadConversionRate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public void modifyConversionRate(ActionEvent actionEvent) {
        try {
            double newRate = Double.parseDouble(txtNewConversion.getText());

            Connection connection = connectionClass.getConnection();

            String sqlQuery = "UPDATE settings set settingValue = ? where settingName = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery);
            preparedStatement.setString(1, String.valueOf(newRate));
            preparedStatement.setString(2, "conversionRate");

            preparedStatement.executeUpdate();
            pointManagerController pmController = new pointManagerController();
            pmController.showAlert("Thành công", "Đã thay đổi tỉ lệ điểm thưởng thành công!");
            loadConversionRate();

        } catch (NumberFormatException e) {
            System.out.println("Invalid input for conversion rate.");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void loadConversionRate() throws SQLException {
        Connection connection = connectionClass.getConnection();
        String currentConversionRate = "";
        PreparedStatement preparedStatement = connection.prepareStatement("select settingValue from settings where settingName = 'conversionRate'");
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()){
            currentConversionRate = resultSet.getString("settingValue");
        }
        System.out.println("Current: " + currentConversionRate);
        lblCurrentPoint.setText(currentConversionRate);
    }



}
