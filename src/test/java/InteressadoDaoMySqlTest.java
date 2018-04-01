import java.util.List;

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
	InteressadoDaoMySql dao = new InteressadoDaoMySql();
	
	@Before
	public void setup() {
		interessado1 = new Interessado("Clarissa","06570555499","2020-1515");
		interessado2 = new Interessado("Hugo", "1234567899", "2020-4558");

	}
	
	@Test
	public void testExcluirInteressado() {
		Interessado inter = dao.getById("2");
		
		System.out.println("Antes:");
		for (Interessado interessado : dao.getAll()) {			
			System.out.println(interessado.getNome());
		}
		
		dao.deletar(inter);
		
		System.out.println("Depois:");
		for (Interessado interessado : dao.getAll()) {
			System.out.println(interessado.getNome());
		}
		
		
	}
	
	
	
	
	

}
