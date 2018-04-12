/**
 * 
 */
package persistence;

import java.util.List;

import business.model.Interested;

/**
 * @author clah
 * @since 05/04/2018
 */
public interface InteressadoDao {
	
	public void salvar(Interested novoInteressado);
	public void atualizar(Interested interessadoModificado);
	public void deletar(Interested interessado);
	public Interested pegarPeloId(Long id);
	public Interested pegarPeloCpf(String cpf) throws RuntimeException;
	public boolean contem(Interested interessado);
	public List<Interested> pegarTodos();
	public List<Interested> burcarPeloNome(String nome);
	

}
