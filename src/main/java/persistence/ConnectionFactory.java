/**
 * 
 */
package persistence;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author clah
 * @since 30/03/2018
 */
public class ConnectionFactory {
	
	public static Connection getConnection() {
		String driver = "jdbc:mysql://localhost/docmanager";
		String user = "root";
		String pass = System.getenv("DATABASE_PASSWORD");
		
		try {
			return DriverManager.getConnection(driver,user,pass);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}		
		
	}
	
	public static void fechaConnection(Connection con) {
		
		try {
			if(con!=null) {
				con.close();
			}
			
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
	public static void fechaConnection(Connection con, PreparedStatement stmt) {
		fechaConnection(con);
		
		try {
			if(stmt != null) {
				stmt.close();
			}
			
		} catch (SQLException e) {
			throw new RuntimeException(e);

		}
	}
	
	public static void fechaConnection(Connection con, PreparedStatement stmt, ResultSet rs) {
		fechaConnection(con, stmt);
		
		try {
			if(rs!=null) {
				rs.close();
			}
			
		} catch (SQLException e) {
			throw new RuntimeException(e);

		}
	}
}
