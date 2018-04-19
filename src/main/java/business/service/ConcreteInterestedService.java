package business.service;

import business.model.Interested;
import persistence.DatabaseException;
import persistence.InteressadoDao;
import persistence.InteressadoDaoMySql;
/**
 * 
 * @author Allan
 *
 */
public class ConcreteInterestedService extends Observable implements InterestedService {
	
	private InteressadoDao interessadoDao;

	// Singleton
	private static final ConcreteInterestedService instance = new ConcreteInterestedService();

	private ConcreteInterestedService() {
		interessadoDao = new InteressadoDaoMySql();
	}

	public static ConcreteInterestedService getInstance() {
		return instance;
	}

	@Override
	public void save(Interested interessado) throws DatabaseException {
		interessadoDao.salvar(interessado);
		notifyObservers();
	}

	@Override
	public void update(Interested interessado) throws DatabaseException {
		interessadoDao.atualizar(interessado);
		notifyObservers();
	}

	@Override
	public void delete(Interested interessado) throws DatabaseException {
		interessadoDao.deletar(interessado);
		notifyObservers();
	}

	@Override
	public Interested searchByCpf(String cpf) throws ValidationException, DatabaseException{
		if(cpf == null || cpf.length() != 11) {
			throw new ValidationException("CPF INVÁLIDO!", "Busca-CPF", "O CPF buscado está incompleto!");
		}
		return interessadoDao.pegarPeloCpf(cpf);
	} 
}
