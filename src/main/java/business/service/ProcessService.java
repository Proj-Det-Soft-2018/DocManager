package business.service;

import java.util.List;

import business.exception.ValidationException;
import business.model.Process;
import business.model.Search;
import persistence.exception.DatabaseException;

public interface ProcessService {

	public List<Process> pullList() throws ValidationException, DatabaseException;
	
	public void save(Process process) throws ValidationException, DatabaseException;
	
	public void update(Process process) throws DatabaseException;
	
	public void delete(Process process, String admUser, String password) throws DatabaseException;
	
	public List<Process> search(Search searchData) throws ValidationException, DatabaseException;
	
	public byte[] getPdf(Process process);
	
	public void attach(Observer observer);
	
	public void dettach(Observer observer);
}
