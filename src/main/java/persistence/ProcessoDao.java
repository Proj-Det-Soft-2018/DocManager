/**
 * 
 */
package persistence;

import java.util.List;

import business.model.Process;

/**
 * @author clah
 *
 */
public interface ProcessoDao{
	
	public void salvar(Process novoProcesso);
	public void atualizar(Process processoModificado);
	public void deletar(Process processo);
	public Process pegarPeloId(Long id);
	public boolean contem(Process processo);
	public List<Process> pegarTodos();
	public List<Process> buscarPorNumero(String numero);
	public List<Process> buscarPorSituacao(int situacaoId);
	public List<Process> buscarPorNomeInteressado(String nome);
	public List<Process> buscarPorCpfInteressado(String cpf);
	public List<Process> buscarPorOrgao(int orgaoId);
	public List<Process> buscarPorAssunto(int assuntoId);
	public List<Process> buscaComposta(String numero, String nomeInteressado, String cpfInteressado, int orgaoId,
			int assuntoId, int situacaoId);
	
}
