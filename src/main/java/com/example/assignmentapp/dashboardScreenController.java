package com.example.assignmentapp;

import javafx.animation.PauseTransition;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.SnapshotParameters;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Date;

import dbConnection.connectionClass;
import javafx.util.Duration;

public class dashboardScreenController implements Initializable {

    @FXML
    private TableColumn<productDetails, Double> totalPriceColumn;
    @FXML
    private TableColumn<productDetails, Double> priceColumn;

    @FXML
    private TableColumn<productDetails, String> productIDColumn;

    @FXML
    private TableColumn<productDetails, String> productNameColumn;

    @FXML
    private TableColumn<productDetails, Integer> quantityColumn;

    @FXML
    private TableColumn<productDetails, Integer> sttColumn;

    public TableColumn<productDetails, Double> getTotalPriceColumn() {
        return totalPriceColumn;
    }

    public TableColumn<productDetails, Double> getPriceColumn() {
        return priceColumn;
    }

    public TableColumn<productDetails, String> getProductIDColumn() {
        return productIDColumn;
    }

    public TableColumn<productDetails, String> getProductNameColumn() {
        return productNameColumn;
    }

    public TableColumn<productDetails, Integer> getQuantityColumn() {
        return quantityColumn;
    }

    public TableColumn<productDetails, Integer> getSttColumn() {
        return sttColumn;
    }

    @FXML
    private TableView<productDetails> tableView;

    @FXML
    private TextField txtBarcode;

    @FXML
    private TextField txtCash;


    @FXML
    private TableColumn<productDetails, String> unitColumn;

    @FXML
    private Button btnTest;

    @FXML
    private Button btnNewBill;

    private PauseTransition pause;
    private PauseTransition pausePhoneNumber;
    @FXML
    private ImageView imageView;
    @FXML
    private Label lblProductName;
    @FXML
    private Label lblSupplier;

    @FXML
    private Label lblTotalPrice;

    @FXML
    private Label lblUnit;

    @FXML
    private Label lblBarcode;

    @FXML
    private Label lblPrice;

    @FXML
    private Label lblMiniTotal;

    @FXML
    private Label lblDiscount;

    @FXML
    private Label lblExchangeMoney;

    @FXML
    private Label lblCustomerName;

    @FXML
    private Label lblCustomerPhoneNumber;

    @FXML
    private Label lblPoint;

    @FXML
    private Label lblRemainPoint;

    @FXML
    private Label lblTest;

    @FXML
    private TextField txtPhoneNumber;

    @FXML
    private TextField txtPoint;

    @FXML
    private TextField txtPointToCash;

    @FXML
    private TabPane pane;

    public Button getBtnNewBill() {
        return btnNewBill;
    }

    public TabPane getPane() {
        return pane;
    }

    private boolean isDiscount = false;

    public void setDiscount(boolean discount) {
        isDiscount = discount;
    }

    public TextField getTxtBarcode() {
        return txtBarcode;
    }

    public TextField getTxtCash() {
        return txtCash;
    }

    public TextField getTxtPhoneNumber() {
        return txtPhoneNumber;
    }

    public TextField getTxtPoint() {
        return txtPoint;
    }

    public void setBarcodeBill(String barcodeBill) {
        this.barcodeBill = barcodeBill;
    }

    public String barcodeBill = "";

    public void setTabDataMap(Map<Tab, ObservableList<productDetails>> tabDataMap) {
        this.tabDataMap = tabDataMap;
    }

    public Map<Tab, ObservableList<productDetails>> getTabDataMap() {
        return tabDataMap;
    }

    private Map<Tab, ObservableList<productDetails>> tabDataMap = new HashMap<>();
    private TableView<productDetails> currentTableView;

    public TableView<productDetails> getCurrentTableView() {
        return currentTableView;
    }

    public void setCurrentTableView(TableView<productDetails> currentTableView) {
        this.currentTableView = currentTableView;
    }
    private Map<Tab, tabData> tabDataValues = new HashMap<>();
    boolean customerHavingPointUsing = false;
    boolean customerExisted = false;

    public String phoneNumberForPoint = "";

    private Double conversionRate = .0;

    public Double getConversionRate() {
        return conversionRate;
    }

    public void setConversionRate(Double conversionRate) {
        this.conversionRate = conversionRate;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb){

        currentTableView = tableView;

        sttColumn.setCellValueFactory(new PropertyValueFactory<>("productID"));
        productIDColumn.setCellValueFactory(new PropertyValueFactory<>("productCode"));
        productNameColumn.setCellValueFactory(new PropertyValueFactory<>("productName"));
        unitColumn.setCellValueFactory(new PropertyValueFactory<>("unit"));
        quantityColumn.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        totalPriceColumn.setCellValueFactory(new PropertyValueFactory<>("totalPrice"));


        pane.getSelectionModel().selectedItemProperty().addListener((observable, oldTab, newTab) -> {
            if (newTab != null) {
                tabData data = tabDataValues.get(newTab);
                if (data == null) {
                    data = new tabData(0.0, 0.0, 0.0, 0.0);
                    tabDataValues.put(newTab, data);
                }
            }
        });

        pane.getSelectionModel().selectedItemProperty().addListener((observable, oldTab, newTab) -> {
            if (newTab != null) {
                currentTableView = (TableView<productDetails>) newTab.getContent();
                System.out.println("Current TableView for Tab: " + newTab.getText());
            }
        });


        pane.getSelectionModel().selectedItemProperty().addListener((observable, oldTab, newTab) -> {
            if (newTab != null) {
                currentTableView = new TableView<>(); // Initialize the currentTableView
                setupTableColumn(currentTableView);
                newTab.setContent(currentTableView);

                ObservableList<productDetails> selectedTabData = tabDataMap.get(newTab);
                if (selectedTabData != null) {
                    currentTableView.setItems(selectedTabData);
                } else {
                    currentTableView.setItems(FXCollections.observableArrayList());
                }

                tabData data = tabDataValues.get(newTab);
                if (data == null) {
                    data = new tabData(0.0, 0.0, 0.0, 0.0);
                    tabDataValues.put(newTab, data);
                }

                Double discountPrice = getDiscountPrice();
                loadMoney(data.getTotalPrice(), discountPrice);
            }
        });


        pause = new PauseTransition(Duration.millis(200));
        pause.setOnFinished(event -> {
                    searchProduct(txtBarcode.getText());
                    setBarcodeBill(txtBarcode.getText());
            try {
                loadProduct(txtBarcode.getText());
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            txtBarcode.clear();
            }
        );

        txtCash.textProperty().addListener((observable, oldvalue, newvalue) -> {
            loadChangeMoney();
        });


        txtBarcode.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.isEmpty()) {
                pause.playFromStart();
            }
        });

        txtPoint.textProperty().addListener((observable, oldvalue, newvalue)->{
            pointToCash();
        });

        txtPhoneNumber.setOnAction(event -> {
            try {
                String phoneNumber = txtPhoneNumber.getText();
                if(phoneNumber.length() != 10){
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("Lỗi");
                    alert.setHeaderText(null);
                    alert.setContentText("Độ dài số điện thoại không phù hợp");
                    alert.show();
                } else {
                    customerHandler(phoneNumber);
                }
            } catch (SQLException e){
                e.printStackTrace();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            txtPhoneNumber.clear();
        });

        //rounded image
        Rectangle clip = new Rectangle(
                imageView.getFitWidth(), imageView.getFitHeight()
        );
        clip.setArcWidth(20);
        clip.setArcHeight(20);
        imageView.setClip(clip);

        SnapshotParameters parameters = new SnapshotParameters();
        parameters.setFill(Color.TRANSPARENT);
        WritableImage image = imageView.snapshot(parameters, null);
        imageView.setClip(null);
        imageView.setImage(image);

        tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);


    }


    public void setupTableColumn(TableView<productDetails> tableView){
        TableColumn<productDetails, String> sttColumn = new TableColumn<>("STT");
        sttColumn.setPrefWidth(75.0);

        TableColumn<productDetails, String> productIDColumn = new TableColumn<>("Mã sản phẩm");
        productIDColumn.setPrefWidth(151.0);

        TableColumn<productDetails, String> productNameColumn = new TableColumn<>("Tên sản phẩm");
        productNameColumn.setPrefWidth(240.0);

        TableColumn<productDetails, String> unitColumn = new TableColumn<>("Đơn vị");
        unitColumn.setPrefWidth(115.0);

        TableColumn<productDetails, Integer> quantityColumn = new TableColumn<>("Số lượng");
        quantityColumn.setPrefWidth(103.0);

        TableColumn<productDetails, Double> priceColumn = new TableColumn<>("Đơn giá");
        priceColumn.setPrefWidth(149.0);




        TableColumn<productDetails, Double> totalPriceColumn = new TableColumn<>("Thành tiền");
        totalPriceColumn.setPrefWidth(226.0);

        sttColumn.setCellValueFactory(new PropertyValueFactory<>("productID"));
        productIDColumn.setCellValueFactory(new PropertyValueFactory<>("productCode"));
        productNameColumn.setCellValueFactory(new PropertyValueFactory<>("productName"));
        unitColumn.setCellValueFactory(new PropertyValueFactory<>("unit"));
        quantityColumn.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        totalPriceColumn.setCellValueFactory(new PropertyValueFactory<>("totalPrice"));

        tableView.getColumns().addAll(sttColumn, productIDColumn, productNameColumn, unitColumn, quantityColumn, priceColumn, totalPriceColumn);
    }


    public void showAddCustomer(String phoneNumber) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("customerAddMiniScreen.fxml"));
        Parent root = loader.load();

        CustomerAddMiniScreen customerAddMiniScreenController = loader.getController();
        customerAddMiniScreenController.setPhoneNumber(phoneNumber);

        customerAddMiniScreenController.setDbScreenController(this);

        Stage stage = new Stage();
        Scene scene = new Scene(root);
        stage.setTitle("Thêm khách hàng mới");
        stage.setScene(scene);
        stage.show();
    }

    public void customerHandler(String phoneNumer) throws SQLException, IOException {
        phoneNumberForPoint = phoneNumer;

        Connection connection = connectionClass.getConnection();
        customerHavingPointUsing = true;
        System.out.println("abc");
        String sqlQuery = "SELECT * FROM customer where customerPhoneNumber =?";
        PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery);
        preparedStatement.setString(1, phoneNumer);

        String customerName = null;
        String customerPhoneNumber = null;
        String customerPoint = null;
        String pointEarned = null;
        ResultSet resultSet = preparedStatement.executeQuery();
        if(resultSet.next()){
            customerExisted = true;

            customerPhoneNumber = resultSet.getString("customerPhoneNumber");
            customerName = resultSet.getString("customerName");
            customerPoint = resultSet.getString("points");
            lblCustomerName.setText(customerName);
            lblCustomerPhoneNumber.setText(customerPhoneNumber);
            lblPoint.setText(customerPoint);
            lblRemainPoint.setText(customerPoint);
            System.out.println("Customer Name: " + customerName + ", Phone Number: " + customerPhoneNumber + ", Points: " + customerPoint);
        } else {
            showAddCustomer(phoneNumer);
        }
    }

    public Double pointCalculate(Double price){
        return price * 0.1;
    }
    public String loadTotalPrice() {

        Double totalPrice =.0;

        for (productDetails product : currentTableView.getItems()){
            totalPrice +=(product.getTotalPrice());
        }
        return String.valueOf(totalPrice-getDiscount());
    }

    public String loadTotalQuantity(){
        int totalQuantity = 0;

        for(productDetails prodduct : currentTableView.getItems()){
            totalQuantity += prodduct.getQuantity();
        }

        return String.valueOf(totalQuantity);
    }

    public String calcTotalPrice(TableView<productDetails> currentTableView, Double discount){
        Double totalPrice =.0;

        for (productDetails product : currentTableView.getItems()){
            totalPrice +=(product.getTotalPrice());
        }
        return String.valueOf(totalPrice-discount);
    }

    public Double getDiscountPrice(){
        Double discountPrice = .0;
        return discountPrice;
    }

    public Double afterDiscountForF11(Double price){
        return price - (price*0.25);
    }

    public void dailyDiscount() {
        var selectedItem = currentTableView.getSelectionModel().getSelectedItem();
        if (selectedItem != null) {
            selectedItem.setTotalPrice(afterDiscountForF11(selectedItem.getTotalPrice()));
            currentTableView.refresh();


            Double discountPrice = getDiscountPrice();
            loadMoney(selectedItem.getTotalPrice(), discountPrice);

            lblMiniTotal.setText(loadTotalPrice());
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("No Selection");
            alert.setHeaderText(null);
            alert.setContentText("Please select an item to apply the discount.");
            alert.show();
        }
    }

    public void loadProduct(String barcode) throws SQLException {
        String productNameFromDatabase = "Sản phẩm";
        String productSupplierFromDatabase = "Nhà cung cấp";
        String productUnitFromDatabase = "Đơn vị";
        Double productPriceFromDatabase = 0.0;
        String productBarcodeFromDatabase ="00000000000";
        String productImage = "";
        Double discountPrice = getDiscountPrice();
        Connection connection = connectionClass.getConnection();
        if(connection!=null) {
            System.out.println("loadProduct database connected");

            String sqlQuery = "SELECT * FROM productdetails WHERE productBarCode = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery)) {
                preparedStatement.setString(1, barcode);

                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    if (resultSet.next()) {
                        productBarcodeFromDatabase = resultSet.getString("productBarCode");
                        productNameFromDatabase = resultSet.getString("productName");
                        productUnitFromDatabase = resultSet.getString("productUnit");
                        productPriceFromDatabase = resultSet.getDouble("productPrice");
                        productImage = resultSet.getString("productImage");
                    }
                }

            } catch (SQLException e){
                e.printStackTrace();
            } finally {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }

            lblProductName.setText(productNameFromDatabase);
            lblBarcode.setText(productBarcodeFromDatabase);
            lblUnit.setText(productUnitFromDatabase);
            loadMoney(productPriceFromDatabase, discountPrice);
            Image image = new Image(getClass().getResourceAsStream("/com/example/assignmentapp/Images/" + productImage));
            System.out.println("Image path: " + "/com/example/assignmentapp/Images/" + productImage);

            imageView.setImage(image);

        }
    }

    public void loadMoney(Double pdP, Double discountPrice){
        lblPrice.setText(String.valueOf(pdP));
        lblTotalPrice.setText(calcTotalPrice(currentTableView, discountPrice));
        lblMiniTotal.setText(String.valueOf(loadTotalPrice()));
        lblDiscount.setText(String.valueOf(discountPrice));
    }


    public void searchProduct(String barcode) {
        Tab selectedTab = pane.getSelectionModel().getSelectedItem();
        ObservableList<productDetails> productList = tabDataMap.get(selectedTab);

        if (productList == null) {
            productList = FXCollections.observableArrayList();
            tabDataMap.put(selectedTab, productList);
        }
        if(currentTableView!=null) {
            currentTableView.setItems(productList); //productList is still null til here
        }

        Connection connection = connectionClass.getConnection();

        if (connection != null) {
            System.out.println("Connected");

            String sqlQuery = "SELECT * FROM productdetails WHERE productBarCode = ?";

            try (PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery)) {
                preparedStatement.setString(1, barcode);

                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    if (resultSet.next()) {
                        String productCode = resultSet.getString("productBarCode");
                        String productName = resultSet.getString("productName");
                        String unit = resultSet.getString("productUnit");
                        double price = resultSet.getDouble("productPrice");

                        boolean productFound = false;
                        for (productDetails product : productList) {
                            if (product.getProductCode().equals(productCode)) { // Use productCode instead of barcode
                                int currentQuantity = product.getQuantity();
                                product.setQuantity(currentQuantity + 1);
                                product.setTotalPrice((currentQuantity + 1) * product.getPrice());
                                productFound = true;
                                break;
                            }
                        }
                        if (!productFound) {
                            productDetails newProduct = new productDetails(
                                    new SimpleIntegerProperty(productList.size() + 1),
                                    new SimpleStringProperty(productCode),
                                    new SimpleStringProperty(productName),
                                    new SimpleStringProperty(unit),
                                    new SimpleIntegerProperty(1),
                                    new SimpleDoubleProperty(price),
                                    new SimpleDoubleProperty(1 * afterDiscountPrice(price))
                            );
                            productList.add(newProduct);
                            System.out.println("added");
                            System.out.println(newProduct.getProductName());

                        }
                    }
                    currentTableView.refresh();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        } else {
            System.out.println("Connection failed.");
        }
    }

    public void pointToCash(){
        Double convertedPoint = .0;
        Double conversionRate = getConversionRate();
        convertedPoint = Double.parseDouble(txtPoint.getText());
        txtPointToCash.setText(String.valueOf(convertedPoint));
    }

    public void convertToCash(MouseEvent mouseEvent) throws SQLException {
        Double availablePoint = 0.0;
        Double inputPoint;

        // Check and print the value of txtPoint for debugging
        System.out.println("Input Points (txtPoint): " + txtPoint.getText());

        try {
            inputPoint = Double.parseDouble(txtPoint.getText());
        } catch (NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Invalid Input");
            alert.setHeaderText(null);
            alert.setContentText("Please enter a valid number for points.");
            alert.show();
            return;
        }

        // Retrieve points from database
        String sqlQuery = "SELECT points FROM customer WHERE customerPhoneNumber = ?;";
        try (Connection connection = connectionClass.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery)) {

            preparedStatement.setString(1, phoneNumberForPoint);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                availablePoint = resultSet.getDouble("points");
                System.out.println("Available Points: " + availablePoint);
            } else {
                System.out.println("No customer found with phone number: " + txtPhoneNumber.getText());
            }
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }

        // Compare points and show alert if insufficient
        if (inputPoint > availablePoint) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Lỗi");
            alert.setHeaderText(null);
            alert.setContentText("Không đủ điểm để quy đổi");
            alert.show();
        } else {
            Double newPoint = availablePoint - inputPoint;
            System.out.println("New Points Balance: " + newPoint);
            try {
                Connection connection = connectionClass.getConnection();

                String sqlUpdateCustomerQuery = "update customer set points = ? where customerPhoneNumber = ?;";
                PreparedStatement preparedStatement = connection.prepareStatement(sqlUpdateCustomerQuery);
                preparedStatement.setString(2, phoneNumberForPoint);
                preparedStatement.setString(1, String.valueOf(newPoint));

                preparedStatement.executeUpdate();
                System.out.println("Data updated successfully!");

                customerHandler(phoneNumberForPoint);

            } catch (SQLException sqlException){
                sqlException.printStackTrace();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }



    public Double getDiscount(){
        return  .0;
    }

    public double afterDiscountPrice(Double price){
        return price - (price*getDiscount());
    }


    public void deleteRowFromTable(ActionEvent e) {
        System.out.println("delete key pressed!");
        // Check that currentTableView has a selected item
        if (currentTableView != null && currentTableView.getSelectionModel().getSelectedItem() != null) {
            currentTableView.getItems().remove(currentTableView.getSelectionModel().getSelectedItem());
        }
    }

    public void removeAllTable(ActionEvent e){
        currentTableView.getItems().clear();
    }



    public void btnPurchaseClicked(ActionEvent e) throws SQLException {


// tạo một truy vấn load dữ liệu từ bảng transaction để lấy thông tin xem người dùng đã có dữ liệu chưa,
// nếu rồi thì lấy dữ liệu `pointEarned` từ bảng ra, cộng với `pointEarned` mới, rồi executeUpdate duy nhất cột đó với giá trị đã cộng khi nãy
        if(txtCash.getText().isEmpty()){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Lỗi");
            alert.setHeaderText("Bạn không thể để giá tiền trống");
            alert.setContentText("Vui lòng nhập giá tiền");
            alert.show();
        }


        Connection connection = connectionClass.getConnection();

        try {
            Calendar curDate = Calendar.getInstance();
            Date date = curDate.getTime();
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            String formattedDate = dateFormat.format(date); //DATE
            String barCodeForBill = barcodeBill;
            String quantityBill = loadTotalQuantity();
            System.out.println(quantityBill);

            Double currentPoint = .0;

            String totalBill = loadTotalPrice();

            //VỀ INSERT TỤI NÓ VÀO DATABASE ĐƯỢC RỒI, BIẾN ĐÃ CÓ
            String sqlQueryAddCustomer = "INSERT INTO transaction(customerPhoneNumber, totalAmount, pointEarned, pointUsed) value (?, ?, ?, ?);";
            String sqlGetData = "select * from customer where customerPhoneNumber = ?;";
            String sqlQueryUpdateCustomer = "update customer set points = ? where customerPhoneNumber = ?;";
            String sqlQuery = "INSERT INTO bill(productBarcode, billDate, billQuantity, billTotalAmount) value (?, ?, ?, ?)";

            PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery);
            preparedStatement.setString(1, barCodeForBill);
            preparedStatement.setString(2, formattedDate);
            preparedStatement.setString(3, String.valueOf(quantityBill));
            preparedStatement.setString(4, totalBill);


            PreparedStatement trasactionPreparedStatement = connection.prepareStatement(sqlQueryAddCustomer);
            trasactionPreparedStatement.setString(1, lblCustomerPhoneNumber.getText());
            trasactionPreparedStatement.setString(2, loadTotalPrice());
            trasactionPreparedStatement.setString(3, lblPoint.getText());
            trasactionPreparedStatement.setString(4, "0");


            PreparedStatement getDataPreparedStatement = connection.prepareStatement(sqlGetData);
            getDataPreparedStatement.setString(1, lblCustomerPhoneNumber.getText());
            try {
                ResultSet resultSet = getDataPreparedStatement.executeQuery();
                if(resultSet.next()){
                    currentPoint = resultSet.getDouble("points");
                    System.out.println(currentPoint);
                }
            } catch (SQLException sqlException)
            {
                sqlException.printStackTrace();
            }


            PreparedStatement customerUpdatePreparedStatement = connection.prepareStatement(sqlQueryUpdateCustomer);

            String newPoint = String.valueOf(currentPoint + Double.parseDouble(String.valueOf(pointCalculate(Double.valueOf(totalBill)))));

            customerUpdatePreparedStatement.setString(1, newPoint);
            customerUpdatePreparedStatement.setString(2, lblCustomerPhoneNumber.getText());
            if (customerHavingPointUsing){
                if(!customerExisted){
                    try {
                        trasactionPreparedStatement.executeUpdate();

                    } catch (Exception exception){
                        System.out.println("Cannot adding data to transaction table");
                    }
                } else {
                    try {
                        customerUpdatePreparedStatement.executeUpdate();
                        customerHandler(lblCustomerPhoneNumber.getText());
                    } catch (Exception exception){
                        System.out.println("Cannot updating data this table");
                    }
                }
            }

            preparedStatement.executeUpdate();


        } catch (Exception exception){
            exception.printStackTrace();
        }
        try {

            System.out.println("Data inserted");
        } catch (Exception exception){
            System.out.println("Giá tiền trống");
        }

    }

    public void loadChangeMoney(){
        System.out.println("Text changed");
        Double exchangeMoney = .0;
        Double receivedMoney = Double.valueOf(txtCash.getText());
        exchangeMoney = receivedMoney - (Double.parseDouble(loadTotalPrice()) - getDiscountPrice());
        lblExchangeMoney.setText(String.valueOf(exchangeMoney));
    }
    @FXML
    private void handleTestButtonAction(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("addingDataSample.fxml"));
        Scene scene = new Scene(loader.load());

//        AddingDataSample addingDataSampleScreen = loader.getController();
//        addingDataSampleScreen.setMainController(this);

        Stage stage = new Stage();
        stage.setTitle("Trình kiểm thử");
        stage.setScene(scene);
        stage.show();
    }

    public void menuSwitchRoleToAdmin (ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("switchToAdminLogin.fxml"));
        Parent root = loader.load();
        Stage stage = new Stage();
        stage.setTitle("Chuyển quyền");
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void openPointManager(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("pointManager.fxml"));
        Parent root = loader.load();
        Stage stage = new Stage();
        stage.setTitle("Quản lý điểm thưởng");
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

}

