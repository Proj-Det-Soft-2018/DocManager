import java.util.Arrays;
import java.util.Collection;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import business.model.Process;
import business.service.ValidationException;
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
			{false, "00000010110145", 0, 2, 3},
			{false, "00000010110141", 10, 0, 3},
			{false, "00000010110143", 13, 2, 0},
			{false, "000000101101430", 11, 3, 1},
			{true, "a1234567", 14, 4, 2},
			{true, "1112", 8, 5, 2},
		});
	}
	
	private boolean oficio;
	private String numero;
	private int assunto;
	private int orgao;
	private int situacao;

	public ProcessoTest(boolean oficio, String numero, int assunto, int orgao, int situacao) {
		this.oficio = oficio;
		this.numero = numero;
		this.assunto = assunto;
		this.orgao = orgao;
		this.situacao = situacao;
	}
	
	@Test(expected = ValidationException.class)
	public void creationTest() {
		Process p = new Process();
		p.setTipoOficio(this.oficio);
		p.setNumero(this.numero);
		p.setAssuntoById(this.assunto);
		p.setUnidadeOrigemById(this.orgao);
		p.setSituacaoById(this.situacao);
	}
}
