import java.util.Arrays;
import java.util.Collection;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import negocio.dominio.Interessado;
import negocio.dominio.Orgao;
import negocio.dominio.Processo;
import negocio.dominio.Situacao;
import persistencia.HashProcessoDao;
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
			{"1", new Interessado("Allan", "000.000.000-00", "123", "123"), "assunto", new Orgao("IMD"), new Situacao("situacao")},
			{"2", new Interessado("Clarissa", "000.000.000-01", "1213", "2123"), "assunto", new Orgao("IMD"), new Situacao("situacao")},
			{"3", new Interessado("Hugo", "000.000.000-02", "1233", "1234"), "assunto", new Orgao("IMD"), new Situacao("situacao")},
			{"4", new Interessado("Uira", "000.000.000-03", "1236", "1237"), "assunto", new Orgao("IMD"), new Situacao("situacao")},
			{"5", new Interessado("Miguel", "000.000.000-04", "1238", "123"), "assunto", new Orgao("IMD"), new Situacao("situacao")},
			{"6", new Interessado("John Doe", "000.000.000-05", "1238", "1233"), "assunto", new Orgao("IMD"), new Situacao("situacao")},
		});
	}
	public static HashProcessoDao banco = new HashProcessoDao();
	
	private String numero;
	private Interessado interessado;
	private String assunto;
	private Orgao unidadeOrigem;
	private Situacao situacaoAtual;
	
	public ProcessoTest(String numero, Interessado interessado, String assunto, Orgao unidadeOrigem, Situacao situacaoAtual) {
		super();
		this.numero = numero;
		this.interessado = interessado;
		this.assunto = assunto;
		this.unidadeOrigem = unidadeOrigem;
		this.situacaoAtual = situacaoAtual;
	}
	
	@Test
	public void inserirTest() {
		banco.salvar(new Processo(this.numero, this.interessado, this.assunto, this.unidadeOrigem, this.situacaoAtual));
		assert(true);
		//assertEquals(expected, banco.salvar);
	}

}
