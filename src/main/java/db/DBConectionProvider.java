package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConectionProvider {

    private static final DBConectionProvider INSTANCE = new DBConectionProvider();

    private Connection connection;

    public static DBConectionProvider getInstance() {

        return INSTANCE;
    }


    private final String DB_URL = "jdbc:mysql://localhost:3306/event_register?useUnicode=true";
    private final String USERNAME = "root";
    private final String PASSWORD = "rootroot";

    private DBConectionProvider() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }


    public Connection getConnection() {
        try {
            if (connection == null || connection.isClosed()) {
                connection = DriverManager.getConnection(DB_URL,USERNAME,PASSWORD);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return connection;
    }
}
