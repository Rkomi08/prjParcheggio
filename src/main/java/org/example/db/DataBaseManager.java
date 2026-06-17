package org.example.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DataBaseManager {

    private static final String URL = "jdbc:mysql://sql7.freesqldatabase.com/sql7830758";
    private static final String USER = "sql7830758";
    private static final String PASSWORD = "lRzfaXeVMN";


    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}