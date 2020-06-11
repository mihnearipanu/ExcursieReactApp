package autogara.persistence;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class JDBCUtils {

    private Properties jdbcProps;

    public JDBCUtils(Properties props){
        jdbcProps = props;
    }

    private Connection connection = null;
    private Connection getNewConnection(){
        String url = jdbcProps.getProperty("baza.jdbc.url");
        Connection conn = null;
        try{
            conn = DriverManager.getConnection(url);
        }catch(SQLException e){
            System.out.println("Error getting connection: " + e);
        }
        return conn;
    }

    public  Connection getConnection(){
        try {
            if(connection == null || connection.isClosed()){
                connection = getNewConnection();
            }
        }catch (SQLException e) {
            System.out.println("Error getting connection "+e);
        }
        return connection;
    }

    public static Connection connect(){
        String url = "jdbc:sqlite:C:\\Users\\ripan\\Desktop\\Proiect MPP Networking\\BazaDate";
        Connection connection = null;
        try{
            connection = DriverManager.getConnection(url);
        }catch(SQLException ex){
            System.out.println(ex.getMessage());
        }
        return connection;
    }
}
