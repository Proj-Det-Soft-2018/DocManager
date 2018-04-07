/**
 * 
 */
package negocio.servico;

import java.util.List;

import negocio.dominio.Processo;

/**
 * @author clah
 *
 */
public interface ProcessoDao{
	
	public void salvar(Processo novoProcesso);
	public void atualizar(Processo processoModificado);
	public void deletar(Processo processo);
	public Processo pegarPeloId(Long id);
	public boolean contem(Processo processo);
	public List<Processo> pegarTodos();
	public List<Processo> buscarPorNumero(String numero);
	public List<Processo> buscarPorSituacao(int situacaoId);
	public List<Processo> buscarPorNomeInteressado(String nome);
	public List<Processo> buscarPorCpfInteressado(String cpf);
	
}
