package dbConnection;
import java.sql.Connection;
import com.mysql.cj.jdbc.Driver;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
public class connectionClass {
    public  static Connection getConnection(){
        Connection connection = null;

        try {
            Driver driver = new Driver();
            DriverManager.registerDriver(driver);
            String connectionString = "jdbc:mySQL://localhost:3306/supermarket_pos";
            String username = "root";
            String password = "123456";

            connection =DriverManager.getConnection(connectionString, username, password);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return connection;
    }

}
