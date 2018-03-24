/**
 * 
 */
package negocio;

import java.util.List;

/**
 * @author clah
 *
 */
public interface GenericoDao<T> {
	
	public void salvar(T bean);
	public void atualizar(T bean);
	public void deletar(T bean);
	public T getById(int id);
	public boolean contem(T bean);
	public List<T> getAll();

}
