import org.junit.Before;
import org.junit.Test;

import negocio.dominio.Interessado;
import persistencia.InteressadoDaoMySql;

/**
 * 
 */

/**
 * @author clah
 * @since 31/03/2018
 */
public class InteressadoDaoMySqlTest {
	private Interessado interessado1;
	private Interessado interessado2;
	
	@Before
	public void setup() {
		interessado1 = new Interessado("Clarissa","06570555499","2020-1515");
		interessado2 = new Interessado("Hugo", "1234567899", "2020-4558");

	}
	
	@Test
	public void testInserirComSucesso() {
		InteressadoDaoMySql dao = new InteressadoDaoMySql();
		System.out.println(interessado1.getCpf());
		dao.salvar(interessado1);
	}
	
	@Test
	public void testInserirMesmointeressado() {
		InteressadoDaoMySql dao = new InteressadoDaoMySql();
		System.out.println(interessado1.getCpf());
		dao.salvar(interessado1);
	}
	
	

}
