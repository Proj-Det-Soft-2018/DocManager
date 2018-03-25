/**
 * 
 */
package negocio.servico;

import java.util.List;

import negocio.GenericoDao;
import negocio.dominio.Processo;
import persistencia.HashProcessoDao;

/**
 * @author clah
 *@since 24/03/2018
 */
public class ProcessoServico {
	GenericoDao<Processo> processoDao = new HashProcessoDao();
	
	public void salvarProcesso(Processo processo) {
		processoDao.salvar(processo);
	}
	public void atualizarProcesso(Processo processo) {
		processoDao.atualizar(processo);
	}
	
	public void deletarProcesso(Processo processo) {
		processoDao.deletar(processo);
	}
	
	public Processo encontrarPorId(int idProcesso) {
		return processoDao.getById(idProcesso);
		
	}
	
	public boolean contem(Processo processo) {
		return processoDao.contem(processo);
	}
	
	public List<Processo> getAll(){
		return processoDao.getAll();
	}

}
