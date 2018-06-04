/**
 * 
 */
package persistence;

import java.util.List;

import business.model.HealthInterested;
import business.model.Interested;
import business.model.Search;
import persistence.exception.DatabaseException;

/**
 * @author clah
 * @since 05/04/2018
 */
public interface InterestedDao {
	
	public void save(Interested newInterested) throws DatabaseException;
	public void update(Interested modifiedInterested) throws DatabaseException;
	public void delete(Interested interested) throws DatabaseException;
	public Interested getById(Long id) throws DatabaseException;
	public Interested search(Search searchData) throws DatabaseException;
	public boolean contains(Interested interested) throws DatabaseException;
	
	// TODO Não é usado, é interessante manter?
	public List<HealthInterested> getAll() throws DatabaseException;
	

}
