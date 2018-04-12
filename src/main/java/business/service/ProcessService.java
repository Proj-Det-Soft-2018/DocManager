package business.service;

import java.util.List;

import business.model.Process;

public interface ProcessService {

	public List<Process> getList();
	
	public void save(Process process);
	
	public void update(Process process);
	
	public void delete(Process process, String admUser, String password);
	
	public List<Process> search(String number, String name, String cpf, int situation, int organization, int subject);
	
	public void attach(Observer observer);
	
	public void dettach(Observer observer);
}
