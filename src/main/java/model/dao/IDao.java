/**
 * 
 */
package br.com.docmanager.model.dao;

import java.util.List;

/**
 * @author clah
 *
 */
public interface IDao<T> {
	
	public void salvar(T bean);
	public void atualizar(T bean);
	public void deletar(T id);
	public T getById(T id);
	public List<T> getAll();

}
