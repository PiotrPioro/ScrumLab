
package pl.coderslab.utils;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DbUtil {
    private static DataSource dataSource;

    public static Connection getConnection() throws SQLException {
        return getInstance().getConnection();
    }

    private static DataSource getInstance() {
        if (dataSource == null) {
            try {
                Context context = new InitialContext();
                dataSource = (DataSource) context.lookup("java:comp/env/jdbc/scrumlab");
            } catch (NamingException e) {
                e.printStackTrace();
            }
        }
        return dataSource;
    }
/*    private static final String DB_URL_dao_data_base = "jdbc:mysql://localhost:3306/scrumlab?serverTimezone=UTC&useSSL=false&characterEncoding=utf8";
    private static final String DB_USER = "root";
    private static final String DB_PASS = "Orzel777";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL_dao_data_base, DB_USER, DB_PASS);
    }*/
}