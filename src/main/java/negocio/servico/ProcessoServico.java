/**
 * 
 */
package negocio.servico;

import java.util.List;

import org.apache.log4j.Logger;

import negocio.dominio.Processo;
import negocio.excecao.NumeroProcessoVazioException;
import negocio.fachada.FachadaNegocio;
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
			processo.validar();
			this.salvarProcesso(processo);
		}
		catch (RuntimeException e) {
			logger.error(e.getMessage(), e);
			//TODO elaborar
		}
	}
	
	public void salvarProcesso(Processo processo) {
		processoDao.salvar(processo);
		this.notificarTodos();
	}
	
	public void atualizarProcesso(Processo processo) {
		try {
			processo.validar();
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

}
