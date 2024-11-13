package com.example.assignmentapp.reportGenerator;

import com.itextpdf.kernel.colors.DeviceGray;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.properties.TextAlignment;
import com.itextpdf.layout.properties.UnitValue;
import dbConnection.connectionClass;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.*;

public class pdfGenerator {
    public void createInvoicePdf(Stage stage, int billId) throws IOException, SQLException {
        Connection connection = connectionClass.getConnection();
        List<billData> billDataList = new ArrayList<>();  // Renamed list to billDataList

        String sqlQuery = "SELECT pd.productName, bd.detailQuantity, pd.productPrice, bd.detailAmount, b.billId " +
                "FROM bill b " +
                "JOIN bill_details bd ON b.billId = bd.billId " +
                "JOIN productdetails pd ON bd.productBarcode = pd.productBarcode " +
                "WHERE b.billId = ?;";
        PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery);
        preparedStatement.setInt(1, billId);

        ResultSet resultSet = preparedStatement.executeQuery();

        // Populate the list with data from the result set
        while (resultSet.next()) {
            System.out.println("Data: " + resultSet.getString(1));
            billData billDate = new billData(
                    resultSet.getString(1),
                    resultSet.getInt(2),
                    resultSet.getDouble(3),
                    resultSet.getDouble(4),
                    resultSet.getInt(5)
            );
            billDataList.add(billDate);
        }

        // Check if data was fetched properly
        if (billDataList.isEmpty()) {
            System.out.println("No bill data found for billId: " + billId);
        } else {
            for (billData item : billDataList) {
                System.out.println("Fetched item: " + item.getProductName() + ", Quantity: " + item.getQuantity() +
                        ", Price: " + item.getPrice() + ", Total: " + item.getTotal());
            }
        }

        // Proceed to create PDF as before
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save PDF");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("PDF Files", "*.pdf"));
        File file = fileChooser.showSaveDialog(stage);

        PdfWriter pdf = new PdfWriter(file.getAbsolutePath());
        PdfDocument pdfDocument = new PdfDocument(pdf);
        Document document = new Document(pdfDocument);

        String fontPath = "C:\\Windows\\Fonts\\times.ttf";  // Replace with your font path
        PdfFont font = PdfFontFactory.createFont(fontPath, "Identity-H");

        // Title and header
        Paragraph title = new Paragraph("HÓA ĐƠN TÍNH TIỀN").setFont(font)
                .setTextAlignment(TextAlignment.CENTER)
                .setBold()
                .setFontSize(16);
        document.add(title);

        Paragraph subtitle = new Paragraph("SmallC Supermarket").setFont(font)
                .setTextAlignment(TextAlignment.CENTER)
                .setFontSize(12);
        document.add(subtitle);

        LocalDateTime now = LocalDateTime.now();
        document.add(new Paragraph("\nNgày lập hóa đơn: " + now).setFont(font)
                .setFontSize(10)
                .setMarginTop(10));
        document.add(new Paragraph("Mã số hóa đơn: " + billId).setFont(font)
                .setFontSize(10));

        // Invoice details table
        document.add(new Paragraph("\nChi tiết hóa đơn:").setBold().setFontSize(10).setFont(font));

        // Define column widths
        float[] columnWidths = {1, 5, 1, 2, 2};
        Table table = new Table(UnitValue.createPercentArray(columnWidths));
        table.setWidth(UnitValue.createPercentValue(100));
        table.setFont(font);

        // Table header
        table.addHeaderCell(new Cell().add(new Paragraph("STT").setBold()));
        table.addHeaderCell(new Cell().add(new Paragraph("Tên sản phẩm").setBold()));
        table.addHeaderCell(new Cell().add(new Paragraph("SL").setBold()));
        table.addHeaderCell(new Cell().add(new Paragraph("Đơn giá").setBold()));
        table.addHeaderCell(new Cell().add(new Paragraph("Thành tiền").setBold()));

        int stt = 0;
        double totalPrice = 0.0;

        // Add rows dynamically
        for (billData e : billDataList) {
            stt++;
            table.addCell(new Cell().add(new Paragraph(String.valueOf(stt))));
            table.addCell(new Cell().add(new Paragraph(e.getProductName())));
            table.addCell(new Cell().add(new Paragraph(String.valueOf(e.getQuantity()))));
            table.addCell(new Cell().add(new Paragraph(String.valueOf(e.getPrice()))));
            table.addCell(new Cell().add(new Paragraph(String.valueOf(e.getTotal()))));
            totalPrice += e.getTotal();
        }

        document.add(table);

        // Total payment
        document.add(new Paragraph("\nTổng thanh toán: " + totalPrice).setFont(font)
                .setTextAlignment(TextAlignment.RIGHT)
                .setBold()
                .setFontSize(10));

        // Footer message
        document.add(new Paragraph("\nCảm ơn vì đã đến!").setFont(font)
                .setTextAlignment(TextAlignment.CENTER)
                .setFontSize(10)
                .setItalic());

        document.close();
    }

}
