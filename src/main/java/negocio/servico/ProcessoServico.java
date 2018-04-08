/**
 * 
 */
package negocio.servico;

import java.util.List;

import org.apache.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.realm.text.IniRealm;
import org.apache.shiro.subject.Subject;

import negocio.dominio.Processo;
import negocio.dominio.Situacao;
import persistencia.ProcessoDao;
import persistencia.ProcessoDaoMySql;

/**
 * @author clah
 *@since 24/03/2018
 */
public class ProcessoServico extends Observavel {

	private static Logger logger = Logger.getLogger(ProcessoServico.class);
	private ProcessoDao processoDao;
	private Subject currentUser;

	// Singleton
	private static final ProcessoServico instance = new ProcessoServico();

	private ProcessoServico() {
		processoDao = new ProcessoDaoMySql();

		// Inicilização do Apache Shiro -- utiliza o resources/shiro.ini
		IniRealm iniRealm = new IniRealm("classpath:shiro.ini");
		SecurityManager secutiryManager = new DefaultSecurityManager(iniRealm);
		SecurityUtils.setSecurityManager(secutiryManager);

		currentUser = SecurityUtils.getSubject();
	}

	public static ProcessoServico getInstance() {
		return instance;
	}


	public void criarProcesso(Processo processo) {
		try{
			this.salvarProcesso(processo);
		}
		catch (RuntimeException e) {
			logger.error(e.getMessage(), e);
			//TODO elaborar
		}
	}

	public void salvarProcesso(Processo processo) {
		//Antes de salvar verificar os campos que nao podem ser nulos
		this.validarNumeroDuplicado(processo.getNumero());

		processoDao.salvar(processo);
		this.notificarTodos();
	}

	public void atualizarProcesso(Processo processo) {
		try {
			//processo.validar();
			processoDao.atualizar(processo);
			this.notificarTodos();	
		}
		catch (RuntimeException e) {
			logger.error(e.getMessage(), e);
			//TODO elaborar
		}
	}

	public void deletarProcesso(Processo processo, String admUser, String password) {

		if (!this.currentUser.isAuthenticated()) {
			UsernamePasswordToken token = new UsernamePasswordToken(admUser, password);
			token.setRememberMe(true);

			currentUser.login(token); // Joga uma AuthenticationException
		}

		if (currentUser.hasRole("admin")) {
			processoDao.deletar(processo);
			this.notificarTodos();
		}

		currentUser.logout();

	}

	public Processo encontrarPorId(Processo processo) {
		return processoDao.pegarPeloId(processo.getId());

	}

	public boolean contem(Processo processo) {
		return processoDao.contem(processo);
	}

	public List<Processo> getAll(){
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
		List<Processo> duplicados = processoDao.buscarPorNumero(numero);
		if(duplicados != null && !duplicados.isEmpty()) {
			//verifica se a situacao dos processos encontrados estao como concluido
			for (Processo processo : duplicados) {
				if(!(processo.getSituacao().ordinal()==Situacao.CONCLUIDO.ordinal()) ) {
					//throw new ProcessoDuplicadoException("Existe outro processo cadastrado com situação não concluída");
				}				
			}			
		}		
	}


	public List<Processo> burcarProcessos(String numero, String nome, String cpf,
			int situacao, int orgao, int assunto) {
		if(numero != null && !numero.isEmpty()) {
			List<Processo> lista = processoDao.buscarPorNumero(numero);
			if(lista==null) {
				//throw new ListaBuscaVazia("Não foi encontrado processos com o número especificado.");
			}
			return lista;

		} else if (nome != null && !nome.isEmpty()) {
			List<Processo> lista = processoDao.buscarPorNomeInteressado(nome);
			if(lista==null) {
				//throw new ListaBuscaVazia("Não foi encontrado processos com o nome especificado.");
			}
			return lista;

		} else if(cpf!=null && !cpf.isEmpty()){
			List<Processo> lista = processoDao.buscarPorCpfInteressado(cpf);
			if(lista==null) {
				//throw new ListaBuscaVazia("Não foi encontrado processos com o cpf especificado.");
			}
			return lista;

		} else if(situacao != 0) {
			List<Processo> lista = processoDao.buscarPorSituacao(situacao);
			if(lista==null) {
				//throw new ListaBuscaVazia("Não foi encontrado processos com a situação especificado.");
			}
			return lista;
		} else if(orgao != 0) {
			List<Processo> lista = processoDao.buscarPorOrgao(orgao);
			if(lista==null) {
				//throw new ListaBuscaVazia("Não foi encontrado processos com o orgao especificado.");
			}
			return lista;
		} else if(assunto != 0) {
			List<Processo> lista = processoDao.buscarPorAssunto(assunto);
			if(lista==null) {
				//throw new ListaBuscaVazia("Não foi encontrado processos com o assunto especificado.");
			}
			return lista;
		}
		else {
			//Se todos os campos estão em branco
			return null;
		}

	}
}