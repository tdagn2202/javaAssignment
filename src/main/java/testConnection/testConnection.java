package testConnection;

import dbConnection.connectionClass;

import java.sql.Connection;
import DAO.loginDAO;
public class testConnection {
    public static void main(String[] args) {
        Connection conn = connectionClass.getConnection();
        if(conn!=null) System.out.println("Connected");


    }
}
