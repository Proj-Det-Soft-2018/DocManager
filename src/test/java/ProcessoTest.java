import java.util.Arrays;
import java.util.Collection;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import negocio.dominio.Processo;
import persistencia.ProcessoDaoMySql;
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
			{},
			{},
			{},
			{},
			{},
			{},
		});
	}
	
	private static ProcessoDaoMySql banco = new ProcessoDaoMySql();

	@Test
	public void inserirTest() {
		Processo p = new Processo(null, false, null, null);
		banco.salvar(p);
		assert(true);
	}
	
	
	
}
