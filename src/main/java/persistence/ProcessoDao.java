/**
 * 
 */
package persistence;

import java.util.List;

import business.model.Process;
import business.service.ValidationException;

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
	public List<Process> buscarPorSituacao(int situacaoId) throws ValidationException, DatabaseException;
	public List<Process> buscarPorNomeInteressado(String nome) throws ValidationException, DatabaseException;
	public List<Process> buscarPorCpfInteressado(String cpf) throws ValidationException, DatabaseException;
	public List<Process> buscarPorOrgao(int orgaoId) throws ValidationException, DatabaseException;
	public List<Process> buscarPorAssunto(int assuntoId) throws ValidationException, DatabaseException;
	public List<Process> buscaComposta(String numero, String nomeInteressado, String cpfInteressado, int orgaoId,
			int assuntoId, int situacaoId) throws ValidationException, DatabaseException;
	
}
