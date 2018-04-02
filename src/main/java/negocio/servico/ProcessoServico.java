/**
 * 
 */
package negocio.servico;

import java.util.List;

import org.apache.log4j.Logger;

import negocio.dominio.Processo;
import persistencia.ProcessoDao;

/**
 * @author clah
 *@since 24/03/2018
 */
public class ProcessoServico extends Observavel {
	
	private static Logger logger = Logger.getLogger(ProcessoServico.class);
	private static GenericoDao<Processo> processoDao = new ProcessoDao();
	
	public void criarProcesso(Processo processo) {
		try{
			processo.validar();
		}
		catch (RuntimeException e) {
			logger.error("NAO VALIDOU PROCESSO");
		}
		finally {
			this.salvarProcesso(processo);
		}
	}
	
	public void salvarProcesso(Processo processo) {
		processoDao.salvar(processo);
		this.notificarTodos();
	}
	
	public void atualizarProcesso(Processo processo) {
		try {
			processo.validar();
		}
		catch (RuntimeException e) {
			logger.error("ATUALIZACAO NAO VALIDADA");
		}
		finally {
			processoDao.atualizar(processo);
			this.notificarTodos();	
		}
	}
	
	public void deletarProcesso(Processo processo) {
		processoDao.deletar(processo);
	}
	
	public Processo encontrarPorId(String numProcesso) {
		return processoDao.getById(numProcesso);
		
	}
	
	public boolean contem(Processo processo) {
		return processoDao.contem(processo);
	}
	
	public List<Processo> getAll(){
		return processoDao.getAll();
	}

}
