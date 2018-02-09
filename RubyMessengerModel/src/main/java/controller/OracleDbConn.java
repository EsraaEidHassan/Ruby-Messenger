package controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * @author Mahmoud.Marzouk
 * @since 08/02/2018
 */
public class OracleDbConn {

    private static Connection dbConn;

    private OracleDbConn() {
    }

    public static Connection getDatabaseConnection() {
        try {
            if (dbConn == null || dbConn.isClosed()) {
                dbConn = DriverManager.getConnection("jdbc:oracle:thin:@titan.ccj2vba3rgd1.eu-central-1.rds.amazonaws.com:1480:ruby",
                        "chat", "chat654321");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return dbConn;
    }
}
