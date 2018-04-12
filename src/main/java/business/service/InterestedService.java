package business.service;

import business.model.Interested;

public interface InterestedService {
	
	public Interested searchByCpf(String cpf);
		
	public void save(Interested interested);
	
	public void update(Interested interested);
	
	public void delete(Interested interested);
	
	public void attach(Observer observer);
	
	public void dettach(Observer observer);
}
