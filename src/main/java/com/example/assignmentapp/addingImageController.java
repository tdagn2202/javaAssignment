package com.example.assignmentapp;

import dbConnection.connectionClass;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;


public class addingImageController {

    @FXML
    private Button btnAddClicked;

    @FXML
    private TextField txtBarcode;

    @FXML
    private TextField txtCate;

    @FXML
    private TextField txtPrice;

    @FXML
    private TextField txtProductName;

    @FXML
    private TextField txtSupplier;

    @FXML
    private TextField txtUnit;

    @FXML
    private TextField txtImagePath;

    private Stage primaryStage;

    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    private String fileName;

    private dashboardScreenController dbController;

    public dashboardScreenController getDbController() {
        return dbController;
    }

    public void setDbController(dashboardScreenController dbController) {
        this.dbController = dbController;
    }

    public void moveImage(File file, String desiredDestination){
        File desiredFolder = new File(desiredDestination);

        Path desiredPath = desiredFolder.toPath().resolve(file.getName());

        try {
            Files.copy(file.toPath(), desiredPath, StandardCopyOption.REPLACE_EXISTING);
            System.out.println("File to moved to: " + desiredPath.toString());
        } catch (IOException e){
            System.out.println("Failed");
            e.printStackTrace();
        }
    }

    public void btnAddClicked(ActionEvent actionEvent){

//        FXMLLoader loader = new FXMLLoader(getClass().getResource("dashboardScreenController.fxml"));
//        dashboardScreenController dbController = loader.getController();

//        Stage stage = (Stage) dbController.getImageView().getScene().getWindow();

        String productBarcode = txtBarcode.getText();
        String productName = txtProductName.getText();
        String productUnit = txtUnit.getText();
        String productSupplier = txtSupplier.getText();
        String productPrice = txtPrice.getText();
        String categoryID = txtCate.getText();

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Chọn hình ảnh để thêm");
        File selectedFile = fileChooser.showOpenDialog(new Stage()); // Replace `new Stage()` with your main stage if you have one
        String targetPath = "D:\\CODE\\Java\\JavaFX\\assignmentApp\\src\\main\\resources\\com\\example\\assignmentapp\\Images\\";
        fileName = selectedFile.getName();
        txtImagePath.setText(fileName);
        if (selectedFile != null) {
            moveImage(selectedFile, targetPath);
            System.out.println("Selected file: " + selectedFile.getName());
        } else {
            System.out.println("No file selected");
        }

    }

    public void load(ActionEvent actionEvent){

        String productBarcode = txtBarcode.getText();
        String productName = txtProductName.getText();
        String productUnit = txtUnit.getText();
        String productSupplier = txtSupplier.getText();
        String productPrice = txtPrice.getText();
        String categoryID = txtCate.getText();
        try{
            Connection connection = connectionClass.getConnection();

            String sqlQuery = "INSERT INTO productdetails (productBarcode, productName, productUnit, productSupplier, productPrice, categoryID, productImage) VALUES\n" +
                    "                            (?, ?, ?, ?, ?, ?, ?)";

            PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery);
            preparedStatement.setString(1, productBarcode);
            preparedStatement.setString(2, productName);
            preparedStatement.setString(3, productUnit);
            preparedStatement.setString(4, productSupplier);
            preparedStatement.setString(5, productPrice);
            preparedStatement.setString(6, categoryID);
            preparedStatement.setString(7, fileName);

            preparedStatement.executeUpdate();
            dbController.loadProduct(null);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}


