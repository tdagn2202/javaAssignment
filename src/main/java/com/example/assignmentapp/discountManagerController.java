

package com.example.assignmentapp;

import dbConnection.connectionClass;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.zip.Adler32;

public class discountManagerController implements Initializable {
    @FXML
    private TableView<discountObject> tableData;

    @FXML
    private TableColumn<discountObject, String> stt;

    @FXML
    private TableColumn<discountObject, Integer> km;

    @FXML
    private TableColumn<discountObject, String> clName;

    @FXML
    private TextField b_name;

    @FXML
    private TextField b_sale;

    @FXML
    private TextField b_stt;

    @FXML
    private TextField txtCategoryId;

    @FXML
    private Button update;

    public void show() throws SQLException {
        stt.setCellValueFactory(new PropertyValueFactory<>("categoryID"));
        clName.setCellValueFactory(new PropertyValueFactory<>("caterogyName"));
        km.setCellValueFactory(new PropertyValueFactory<>("discountRate"));

        tableData.setItems(listData());
    }

    public ObservableList<discountObject> listData() throws SQLException {
        Connection connection = connectionClass.getConnection();
        ObservableList<discountObject> list = FXCollections.observableArrayList();
        String sql = "SELECT discountId, categoryName, discountPercentage FROM discount JOIN category c ON c.categoryId = discount.categoryId;";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        ResultSet rs = preparedStatement.executeQuery();

        while (rs.next()) {
            discountObject category = new discountObject(
                    rs.getString(1),
                    rs.getString(2),
                    rs.getInt(3)
            );
            list.add(category);
        }

        return list;
    }

    public void selectData() {
        discountObject c = tableData.getSelectionModel().getSelectedItem();
        if (c == null) {
            System.out.println("null");
        } else {
            txtCategoryId.setText(c.getCaterogyName());
            b_stt.setText(c.getCategoryID());
            b_sale.setText(String.valueOf(c.getDiscountRate()));
        }
    }

//    public void add() throws SQLException {
//        String n = b_name.getText();
//        String s = b_sale.getText();
//        Connection dbConnect = connectionClass.getConnection();
//        addCate(n, Integer.parseInt(s));
//        show();
//    }

    public void clear() throws SQLException {
        txtCategoryId.setText("");
        b_stt.setText("");
        b_sale.setText("");
    }

    public void update() throws SQLException {
        updateCate();
        clear();
        reloadTableData();
    }

    public void delete() throws SQLException {
        deleteCate();
        clear();
        reloadTableData();
    }

    private void reloadTableData() throws SQLException {
        tableData.getItems().clear();  // Clear the existing data
        tableData.setItems(listData()); // Re-fetch and set the updated data
        tableData.refresh();            // Refresh the TableView to display the new data
    }

    private void showAlert(String title, String content){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setHeaderText(null);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.show();
    }

    public void addCate() {
        // Validate inputs
        if (b_stt.getText().isEmpty() || b_sale.getText().isEmpty() || txtCategoryId.getText().isEmpty()) {
            showAlert("Lỗi", "Vui lòng điền đầy đủ thông tin.");
            return;
        }

        double discountRate;
        int categoryId;

        // Parse discount rate and category ID, handle parsing errors
        try {
            discountRate = Double.parseDouble(b_sale.getText());
        } catch (NumberFormatException e) {
            showAlert("Lỗi", "Vui lòng nhập tỉ lệ giảm giá hợp lệ (số).");
            return;
        }

        try {
            categoryId = Integer.parseInt(txtCategoryId.getText());
        } catch (NumberFormatException e) {
            showAlert("Lỗi", "Vui lòng nhập ID danh mục hợp lệ (số).");
            return;
        }

        // Perform the database insert operation
        try {
            Connection connection = connectionClass.getConnection();
            String sqlInsert = "INSERT INTO discount(discountId, discountPercentage, categoryId) VALUES (?, ?, ?)";
            PreparedStatement pr = connection.prepareStatement(sqlInsert);
            pr.setString(1, b_stt.getText());
            pr.setDouble(2, discountRate);
            pr.setInt(3, categoryId);

            int rowsAffected = pr.executeUpdate();
            if (rowsAffected > 0) {
                showAlert("Thông báo", "Thêm giảm giá thành công!");
                clear();
                reloadTableData();
            } else {
                showAlert("Lỗi", "Không thể thêm giảm giá. Vui lòng thử lại.");
            }
        } catch (SQLException sqlException) {
            showAlert("Lỗi hệ thống", sqlException.getMessage());
        }
    }


    public void updateCate() {
        if (b_stt.getText().isEmpty() || b_sale.getText().isEmpty()) {
            showAlert("Lỗi", "Vui lòng chọn phân loại hàng cần cập nhật");
            return;
        }

        try {
            Connection connection = connectionClass.getConnection();
            String sql = "UPDATE discount SET discountPercentage = ? WHERE discountId = ?";
            PreparedStatement pr = connection.prepareStatement(sql);
            double discountRate;
            try {
                discountRate = Double.parseDouble(b_sale.getText());
            } catch (NumberFormatException e) {
                showAlert("Lỗi", "Vui lòng nhập tỉ lệ hợp lệ");
                return;
            }

            pr.setDouble(1, discountRate);
            pr.setString(2, b_stt.getText());

            int rowsAffected = pr.executeUpdate();
            if (rowsAffected > 0) {
                showAlert("Thành công", "Tỉ lệ giảm giá đã được cập nhật!");
            } else {
                showAlert("Lỗi", "Không tìm thấy mã giảm giá");
            }
        } catch (SQLException sqlException) {
            showAlert("Lỗi hệ thống", sqlException.getMessage());
        }
    }


    public void deleteCate() {
        String id = b_stt.getText();
        if (id.isEmpty()) {
            showAlert("Lỗi", "Vui lòng chọn mục muốn xóa");
            return;
        }

        try {
            Connection connection = connectionClass.getConnection();
            String sql = "DELETE FROM discount WHERE discountId = ?";
            PreparedStatement pr = connection.prepareStatement(sql);
            pr.setString(1, id);

            int rowsAffected = pr.executeUpdate();
            if (rowsAffected > 0) {
                showAlert("Thành công", "Đã xóa giảm giá này");
            } else {
                showAlert("Lỗi", "Không tìm thấy mã giảm giá");
            }
        } catch (SQLException sqlException) {
            showAlert("Lỗi hệ thống", sqlException.getMessage());
        }
    }



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            show();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
