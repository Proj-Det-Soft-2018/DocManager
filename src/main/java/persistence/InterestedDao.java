/**
 * 
 */
package persistence;

import java.util.List;

import business.model.Interested;
import persistence.exception.DatabaseException;

/**
 * @author clah
 * @since 05/04/2018
 */
public interface InteressadoDao {
	
	public void save(Interested newInterested) throws DatabaseException;
	public void update(Interested modifiedInterested) throws DatabaseException;
	public void delete(Interested interested) throws DatabaseException;
	public Interested getById(Long id) throws DatabaseException;
	public Interested getByCpf(String cpf) throws DatabaseException;
	public boolean contains(Interested interested) throws DatabaseException;
	public List<Interested> getAll() throws DatabaseException;
	

}
