import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import negocio.dominio.Processo;
import persistencia.ProcessoDaoMySql;

/**
 * 
 */

/**
 * @auhor
 *
 */
public class ProcessoDaoMySqlTest {

	private ProcessoDaoMySql dao;

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		dao = new ProcessoDaoMySql();
	}
	
	@Test
	public void testBuscaPorNumero() {
		List <Processo> lista = dao.buscarPorNumero("23057012568018");
		if(lista ==null) {
			fail("Deveria retornar 1 processo");
		}else {
			this.imprimir(lista,"\n###busca por numero###\n");
		}
		
		assertEquals(1, lista.size(), 0);
		
	}
	
	@Test
	public void testBuscaPorNomeInteressado() {
		List <Processo> lista = dao.buscarPorNomeInteressado("LáR");
		if(lista ==null) {
			fail("Deveria retornar 3 processos");
		}else {
			this.imprimir(lista,"\n###busca por nome###\n");
		}
		
		assertEquals(3, lista.size(), 0);
		
	}
	
	@Test
	public void testBuscaPorCpfInteressado() {
		List <Processo> lista = dao.buscarPorCpfInteressado("06570555499");
		if(lista.isEmpty()) {
			fail("Deveria retornar 2 processos");
		}else {
			this.imprimir(lista,"\n###busca por cpf###\n");
		}
		
		assertEquals(2, lista.size(), 0);
		
	}

	@Test
	public void testBuscaPorSituacao() {
		List <Processo> lista = dao.buscarPorSituacao(1);
		if(lista ==null) {
			fail("Deveria retornar 3 processos");
		}else {
			this.imprimir(lista,"\n###busca por situacao###\n");
		}
		
		assertEquals(3, lista.size(), 0);
		
	}
	
	@Test
	public void testBuscaPorOrgao() {
		List <Processo> lista = dao.buscarPorOrgao(1);
		if(lista ==null) {
			fail("Deveria retornar 2 processos");
		}else {
			this.imprimir(lista, "\n###busca por orgao###\n");
		}
		
		assertEquals(2, lista.size(), 0);
		
	}
	
	@Test
	public void testBuscaPorId() {
		Processo processo = dao.pegarPeloId(Long.parseLong("1"));
		if(processo ==null) {
			fail("Deveria retornar 1 processos");
		}else {
			List<Processo> lista = new ArrayList<>();
			lista.add(processo);
			this.imprimir(lista, "\n###busca por ID###\n");
		}
		
		assertNotNull(processo);
		
	}
	
	private void imprimir(List<Processo> lista, String tipoBusca) {
		System.out.println(tipoBusca);
		for (Processo processo : lista) {
			System.out.println("==============Dados do Processo===========");
			System.out.println("Nome:"+processo.getInteressado().getNome());
			System.out.println("CPF:"+processo.getInteressado().getCpf());
			System.out.println("Numero: "+processo.getNumero());
			
			System.out.println("Situacao "+ processo.getSituacao().toString());
			System.out.println("Orgao: "+processo.getUnidadeOrigem().toString());
			System.out.println("Assunto: "+processo.getAssunto().toString());
				
		}
	}

}
