//package com.example.assignmentapp;
//
//import javafx.collections.FXCollections;
//import javafx.collections.ObservableList;
//import javafx.event.ActionEvent;
//import javafx.fxml.FXML;
//import javafx.scene.control.Button;
//import javafx.scene.control.TableColumn;
//import javafx.scene.control.TableView;
//import javafx.scene.control.TextField;
//import javafx.scene.control.cell.PropertyValueFactory;
//import javafx.stage.Stage;
//
//public class AddingDataSample {
//    @FXML
//    private Button btnAdd;
//
//    @FXML
//    private TextField txtBarcode;
//
//    @FXML
//    private TextField txtPrice;
//
//    @FXML
//    private TextField txtProductName;
//
//    @FXML
//    private TextField txtQuantity;
//
//    @FXML
//    private TextField txtUnit;
//
//    @FXML
//    private TableColumn<productDetails, Double> priceColumn;
//
//    @FXML
//    private TableColumn<productDetails, String> productIDColumn;
//
//    @FXML
//    private TableColumn<productDetails, String> productNameColumn;
//
//    @FXML
//    private TableColumn<productDetails, Integer> quantityColumn;
//
//    @FXML
//    private TableColumn<productDetails, Integer> sttColumn;
//
//    @FXML
//    private TableView tableView;
//
//    @FXML
//    private TableColumn<productDetails, Double> totalPriceColumn;
//
//    @FXML
//    private TableColumn<productDetails, String> unitColumn;
//    private dashboardScreenController mainController;
//
//    public void setMainController(dashboardScreenController mainController){
//        this.mainController = mainController;
//    }
//    @FXML
//    public void btnAddClicked(ActionEvent e){
//
//        productDetails product = new productDetails("543359043", "My thanh long Caty", "Goi", 1, 13000);
//        mainController.addProduct(product);
//
////        tableView.setItems(data);
//    }
//}
