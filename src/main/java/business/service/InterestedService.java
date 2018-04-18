package business.service;

import business.model.Interested;
import persistence.DatabaseException;

public interface InterestedService {
	
	public Interested searchByCpf(String cpf) throws ValidationException, DatabaseException;
		
	public void save(Interested interested) throws DatabaseException;
	
	public void update(Interested interested) throws DatabaseException;
	
	public void delete(Interested interested) throws DatabaseException;
	
	public void attach(Observer observer);
	
	public void dettach(Observer observer);
}
