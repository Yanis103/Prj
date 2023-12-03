package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/*Connexion distante*/
public class Connexion {
	private static String url = "jdbc:mysql://sql11.freesqldatabase.com:3306/sql11667156";
    private static String user = "sql11667156";
    private static String password = "fKQ52kr9qa";
	
    public static Connection getConnection() throws SQLException {
    	try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			return DriverManager.getConnection(url, user, password);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}       
    }
}

/*Connexion locale*/
/*import java.sql.SQLException;

public class Connexion {
    // JDBC URL, username, and password of MySQL server
    static final String JDBC_URL = "jdbc:mysql://localhost:3306/Projet";
    static final String DB_USER = "root";
    static final String DB_PASSWORD = "";
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASSWORD);
    }
}
*/
