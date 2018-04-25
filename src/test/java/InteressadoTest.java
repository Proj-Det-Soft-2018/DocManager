import java.util.Arrays;
import java.util.Collection;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import business.exception.ValidationException;
import business.model.Interested;

@RunWith(value = Parameterized.class)
public class InteressadoTest {
	
	@Parameters
	public static Collection <Object[]> data(){
		return Arrays.asList(new Object[][] {
			{null, "84999999999"},
			{"teste dois", null},
			{"", "84999999999"},
			{"teste1", "8433334444"},
			{"teste1", "8433"},
		});
	}
	
	private String nome;
	private String contato;
	
	public InteressadoTest(String nome, String contato) {
		this.nome = nome;
		this.contato = contato;
	}



	@Test(expected = ValidationException.class)
	public void creationTest() throws ValidationException {
		Interested i = new Interested();
		i.setName(this.nome);
		i.setContact(this.contato);
	}

}
