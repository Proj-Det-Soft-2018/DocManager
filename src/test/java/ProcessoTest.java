import java.util.Arrays;
import java.util.Collection;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;
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
	

	@Test
	public void inserirTest() {
		assert(true);
	}
	
	
	
}
