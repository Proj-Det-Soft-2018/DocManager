/**
 * 
 */
package negocio.servico;

import java.util.List;

import org.apache.log4j.Logger;

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
	
	// Singleton
	private static final ProcessoServico instance = new ProcessoServico();
	
	private ProcessoServico() {
		processoDao = new ProcessoDaoMySql();
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
	
	public void deletarProcesso(Processo processo) {
		processoDao.deletar(processo);
		this.notificarTodos();
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
	
	
	public List<Processo> burcarProcessos(String numero, String nome, String cpf) {
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
			
		} else {
			//Se todos os campos estão em branco
			return null;
		}
		
	}
}