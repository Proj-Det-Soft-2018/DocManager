import org.junit.runner.RunWith;
import org.junit.runners.Suite;
/**
 * 
 * @author Allan
 *
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({
	ConnectionTest.class,
	InteressadoDaoMySqlTest.class,
	ProcessoDaoMySqlTest.class,
	ProcessoServicoTest.class,
	ProcessoTest.class,
})
public class JunitTestSuite {
}
