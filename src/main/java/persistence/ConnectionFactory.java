/**
 * 
 */
package persistence;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import persistence.exception.DatabaseException;

/**
 * @author clah
 * @since 30/03/2018
 */
public class ConnectionFactory {
	
	public static Connection getConnection() throws DatabaseException {
		String driver = "jdbc:mysql://localhost/docmanager";
		String user = "root";
		String pass = System.getenv("DATABASE_PASSWORD");
		
		try {
			return DriverManager.getConnection(driver,user,pass);
		} catch (SQLException e) {
			throw new DatabaseException("Não foi possível estabelecer conexão com o Banco de Dados.");
		}		
		
	}
	
	public static void fechaConnection(Connection con) throws DatabaseException {
		
		try {
			if(con!=null) {
				con.close();
			}
			
		} catch (SQLException e) {
			throw new DatabaseException("Não foi possível encerrar conexão com o Banco de Dados.");
		}
	}
	
	public static void fechaConnection(Connection con, PreparedStatement stmt) throws DatabaseException {
		fechaConnection(con);
		
		try {
			if(stmt != null) {
				stmt.close();
			}
			
		} catch (SQLException e) {
			throw new DatabaseException("Não foi possível destruir o statement construído.");

		}
	}
	
	public static void fechaConnection(Connection con, PreparedStatement stmt, ResultSet rs) throws DatabaseException {
		fechaConnection(con, stmt);
		
		try {
			if(rs!=null) {
				rs.close();
			}
			
		} catch (SQLException e) {
			throw new DatabaseException("Não foi possível destruir o Result Set.");

		}
	}
}
