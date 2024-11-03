package DAO;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import dbConnection.connectionClass;

public class loginDAO implements loginDAOIterface{

    public static loginDAO openConnection(){
        return new loginDAO();
    }

    @Override
    public boolean login(acocunt anct) {
        try
        {
            Connection connection = connectionClass.getConnection();
            Statement st = connection.createStatement();

            String sql = "SELECT COUNT(*) as count FROM users WHERE uid = '" + anct.getUsername() + "' AND pwd = '" + anct.getPassword() + "'";
            ResultSet rs = st.executeQuery(sql);
            while(rs.next()){
                if(rs.getInt("count")>0) {
                    return true;
                }
            }

        } catch (Exception e){
            e.printStackTrace();
        }



        return false;

    }
}
