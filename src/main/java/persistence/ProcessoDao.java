/**
 * 
 */
package persistence;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import business.exception.ValidationException;
import business.model.Process;
import persistence.exception.DatabaseException;

/**
 * @author clah
 *
 */
public interface ProcessoDao{
	
	public void salvar(Process novoProcesso) throws DatabaseException;
	public void atualizar(Process processoModificado) throws DatabaseException;
	public void deletar(Process processo) throws DatabaseException;
	public Process pegarPeloId(Long id) throws ValidationException, DatabaseException;
	public boolean contem(Process processo) throws ValidationException, DatabaseException;
	public List<Process> pegarTodos() throws ValidationException, DatabaseException;
	public List<Process> buscarPorNumero(String numero) throws ValidationException, DatabaseException;
	public List<Process> buscaComposta(String numero, String nomeInteressado, String cpfInteressado, int orgaoId,
			int assuntoId, int situacaoId) throws ValidationException, DatabaseException;
	//Statistics lists
	public Map<Integer, ArrayList<Integer>> getQuantityProcessPerMonthYearList() throws DatabaseException;
	public Map<Integer, Integer> getQuantityProcessPerSituationList() throws DatabaseException;
	public Map<Integer, ArrayList<Integer>> getQuantityProcessPerMonthFromLastYearList() throws DatabaseException;
	public Map<Integer, Integer> getQuantityProcessPerOrganizationList() throws DatabaseException;
	
}
