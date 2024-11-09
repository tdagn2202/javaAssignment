package com.example.assignmentapp;

import dbConnection.connectionClass;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class StatisticalReportController {
    private Scene scene;
    private Stage stage;
    private Parent root;

    @FXML
    private TableView<MyData> TableView;
    @FXML
    private TableColumn<MyData, String> colType;
    @FXML
    private TableColumn<MyData, Integer> colCode;
    @FXML
    private TableColumn<MyData, String> colName;
    @FXML
    private TableColumn<MyData, Date> colDate;
    @FXML
    private TableColumn<MyData, Integer> colQuantity;
    @FXML
    private TableColumn<MyData, Double> colUPrice;
    @FXML
    private TableColumn<MyData, Double> colAmount;
    @FXML
    private ComboBox<String> comboBox_Category;
    @FXML
    private Label label_TotalProductsSold;
    @FXML
    private Label label_TotalIncome;
    @FXML
    private Label label_TotalProductCategories;
    @FXML
    private DatePicker DatePicker_From;
    @FXML
    private DatePicker DatePicker_To;
    @FXML
    private RadioButton Rabt_Day;
    @FXML
    private RadioButton Rabt_Month;
    @FXML
    private RadioButton Rabt_Year;


    String TotalDataQuery = "SELECT category.categoryName AS Category_Name, productdetails.productBarcode AS Product_ID, productdetails.productName AS Product_Name, bill.billDate AS Bill_Date, bill_details.detailQuantity AS Bill_Quantity, productdetails.productPrice AS Product_Price, bill_details.detailAmount AS Bill_Amount " +
            "FROM bill " +
            "JOIN bill_details ON bill.billId = bill_details.billId " +
            "JOIN productdetails ON bill_details.productBarcode = productdetails.productBarcode " +
            "JOIN category ON productdetails.categoryID = category.categoryId";


    public void initialize() {
        colType.setCellValueFactory(new PropertyValueFactory<>("ProductType"));
        colCode.setCellValueFactory(new PropertyValueFactory<>("ProductCode"));
        colName.setCellValueFactory(new PropertyValueFactory<>("ProductName"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("SaleDate"));
        colQuantity.setCellValueFactory(new PropertyValueFactory<>("Quantity"));
        colUPrice.setCellValueFactory(new PropertyValueFactory<>("UnitPrice"));
        colAmount.setCellValueFactory(new PropertyValueFactory<>("Amount"));

        fillComboBoxCategory();

        loadTableview(TotalDataQuery);

        updateLabels();
    }

    private void loadTableview(String query) {
        ObservableList<MyData> dataList = FXCollections.observableArrayList();
        try (Connection connection = connectionClass.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {

            while (resultSet.next()) {
                dataList.add(new MyData(
                        resultSet.getString("categoryName"),  // category.categoryName
                        resultSet.getString("productBarcode"),     // productdetails.productBarcode
                        resultSet.getString("productName"),   // productdetails.productName
                        resultSet.getDate("Bill_Date"),        // bill.billDate
                        resultSet.getInt("detailQuantity"),     // bill_details.detailQuantity
                        resultSet.getDouble("productPrice"),  // productdetails.productPrice
                        resultSet.getDouble("detailAmount")     // bill_details.detailAmount
                ));
            }
            TableView.setItems(dataList);
            updateLabels();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private ObservableList<String> fillComboBoxCategory() {
        ObservableList<String> categories = FXCollections.observableArrayList();

        String query = "SELECT Category_Name FROM Category";

        try (Connection connection = connectionClass.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {
            categories.add("ALL");
            while (resultSet.next()) {
                categories.add(resultSet.getString("categoryName"));
            }

            comboBox_Category.setItems(categories);
        } catch (SQLException e) {
            System.out.println("Query failed: " + e.getMessage());
        }
        return categories;
    }


    private boolean checkAmount = true;
    private boolean checkDate = true;
    private boolean checkCategory = true;

    public void setBt_SortByAmount(ActionEvent event) {
        if (checkAmount) {
            TableView.getItems().sort((d1, d2) -> Double.compare(d1.getAmount(), d2.getAmount()));
        } else {
            TableView.getItems().sort((d1, d2) -> Double.compare(d2.getAmount(), d1.getAmount()));
        }
        checkAmount = !checkAmount;
    }

    public void setBt_SortByDate(ActionEvent event) {
        if (checkDate) {
            TableView.getItems().sort((d1, d2) -> d1.getSaleDate().compareTo(d2.getSaleDate()));
        } else {
            TableView.getItems().sort((d1, d2) -> d2.getSaleDate().compareTo(d1.getSaleDate()));
        }
        checkDate = !checkDate;
    }

    public void setBt_SortByCategory(ActionEvent event) {
        if (checkCategory) {
            TableView.getItems().sort((d1, d2) -> d1.getProductType().compareTo(d2.getProductType()));
        } else {
            TableView.getItems().sort((d1, d2) -> d2.getProductType().compareTo(d1.getProductType()));
        }
        checkCategory = !checkCategory;
    }



    private void updateLabels() {
        label_TotalProductCategories.setText("Tổng các loại hàng đã bán: " + getTotalProductCategories());
        label_TotalProductsSold.setText("Tổng số lượng sản phẩm đã bán: " + getTotalProductsSold());
        label_TotalIncome.setText("Tổng doanh thu: $" + getTotalIncome());
    }
    private int getTotalProductsSold() {
        return TableView.getItems().stream().mapToInt(MyData::getQuantity).sum();
    }
    private double getTotalIncome() {
        return TableView.getItems().stream().mapToDouble(MyData::getAmount).sum();
    }
    private int getTotalProductCategories() {
        Set<String> uniqueCategories = new HashSet<>();
        TableView.getItems().forEach(data -> uniqueCategories.add(data.getProductType()));
        return uniqueCategories.size();
    }



    public void handleOkButton(ActionEvent event) {
        LocalDate startDate = null;
        LocalDate endDate = null;
        String selectedCategory = comboBox_Category.getValue();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        if (Rabt_Day.isSelected()) {
            startDate = LocalDate.now();
            endDate = LocalDate.now();
        } else if (Rabt_Month.isSelected()) {
            startDate = LocalDate.now().withDayOfMonth(1);
            endDate = LocalDate.now();
        } else if (Rabt_Year.isSelected()) {
            startDate = LocalDate.now().withDayOfYear(1);
            endDate = LocalDate.now();
        } else {
            startDate = DatePicker_From.getValue();
            endDate = DatePicker_To.getValue();
        }

        String query = "SELECT Category.Category_Name, Product.Product_ID, Product.Product_Name, Bill.Bill_Date, Bill.Bill_Quantity, Product.Product_Price, Bill.Bill_Amount " +
                "FROM Bill " +
                "JOIN Product ON Bill.Product_ID = Product.Product_ID " +
                "JOIN Category ON Product.Category_ID = Category.Category_ID " +
                "WHERE Bill.Bill_Date BETWEEN '" + startDate.format(formatter) + "' AND '" + endDate.format(formatter) + "'";

        if (selectedCategory != null && !selectedCategory.isEmpty() && !selectedCategory.equals("ALL")) {
            query += " AND Category.Category_Name = '" + selectedCategory + "'";
        }

        loadTableview(query);
    }


    public void switchToMain(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("MainScreen.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }


}
