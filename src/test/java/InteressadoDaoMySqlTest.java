import static org.junit.Assert.assertEquals;

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
	InteressadoDaoMySql dao = new InteressadoDaoMySql();
	
	@Before
	public void setup() {
		//interessado1 = new Interessado("Clarissa","06570555499","2020-1515");

	}
	
	@Test
	public void testInserirExcluirComSucesso() {
		dao.salvar(interessado1);
		System.out.println("Depois inserção:");
		for (Interessado inter : dao.pegarTodos()) {			
			System.out.println(inter.getNome());
		}
		Interessado interessado = dao.pegarPeloCpf("06570555499");
		assertEquals(interessado1.getCpf(), interessado1.getCpf());
		dao.deletar(interessado);
		
		System.out.println("Depois exclusão:");
		for (Interessado inter : dao.pegarTodos()) {			
			System.out.println(inter.getNome());
		}
		
	}
	
	
	@Test
	public void testAtualizar() {
		Interessado inter = dao.pegarPeloId(Long.parseLong("2"));
		
		System.out.println("Antes:");
		for (Interessado interessado : dao.pegarTodos()) {			
			System.out.println(interessado.getNome());
		}
		
		dao.deletar(inter);
		
		System.out.println("Depois:");
		for (Interessado interessado : dao.pegarTodos()) {
			System.out.println(interessado.getNome());
		}
		
		
	}
	
	
	
	
	

}
