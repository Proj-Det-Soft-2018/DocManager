/**
 * 
 */
package persistencia;

import java.util.List;

import negocio.dominio.Interessado;

/**
 * @author clah
 * @since 05/04/2018
 */
public interface InteressadoDao {
	
	public void salvar(Interessado novoInteressado);
	public void atualizar(Interessado interessadoModificado);
	public void deletar(Interessado interessado);
	public Interessado pegarPeloId(Long id);
	public Interessado pegarPeloCpf(String cpf) throws RuntimeException;
	public boolean contem(Interessado interessado);
	public List<Interessado> pegarTodos();
	public List<Interessado> burcarPeloNome();
	

}
