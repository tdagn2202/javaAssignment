package com.example.assignmentapp;

import dbConnection.connectionClass;
import javafx.collections.FXCollections;
import javafx.collections.ObservableArray;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class pointManagerController extends loginScreenController implements Initializable {
    @FXML
    private TableColumn<pointItem, String> colCustomerName;

    @FXML
    private TableColumn<pointItem, String> colPhoneNumber;

    @FXML
    private TableColumn<pointItem, String> colPoint;

    @FXML
    private TableView<pointItem> tableView;

    @Override
    public void initialize(URL url, ResourceBundle rb){
        colPhoneNumber.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
        colCustomerName.setCellValueFactory(new PropertyValueFactory<>("customerName"));
        colPoint.setCellValueFactory(new PropertyValueFactory<>("currentPoint"));
        tableView.setItems(pointManagerData());
        loadCustomerAndPoint();
    }

    private ObservableList<pointItem> pointManagerData(){
        return FXCollections.observableArrayList(
            new pointItem("0823974998", "Tran Hai Dang", "100")
        );
    }

    public void loadCustomerAndPoint(){
        ObservableList<pointItem> dataList = FXCollections.observableArrayList();
        try {
            String phoneNumber = null;
            String customerName = null;
            String currentPoint = null;
            Connection connection = connectionClass.getConnection();
            String sqlQuery = "SELECT * FROM customer";
            PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery);
            ResultSet resultSet = preparedStatement.executeQuery();

            while(resultSet.next()){
                phoneNumber = resultSet.getString("customerPhoneNumber");
                customerName = resultSet.getString("customerName");
                currentPoint = resultSet.getString("points");
                dataList.add(new pointItem(phoneNumber, customerName, currentPoint));
            }

            tableView.setItems(dataList);

        } catch (SQLException sqlException){
            sqlException.printStackTrace();
        }
    }

    public void addPointItem(String phoneNumer, String customerName, String currentPoint){
        tableView.getItems().add(new pointItem(phoneNumer, customerName, currentPoint));
    }

    public void removeCustomer(){
        String toRemovePhoneNumber = tableView.getSelectionModel().getSelectedItem().getPhoneNumber();

        try {
            Connection connection = connectionClass.getConnection();

            String sqlQuery = "DELETE FROM customer WHERE customerPhoneNumber = ?;";
            PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery);
            preparedStatement.setString(1, toRemovePhoneNumber);
            preparedStatement.executeUpdate();

            showAlert("Thông báo","Xóa khách hàng và điểm thành công!");
            loadCustomerAndPoint();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void showAlert(String title, String context){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(context);
        alert.show();
    }

    public void openModifPointManager() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("modifyConversion.fxml"));
        Parent parent = loader.load();
        Stage stage = new Stage();
        Scene scene = new Scene(parent);
        stage.setTitle("Thay đổi tỉ lệ điểm thưởng");
        stage.setScene(scene);
        stage.show();
    }


}
