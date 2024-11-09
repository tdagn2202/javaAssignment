package com.example.assignmentapp;

import dbConnection.connectionClass;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;


public class QuanLyKhoHangController implements Initializable {
    private Stage stage;
    private Scene scene;
    private Parent root;
    private Image image;
    private Connection connect;
    private Statement statement;
    private ResultSet result;
    private Alert alert;
    private PreparedStatement prepare;

    @FXML
    private AnchorPane khohangScene;

    @FXML
    private Button khohang_add;

    @FXML
    private Button khohang_clear;

    @FXML
    private Button khohang_delete;

    @FXML
    private ImageView khohang_imageview;

    @FXML
    private Button khohang_import;

    @FXML
    private TextField khohang_price;

    @FXML
    private TextField khohang_productID;

    @FXML
    private TextField khohang_productName;

    @FXML
    private TextField khohang_stock;

    @FXML
    private  TableView<warehouseItems> khohang_table;

    @FXML
    private Button khohang_update;

    @FXML
    private TableColumn<warehouseItems, Integer> table_categoryid;

    @FXML
    private TableColumn<warehouseItems, Double> table_price;

    @FXML
    private TableColumn<warehouseItems, String> table_productid;

    @FXML
    private TableColumn<warehouseItems, String> table_productname;

    @FXML
    private TableColumn<warehouseItems, Integer> table_quantityin;

    @FXML
    private TableColumn<warehouseItems, Integer> table_stock;

    @FXML
    private TableColumn<warehouseItems, String> table_unit;

    @FXML
    private ComboBox<String> category_combobox;

    @FXML
    private ComboBox<String> unit_combobox;

    @FXML
    private TextField txtSupplier;

    //Chuyen doi trang
    public void switchToMainChinh(ActionEvent event) throws IOException {
//        root = FXMLLoader.load(getClass().getResource("dashboardScreen.fxml"));
        stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
//        scene = new Scene(root);
//        stage.setScene(scene);
        stage.close();
    }

    //Lay du lieu database vao tableview
    public void getProducts() {
        ObservableList<warehouseItems> products = FXCollections.observableArrayList();
        Connection conn = connectionClass.getConnection();
        String query = "SELECT p.productBarcode AS ProductID, " +
                "p.productName AS ProductName, " +
                "p.categoryID AS CategoryID, " +
                "p.productUnit AS Unit, " +
                "w.stock AS Stock, " +
                "p.productPrice AS Price, " +
                "p.productImage AS ImagePath, " +
                "w.quantityIn AS QuantityIn, " +
                "p.productSupplier AS Supplier " +  // Add this line for productSupplier
                "FROM warehouse w " +
                "JOIN productdetails p ON w.productBarcode = p.productBarcode;";

        try {
            Statement statement = conn.createStatement();
            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                products.add(new warehouseItems(
                        resultSet.getString("ProductID"),
                        resultSet.getString("ProductName"),
                        resultSet.getInt("CategoryID"),
                        resultSet.getString("Unit"),
                        resultSet.getInt("Stock"),
                        resultSet.getDouble("Price"),
                        resultSet.getInt("QuantityIn"),
                        resultSet.getString("ImagePath"),
                        resultSet.getString("Supplier") // Set the Supplier field
                ));
            }
            khohang_table.setItems(products);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    //Lay du lieu CategoryID vao combobox
    private void loadCategoryIDs() {
        ObservableList<String> categories = FXCollections.observableArrayList();
        Connection conn = connectionClass.getConnection();
        String query = "SELECT categoryId FROM Category";

        try {
            Statement statement = conn.createStatement();
            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                categories.add(resultSet.getString("CategoryID"));
            }
            category_combobox.setItems(categories);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    //Danh sach chon Unit add vao combobox
    private  String[] unitList = {"kg (kilogram)", "g (gram)", "l (lít)", "ml (milliliter)", "chai", "hộp", "lon", "gói", "vỉ", "cây", "bịch", "cái", "thùng", "túi"};
    public void khohangUnitList(){
        List<String> unitL = new ArrayList<>();
        for (String data : unitList) {
            unitL.add(data);
        }
        ObservableList listData = FXCollections.observableArrayList(unitL);
        unit_combobox.setItems(listData);
    }
    //Chuc nang Add
    public void khohangAdd() {
        if (khohang_productID.getText().isEmpty()
                || khohang_productName.getText().isEmpty()
                || category_combobox.getSelectionModel().getSelectedItem() == null
                || unit_combobox.getSelectionModel().getSelectedItem() == null
                || khohang_stock.getText().isEmpty()
                || khohang_price.getText().isEmpty()
                || data.path == null
                || txtSupplier.getText().isEmpty()) {  // Check if Supplier is entered

            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText("Please enter all blank fields.");
            alert.showAndWait();
        } else {
            String formattedPath = data.path.replace("\\\\", "\\"); // Convert path format

            // Check for ProductID
            String checkProductID = "SELECT productBarcode FROM productdetails WHERE productBarcode='" + khohang_productID.getText() + "'";

            connect = connectionClass.getConnection();
            try {
                statement = connect.createStatement();
                result = statement.executeQuery(checkProductID);
                if (result.next()) {
                    alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error Message");
                    alert.setHeaderText(null);
                    alert.setContentText(khohang_productID.getText() + " already exists.");
                    alert.showAndWait();
                } else {
                    // Insert product into productdetails
                    String insertDataProducts = "INSERT INTO productdetails "
                            + "(productBarcode, productName, categoryID, productUnit, productPrice, productImage, productSupplier) VALUES (?, ?, ?, ?, ?, ?, ?)";  // Include productSupplier

                    prepare = connect.prepareStatement(insertDataProducts, Statement.RETURN_GENERATED_KEYS);
                    prepare.setString(1, khohang_productID.getText());
                    prepare.setString(2, khohang_productName.getText());
                    prepare.setInt(3, Integer.parseInt((String) category_combobox.getSelectionModel().getSelectedItem()));
                    prepare.setString(4, (String) unit_combobox.getSelectionModel().getSelectedItem());
                    prepare.setDouble(5, Double.parseDouble(khohang_price.getText()));
                    prepare.setString(6, formattedPath);
                    prepare.setString(7, txtSupplier.getText());  // Set the supplier field
                    prepare.executeUpdate(); // Execute the insert for productdetails

                    // Retrieve generated keys for productdetails
                    ResultSet generatedKeys = prepare.getGeneratedKeys();
                    int productId = 0;
                    if (generatedKeys.next()) {
                        productId = generatedKeys.getInt(1);  // Get the auto-generated product ID
                    }

                    // Insert product into warehouse
                    String insertDataWarehouse = "INSERT INTO Warehouse (productBarcode, stock, quantityIn) VALUES (?, ?, ?)";
                    prepare = connect.prepareStatement(insertDataWarehouse);
                    prepare.setString(1, khohang_productID.getText());
                    prepare.setInt(2, Integer.parseInt(khohang_stock.getText())); // Store CurrentStock
                    prepare.setInt(3, Integer.parseInt(khohang_stock.getText())); // Store QuantityIn equal to Stock
                    prepare.executeUpdate(); // Execute the insert for warehouse

                    // Insert into warehousegeneral
                    String insertWarehouseGeneral = "INSERT INTO warehousegeneral (warehouseId, warehouseDate) values (?, CURRENT_DATE())";
                    PreparedStatement preparedStatement = connect.prepareStatement(insertWarehouseGeneral);
                    preparedStatement.setInt(1, productId); // Use productId or another warehouse identifier if needed
                    preparedStatement.executeUpdate(); // Execute the insert for warehousegeneral

                    // Show success message
                    alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Notification");
                    alert.setHeaderText(null);
                    alert.setContentText(khohang_productID.getText() + " has been added.");
                    alert.showAndWait();

                    // Refresh the product list
                    getProducts();
                    khohangClear();  // Clear fields after adding
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }




    //Chuc nang Clear
    public void khohangClear(){
        khohang_productID.setText("");
        khohang_productName.setText("");
        category_combobox.getSelectionModel().clearSelection();
        unit_combobox.getSelectionModel().clearSelection();
        khohang_stock.setText("");
        khohang_price.setText("");
        data.path = "";
        khohang_imageview.setImage(null);
    }

//    Chuc nang Update
public void khohangUpdate() throws SQLException {
    connect = connectionClass.getConnection();
    int newStock = Integer.parseInt(khohang_stock.getText());

    if (khohang_productID.getText().isEmpty()
            || khohang_productName.getText().isEmpty()
            || category_combobox.getSelectionModel().getSelectedItem() == null
            || unit_combobox.getSelectionModel().getSelectedItem() == null
            || khohang_stock.getText().isEmpty()
            || khohang_price.getText().isEmpty()
            || data.path == null
            || txtSupplier.getText().isEmpty()) {  // Check if Supplier is entered

        alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error Message");
        alert.setHeaderText(null);
        alert.setContentText("Please enter all blank fields.");
        alert.showAndWait();
    } else {
        String formattedPath = data.path.replace("\\", "\\\\"); // Format the path
        String updateDataProducts = "UPDATE productdetails SET "
                + "productBarcode='" + khohang_productID.getText() + "', productName='"
                + khohang_productName.getText() + "', categoryID= '"
                + category_combobox.getSelectionModel().getSelectedItem() + "', productUnit='"
                + unit_combobox.getSelectionModel().getSelectedItem() + "', productPrice='"
                + khohang_price.getText() + "', productImage='"
                + formattedPath + "', productSupplier='"  // Update the supplier
                + txtSupplier.getText() + "' WHERE productBarcode='" + khohang_productID.getText() + "'";

        try {
            alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText("Are you sure you want to UPDATE ProductID: " + khohang_productID.getText() + "?");
            Optional<ButtonType> option = alert.showAndWait();
            if (option.get() == ButtonType.OK) {
                prepare = connect.prepareStatement(updateDataProducts);
                prepare.executeUpdate();
                alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Success");
                alert.setHeaderText(null);
                alert.setContentText("Product successfully updated.");
                alert.showAndWait();
                getProducts();
                khohangClear();
            } else {
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Canceled to update product!");
                alert.showAndWait();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

        //Chuc nang Delete
    public void khohangDelete() {
        if (khohang_productID.getText().isEmpty()) {
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText("Please enter a Product ID to delete.");
            alert.showAndWait();
        } else {
            String dateDataWarehoue = "DELETE FROM warehouse WHERE productBarcode = ?";
            String deleteDataProducts = "DELETE FROM productdetails WHERE productBarcode = ?";


            connect = connectionClass.getConnection();

            try {
                alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Confirmation Message");
                alert.setHeaderText(null);
                alert.setContentText("Are you sure you want to DELETE ProductID: " + khohang_productID.getText() + "?");
                Optional<ButtonType> option = alert.showAndWait();

                if (option.isPresent() && option.get() == ButtonType.OK) {
                    prepare = connect.prepareStatement(deleteDataProducts);
                    PreparedStatement preparedStatement = connect.prepareStatement(dateDataWarehoue);
                    prepare.setString(1, khohang_productID.getText()); // Gán giá trị cho tham số
                    preparedStatement.setString(1, khohang_productID.getText());

                    preparedStatement.executeUpdate();
                    int rowsAffected = prepare.executeUpdate();

                    if (rowsAffected > 0) {
                        alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("Information Message");
                        alert.setHeaderText(null);
                        alert.setContentText("Product has been deleted successfully.");
                        alert.showAndWait();

                        getProducts();
                        khohangClear();
                    } else {
                        alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Error Message");
                        alert.setHeaderText(null);
                        alert.setContentText("Product ID not found.");
                        alert.showAndWait();
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    //Chon san pham tren bang
    public void khohangSelectProduct(){
        warehouseItems product = khohang_table.getSelectionModel().getSelectedItem();
        if (product == null) return; // Kiểm tra nếu không có sản phẩm nào được chọn

        // Cập nhật các trường thông tin từ sản phẩm đã chọn
        khohang_productID.setText(product.getProductID());
        khohang_productName.setText(product.getProductName());
        khohang_stock.setText(String.valueOf(product.getStock())); // Hiển thị đúng giá trị Stock
        khohang_price.setText(String.valueOf(product.getPrice()));

        // Đặt CategoryID và Unit từ sản phẩm đã chọn vào ComboBox
        category_combobox.getSelectionModel().select(String.valueOf(product.getCategoryID()));
        unit_combobox.getSelectionModel().select(product.getUnit());


        // Cập nhật đường dẫn ảnh từ sản phẩm đã chọn
        data.path = product.getImage(); // Lấy đường dẫn từ database
        System.out.println("Product ID: " + product.getProductID());

        if (data.path != null && !data.path.isEmpty()) {
            // Thêm "file:" vào trước đường dẫn nếu cần thiết
            File file = new File(data.path);
            if (file.exists()) {
                image = new Image(file.toURI().toString(), 203, 117, false, true);
                khohang_imageview.setImage(image);
            } else {
                System.out.println("File ảnh không tồn tại: " + data.path);
                khohang_imageview.setImage(null); // Nếu không có ảnh, để trống ImageView
            }
        } else {
            khohang_imageview.setImage(null); // Nếu không có ảnh, để trống ImageView
        }
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
    //Chuc nang Import hinh anh
    public void khohangImport(){
        FileChooser openFlie = new FileChooser();
        openFlie.getExtensionFilters().add(new FileChooser.ExtensionFilter("Open Image File", "*.png", "*.jpg"));
        String targetPath = "D:\\CODE\\Java\\JavaFX\\assignmentApp\\src\\main\\resources\\com\\example\\assignmentapp\\Images\\";
        File file = openFlie.showOpenDialog(khohangScene.getScene().getWindow());
        if (file != null) {
            moveImage(file, targetPath);
            data.path = file.getName();
            image = new Image(file.toURI().toString(), 203, 117, false, true);
            System.out.println("URI: " + file.toURI());
            khohang_imageview.setImage(image);
        }
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        table_productid.setCellValueFactory(new PropertyValueFactory<>("productID"));
        table_productname.setCellValueFactory(new PropertyValueFactory<>("productName"));
        table_categoryid.setCellValueFactory(new PropertyValueFactory<>("categoryID"));
        table_unit.setCellValueFactory(new PropertyValueFactory<>("unit"));
        table_stock.setCellValueFactory(new PropertyValueFactory<>("stock"));
        table_price.setCellValueFactory(new PropertyValueFactory<>("price"));
        table_quantityin.setCellValueFactory(new PropertyValueFactory<>("quantityIn"));
        getProducts();
        loadCategoryIDs();
        khohangUnitList();
    }
}
