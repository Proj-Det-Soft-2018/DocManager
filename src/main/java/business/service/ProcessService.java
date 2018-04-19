package business.service;

import java.io.InputStream;
import java.util.List;

import business.exception.ValidationException;
import business.model.Process;
import persistence.exception.DatabaseException;

public interface ProcessService {

	public List<Process> getList() throws ValidationException, DatabaseException;
	
	public void save(Process process) throws ValidationException, DatabaseException;
	
	public void update(Process process) throws DatabaseException;
	
	public void delete(Process process, String admUser, String password) throws DatabaseException;
	
	public List<Process> search(String number, String name, String cpf, int situation, int organization, int subject) throws ValidationException, DatabaseException;
	
	public InputStream getPdf(Process process);
	
	public void attach(Observer observer);
	
	public void dettach(Observer observer);
}
