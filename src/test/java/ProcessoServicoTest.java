/**
 * 
 */


import static org.junit.Assert.*;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import negocio.dominio.Processo;
import negocio.servico.ProcessoServico;

/**
 * @author clah
 * @since 07/04/2018
 */
public class ProcessoServicoTest {

	private ProcessoServico servico;

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		servico = ProcessoServico.getInstance();
	}
	
	@Test
	public void testBuscaPorOrgao() {
		
		List <Processo> lista = servico.burcarProcessos(null, null, null, 0, 1, 0);
		if(lista ==null) {
			fail("Deveria retornar 2 processos");
		}else {
			this.imprimir(lista, "\n###busca por orgao###\n");
		}
		
		assertEquals(2, lista.size(), 0);
		
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
