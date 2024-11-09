package com.example.assignmentapp;

import DAO.acocunt;
import DAO.loginDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;

public class loginScreenController {


    @FXML
    private Button btnCancel;
    @FXML
    private AnchorPane scenePane;
    @FXML
    private Label lblInvalid;
    @FXML
    private TextField txtUsername;
    @FXML
    private TextField txtPassword;
    @FXML
    private Pane btnClose;
    @FXML
    private Pane btnMin;

    private double y = 0;
    private double x = 0;
    public void onCloseEvent(ActionEvent e){
        Stage stage = (Stage) scenePane.getScene().getWindow();
        stage.close();
    }

    private dashboardScreenController dashboardController;

    public void setDashboardController(dashboardScreenController dashboardController) {
        this.dashboardController = dashboardController;
    }


    public void moveToDashBoard(Stage currentStage) throws IOException {
        FXMLLoader dashboardLoader = new FXMLLoader(getClass().getResource("dashboardScreen.fxml"));
        Parent dashboardRoot = dashboardLoader.load();
        dashboardScreenController dbController = dashboardLoader.getController();
        Scene scene = new Scene(dashboardRoot);

        scene.addEventFilter(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>() {
            public void handle(KeyEvent keyEvent) {
                switch (keyEvent.getCode()){
                    case ESCAPE: {
                        System.out.println("Key Pressed: " + keyEvent.getCode());
                        keyEvent.consume(); // <-- stops passing the event to next node
                        break;
                    }

                    case F2: {
                        dbController.getTxtBarcode().requestFocus();
                        break;
                    }

                    case F5: {
                        dbController.getTxtCash().requestFocus();
                        break;
                    }

                    case F6: {
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("Move to banking page");
                        alert.setHeaderText("Chuyển tới trang thanh toán điện tử");
                        alert.show();
                        break;
                    }

                    case F3: {
                        dbController.getTxtPhoneNumber().requestFocus();
                        break;
                    }

                    case F4: {
                        dbController.getTxtPoint().requestFocus();
                        break;
                    }

                    case F12: {
                        ActionEvent e = new ActionEvent();
                        try {
                            dbController.btnPurchaseClicked(e);
                        } catch (SQLException ex) {
                            throw new RuntimeException(ex);
                        }
                        break;
                    }

                    case DELETE: {
                        ActionEvent e = new ActionEvent();
                        dbController.deleteRowFromTable(e);
                        break;
                    }

                    case F9: {
                        ActionEvent e = new ActionEvent();
                        dbController.removeAllTable(e);
                        break;
                    }

                    case F10: {
                        final Tab tab = new Tab("Phiếu tạm " + (dbController.getPane().getTabs().size() + 1));
                        TableView tableView = new TableView();
                        tableView.setPrefHeight(444.0);
                        tableView.setPrefWidth(1105.0);
                        tableView.setStyle("-fx-background-radius: 20;");
                        VBox.setVgrow(tableView, Priority.ALWAYS);

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

                        tableView.getColumns().addAll(sttColumn, productIDColumn, productNameColumn, unitColumn, quantityColumn, priceColumn, totalPriceColumn);

                        tab.setContent(tableView);

                        dbController.getPane().getTabs().add(tab);
                        dbController.getPane().getSelectionModel().select(tab);
                        break;
                    }

                    case F7: {
                        MouseEvent mouseEvent = new MouseEvent(
                                MouseEvent.MOUSE_CLICKED,
                                0, 0, 0, 0,
                                MouseButton.PRIMARY,
                                1,
                                false, false, false, false,
                                true, false, false, false,
                                false, false, null
                        );
                        try {
                            dbController.convertToCash(mouseEvent);
                        } catch (SQLException e) {
                            throw new RuntimeException(e);
                        }
                    }

                    case F11: {
                        dbController.dailyDiscount();

                    }


                }



            }
        });

        Stage stage = new Stage();
        stage.setTitle("Trình quản lý siêu thị");

        stage.setResizable(true);
        stage.setScene(scene);
        stage.show();

        currentStage.close();
    }


    public void addNewTab() throws IOException {
        FXMLLoader dashboardLoader = new FXMLLoader(getClass().getResource("dashboardScreen.fxml"));
        Parent dashboardRoot = dashboardLoader.load();
        dashboardScreenController dbController = dashboardLoader.getController();

        final Tab newTab = new Tab("Phiếu tạm " + (dbController.getPane().getTabs().size() + 1));
        TableView tableView = new TableView();
        tableView.setPrefHeight(444.0);
        tableView.setPrefWidth(1105.0);
        tableView.setStyle("-fx-background-radius: 20;");
        VBox.setVgrow(tableView, Priority.ALWAYS);

        dbController.setCurrentTableView(new TableView<>());
        setupTableColumn(tableView);
        newTab.setContent(dbController.getCurrentTableView());

        ObservableList<productDetails> newProductList = FXCollections.observableArrayList();
        dbController.getTabDataMap().put(newTab, newProductList);

        tableView.setItems(newProductList);


        dbController.getPane().getTabs().add(newTab);
        dbController.getPane().getSelectionModel().select(newTab);

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
    public void moveToStaffDashBoard(Stage currentStage) throws IOException {
        FXMLLoader dashboardLoader = new FXMLLoader(getClass().getResource("dashboardScreen_staff.fxml"));
        Parent dashboardRoot = dashboardLoader.load();
        dashboardScreenController dbController = dashboardLoader.getController();
        Scene scene = new Scene(dashboardRoot);

        scene.addEventFilter(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>() {
            public void handle(KeyEvent keyEvent) {
                switch (keyEvent.getCode()){
                    case ESCAPE: {
                        System.out.println("Key Pressed: " + keyEvent.getCode());
                        keyEvent.consume(); // <-- stops passing the event to next node
                        break;
                    }

                    case F2: {
                        dbController.getTxtBarcode().requestFocus();
                        break;
                    }

                    case F5: {
                        dbController.getTxtCash().requestFocus();
                        break;
                    }

                    case F6: {
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("Move to banking page");
                        alert.setHeaderText("Chuyển tới trang thanh toán điện tử");
                        alert.show();
                        break;
                    }

                    case F3: {
                        dbController.getTxtPhoneNumber().requestFocus();
                        break;
                    }

                    case F4: {
                        dbController.getTxtPoint().requestFocus();
                        break;
                    }

                    case F12: {
                        ActionEvent e = new ActionEvent();
                        try {
                            dbController.btnPurchaseClicked(e);
                        } catch (SQLException ex) {
                            throw new RuntimeException(ex);
                        }
                        break;
                    }

                    case DELETE: {
                        ActionEvent e = new ActionEvent();
                        dbController.deleteRowFromTable(e);
                        break;
                    }

                    case F9: {
                        ActionEvent e = new ActionEvent();
                        dbController.removeAllTable(e);
                        break;
                    }

                    case F10: {
                        final Tab tab = new Tab("Phiếu tạm " + (dbController.getPane().getTabs().size() + 1));
                        TableView tableView = new TableView();
                        tableView.setPrefHeight(444.0);
                        tableView.setPrefWidth(1105.0);
                        tableView.setStyle("-fx-background-radius: 20;");
                        VBox.setVgrow(tableView, Priority.ALWAYS);

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

                        tableView.getColumns().addAll(sttColumn, productIDColumn, productNameColumn, unitColumn, quantityColumn, priceColumn, totalPriceColumn);

                        tab.setContent(tableView);

                        dbController.getPane().getTabs().add(tab);
                        dbController.getPane().getSelectionModel().select(tab);
                        break;
                    }

                    case F7: {
                        MouseEvent mouseEvent = new MouseEvent(
                                MouseEvent.MOUSE_CLICKED,
                                0, 0, 0, 0,
                                MouseButton.PRIMARY,
                                1,
                                false, false, false, false,
                                true, false, false, false,
                                false, false, null
                        );
                        try {
                            dbController.convertToCash(mouseEvent);
                        } catch (SQLException e) {
                            throw new RuntimeException(e);
                        }
                    }

                    case F11: {
                        dbController.dailyDiscount();

                    }


                }



            }
        });

        Stage stage = new Stage();
        stage.setTitle("Trình quản lý siêu thị");

        stage.setResizable(true);
        stage.setScene(scene);
        stage.show();

        currentStage.close();
    }

    public void btnLoginClicked(ActionEvent e) throws IOException {
        String uid = txtUsername.getText();
        String pwd = txtPassword.getText();

        acocunt acnt = new acocunt(uid, pwd);
        boolean ckc = loginDAO.openConnection().login(acnt);
        System.out.println(ckc);

        if(ckc && !uid.equals("admin")){
            Stage currentStage = (Stage) scenePane.getScene().getWindow(); //scenePane that contains the btnLogin...
            moveToStaffDashBoard(currentStage);
        } else if (ckc && uid.equals("admin")) {
            Stage currentStage = (Stage) scenePane.getScene().getWindow(); //scenePane that contains the btnLogin...
            moveToDashBoard(currentStage);
        }
        else  {
            lblInvalid.setText("Vui lòng kiểm tra tài khoản và mật khẩu");
        }
        System.out.println("btnLogin clicked");
    }

    public void btnCloseClicked(MouseEvent e){
        javafx.application.Platform.exit();
    }

    public void btnMinClicked(MouseEvent e){
        Stage stage = (Stage) btnMin.getScene().getWindow();
        stage.setIconified(true);
    }
    @FXML
    public void screenDragged(MouseEvent e) {
        Stage stage = (Stage) scenePane.getScene().getWindow();
        stage.setY((e.getScreenY() - y));
        stage.setX((e.getScreenX() - x));
    }
    @FXML
    public void sceenPressed(MouseEvent e) {
        Stage stage = (Stage) scenePane.getScene().getWindow();

        // Calculate the offset between the mouse press position and the top-left corner of the window
        x = e.getScreenX() - stage.getX();
        y = e.getScreenY() - stage.getY();
    }
}
