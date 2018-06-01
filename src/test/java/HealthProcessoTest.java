import java.util.Arrays;
import java.util.Collection;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import business.exception.ValidationException;
import business.model.HealthProcess;
import business.model.Process;
/**
 * 
 * @author Allan
 *
 */
@RunWith(value = Parameterized.class)
public class HealthProcessoTest {
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

	public HealthProcessoTest(boolean oficio, String numero, int assunto, int orgao, int situacao) {
		this.oficio = oficio;
		this.numero = numero;
		this.assunto = assunto;
		this.orgao = orgao;
		this.situacao = situacao;
	}
	
	@Test(expected = ValidationException.class)
	public void creationTest() throws ValidationException {
		
		Process p = new HealthProcess();
		p.setTipoOficio(this.oficio);
		p.setNumber(this.numero);
		p.setSubjectById(this.assunto);
		p.setOriginEntityById(this.orgao);
		p.setSituationById(this.situacao);
	}
}
