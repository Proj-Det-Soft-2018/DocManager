/**
 * 
 */
package persistence;

import business.model.Interested;
import business.model.Search;
import persistence.exception.DatabaseException;

/**
 * @author clah
 * @since 05/04/2018
 */
public interface InterestedDao {
	/**
	 * Salva um Interessado no Banco de Dados
	 * @param newInterested Interessado a ser salvo no Banco de Dados
	 * @throws DatabaseException
	 */
	public void save(Interested newInterested) throws DatabaseException;
	/**
	 * Atualiza um Interessado no Banco de Dados
	 * @param modifiedInterested Interessado a ser modificado no Banco de Dados
	 * @throws DatabaseException
	 */
	public void update(Interested modifiedInterested) throws DatabaseException;
	/**
	 * Deleta um Interessado do Banco de Dados
	 * @param interested Interessado a ser deletado
	 * @throws DatabaseException
	 */
	public void delete(Interested interested) throws DatabaseException;
	/**
	 * Busca um Interessado no Banco de Dados
	 * @param searchData Dados da busca a ser realizada
	 * @return Interessado encontrado na busca
	 * @throws DatabaseException
	 */
	public Interested search(Search searchData) throws DatabaseException;
	

}
