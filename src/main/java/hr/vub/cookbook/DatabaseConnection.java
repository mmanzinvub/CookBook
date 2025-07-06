package hr.vub.cookbook;

import java.sql.Connection;
import java.sql.DriverManager;

public class DatabaseConnection {
    public Connection databaseLink;

    public Connection getConnection(){
        String databaseName = "cookbook_db";
        String databaseUser = "root";
        String databasePassword = "";

        String url = "jdbc:mysql://localhost:3306/" + databaseName;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            databaseLink = DriverManager.getConnection(url, databaseUser, databasePassword);
            System.out.println("Database connection successful!");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return databaseLink;
    }
}