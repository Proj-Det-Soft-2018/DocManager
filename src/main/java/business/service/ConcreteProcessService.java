/**
 * 
 */
package business.service;

import java.util.List;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.realm.text.IniRealm;
import org.apache.shiro.subject.Subject;

import business.model.Process;
import business.model.Situation;
import persistence.ProcessoDao;
import persistence.ProcessoDaoMySql;

/**
 * @author clah
 *@since 24/03/2018
 */
public class ConcreteProcessService extends Observable implements ProcessService {

	private ProcessoDao processoDao;
	private Subject currentUser;

	// Singleton
	private static final ConcreteProcessService instance = new ConcreteProcessService();

	private ConcreteProcessService() {
		processoDao = new ProcessoDaoMySql();

		// Inicilização do Apache Shiro -- utiliza o resources/shiro.ini
		IniRealm iniRealm = new IniRealm("classpath:shiro.ini");
		SecurityManager secutiryManager = new DefaultSecurityManager(iniRealm);
		SecurityUtils.setSecurityManager(secutiryManager);

		currentUser = SecurityUtils.getSubject();
	}

	public static ConcreteProcessService getInstance() {
		return instance;
	}


	@Override
	public void save(Process process) {
		//Antes de salvar verificar os campos que nao podem ser nulos
		this.validarNumeroDuplicado(process.getNumero());

		processoDao.salvar(process);
		this.notifyObservers();
	}

	@Override
	public void update(Process process) {
		processoDao.atualizar(process);
		this.notifyObservers();			
	}

	@Override
	public void delete(Process process, String admUser, String password) {

		if (!this.currentUser.isAuthenticated()) {
			UsernamePasswordToken token = new UsernamePasswordToken(admUser, password);
			token.setRememberMe(true);

			currentUser.login(token); // Joga uma AuthenticationException
		}

		if (currentUser.hasRole("admin")) {
			processoDao.deletar(process);
			this.notifyObservers();
		}

		currentUser.logout();
	}

	public List<Process> getList(){
		return processoDao.pegarTodos();
	}



	/**
	 * Método procura no banco se tem outro processo com o mesmo número. Se tem, o registro deve
	 *  estar com a situação definida como concluída. Caso contrário, pede confirmação do 
	 *  usuário para modificar situacao do registro antigo como concluido.
	 *  
	 * @param numero Numero do processo que está sendo inserido.
	 */
	public void validarNumeroDuplicado(String numero) {
		List<Process> duplicados = processoDao.buscarPorNumero(numero);
		if(duplicados != null && !duplicados.isEmpty()) {
			//verifica se a situacao dos processos encontrados estao como concluido
			for (Process processo : duplicados) {
				if(!(processo.getSituacao().ordinal()==Situation.CONCLUIDO.ordinal()) ) {
					//TODO tratar e criar Exception
					//throw new ProcessoDuplicadoException("Existe outro processo cadastrado com situação não concluída");
				}				
			}			
		}		
	}


	public List<Process> search(String number, String name, String cpf, int situation, int organization, int subject) {
		
		boolean invalidNumber = (number == null || number.isEmpty());
		boolean invalidName = (name == null || name.isEmpty());
		boolean invalidCpf = (cpf == null || cpf.isEmpty());
		boolean invalidSituation = (situation == 0);
		boolean invalidOrganization = (organization == 0);
		boolean invalidSubject = (subject == 0);
		
		if(invalidNumber && invalidName && invalidCpf && invalidSituation && invalidOrganization && invalidSubject) {
			throw new ValidationException("BUSCA INVÁLIDA!", "Busca", "Não foram inseridos valores para busca!");
		}
		return processoDao.buscaComposta(number, name, cpf, organization, subject, situation);
	}
}