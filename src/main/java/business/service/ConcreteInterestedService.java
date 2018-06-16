package business.service;

import business.exception.ValidationException;
import business.model.Interested;
import business.model.Search;
import persistence.DaoFactory;
import persistence.InterestedDao;
import persistence.exception.DatabaseException;
/**
 * 
 * @author Allan
 *
 */
public class ConcreteInterestedService extends Observable implements InterestedService {
	
	private InterestedDao interessadoDao;

	public ConcreteInterestedService(DaoFactory daoFactory) {
		interessadoDao = daoFactory.getInterestedDao();
	}

	@Override
	public void save(Interested interessado) throws DatabaseException {
		interessadoDao.save(interessado);
		notifyObservers();
	}

	@Override
	public void update(Interested interessado) throws DatabaseException {
		interessadoDao.update(interessado);
		notifyObservers();
	}

	@Override
	public void delete(Interested interessado) throws DatabaseException {
		interessadoDao.delete(interessado);
		notifyObservers();
	}

	@Override
	public Interested search(Search searchData) throws ValidationException, DatabaseException {
	    searchData.validate();
		return interessadoDao.search(searchData);
	} 
}
