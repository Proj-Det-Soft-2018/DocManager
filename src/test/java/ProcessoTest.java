import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.Collection;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import negocio.dominio.Assunto;
import negocio.dominio.Interessado;
import negocio.dominio.Orgao;
import negocio.dominio.Processo;
import negocio.dominio.Situacao;
import persistencia.ProcessoDao;
/**
 * 
 * @author Allan
 *
 */
@RunWith(value = Parameterized.class)
public class ProcessoTest {
	@Parameters
	public static Collection <Object[]> data(){
		return Arrays.asList(new Object[][] {
			{"1", new Interessado("Allan", "000.000.000-00", "123", "123"), new Assunto("assunto"), new Orgao("IMD"), Situacao.getById(0)},
			{"2", new Interessado("Clarissa", "000.000.000-01", "1213", "2123"), new Assunto("assunto"), new Orgao("IMD"), Situacao.getById(0)},
			{"3", new Interessado("Hugo", "000.000.000-02", "1233", "1234"), new Assunto("assunto"), new Orgao("IMD"), Situacao.getById(0)},
			{"4", new Interessado("Uira", "000.000.000-03", "1236", "1237"), new Assunto("assunto"), new Orgao("IMD"), Situacao.getById(0)},
			{"5", new Interessado("Miguel", "000.000.000-04", "1238", "123"), new Assunto("assunto"), new Orgao("IMD"), Situacao.getById(0)},
			{"6", new Interessado("John Doe", "000.000.000-05", "1238", "1233"), new Assunto("assunto"), new Orgao("IMD"), Situacao.getById(0)},
		});
	}
	
	private static ProcessoDao banco = new ProcessoDao();
	
	private String numero;
	private Interessado interessado;
	private Assunto assunto;
	private Orgao unidadeOrigem;
	private Situacao situacaoAtual;
	
	public ProcessoTest(String numero, Interessado interessado, Assunto assunto, Orgao unidadeOrigem, Situacao situacaoAtual) {
		super();
		this.numero = numero;
		this.interessado = interessado;
		this.assunto = assunto;
		this.unidadeOrigem = unidadeOrigem;
		this.situacaoAtual = situacaoAtual;
	}
	
	@Test
	public void inserirTest() {
		Processo p = new Processo(false, this.numero, this.interessado, this.assunto, this.unidadeOrigem, this.situacaoAtual);
		banco.salvar(p);
		assertEquals(p, banco.getById(p.getNumero()));
	}
	
	/**
	@Test
	public void inserirMesmoTest() {
		Processo p = new Processo(false, this.numero, this.interessado, this.assunto, this.unidadeOrigem, this.situacaoAtual);
		banco.salvar(p);
		
	}
	*/
}
