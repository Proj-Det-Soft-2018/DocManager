import static org.junit.Assert.*;

import java.sql.Connection;

import org.junit.Test;

import persistencia.ConnectionFactory;

/**
 * 
 */

/**
 * @author clah
 *
 */
public class ConnectionTest {

	@Test
	public void testConexaoSucesso() {
		Connection con = ConnectionFactory.getConnection();
		assertNotNull(con);	
		ConnectionFactory.fechaConnection(con);
		
	}

}
