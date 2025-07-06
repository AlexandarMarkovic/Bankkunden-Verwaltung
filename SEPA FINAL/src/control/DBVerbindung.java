package control;

import java.sql.*;

public class DBVerbindung {
    public static Connection getConnection() throws SQLException {
        // verbindet mit der Datenbank "Bank"
        return DriverManager.getConnection(
            "jdbc:mysql://localhost:3306/Bank?useUnicode=true&characterEncoding=utf8",
            "root", "");
    }
}
