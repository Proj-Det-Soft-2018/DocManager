package business.service;

import business.exception.ValidationException;
import business.model.Interested;
import persistence.DaoFactoryJDBC;
import persistence.InterestedDao;
import persistence.exception.DatabaseException;
/**
 * 
 * @author Allan
 *
 */
public class ConcreteInterestedService extends Observable implements InterestedService {
	
	private InterestedDao interessadoDao;

	// Singleton
	private static final ConcreteInterestedService instance = new ConcreteInterestedService();

	private ConcreteInterestedService() {
		interessadoDao = DaoFactoryJDBC.getInterestedDao();
	}

	public static ConcreteInterestedService getInstance() {
		return instance;
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
	public Interested searchByCpf(String cpf) throws ValidationException, DatabaseException{
		if(cpf == null || cpf.length() != 11) {
			throw new ValidationException("O CPF buscado está incompleto!");
		}
		return interessadoDao.getByCpf(cpf);
	} 
}
