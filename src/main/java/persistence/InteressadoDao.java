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
	
	public void salvar(Interested novoInteressado) throws DatabaseException;
	public void atualizar(Interested interessadoModificado) throws DatabaseException;
	public void deletar(Interested interessado) throws DatabaseException;
	public Interested pegarPeloId(Long id) throws DatabaseException;
	public Interested pegarPeloCpf(String cpf) throws DatabaseException;
	public boolean contem(Interested interessado) throws DatabaseException;
	public List<Interested> pegarTodos() throws DatabaseException;
	public List<Interested> burcarPeloNome(String nome);
	

}
