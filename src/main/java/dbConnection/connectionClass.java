package dbConnection;
import java.sql.*;

import com.example.assignmentapp.reportGenerator.billData;
import com.mysql.cj.jdbc.Driver;
import com.mysql.cj.protocol.PacketReceivedTimeHolder;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

public class connectionClass {

    public static Connection getConnection() {
        Connection connection = null;

        try {
            Driver driver = new Driver();
            DriverManager.registerDriver(driver);
            String connectionString = "jdbc:mySQL://localhost:3306/supermarket_pos";
            String username = "root";
            String password = "123456";

            connection = DriverManager.getConnection(connectionString, username, password);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return connection;
    }
}

