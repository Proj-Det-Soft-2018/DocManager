package business.service;

import java.util.ArrayList;
import java.util.List;

import persistence.DatabaseException;

public abstract class Observable {

	List<Observer> observers = new ArrayList<>();
	
	public void attach(Observer obs) {
		this.observers.add(obs);
	}
	
	public void dettach(Observer obs) {
		this.observers.remove(obs);
	}
	
	public void notifyObservers () {
		this.observers.forEach(t -> {
			try {
				t.update();
			} catch (ValidationException e) {
				// TODO ANALISAR ESSE TRY-CATCH
				e.printStackTrace();
			} catch (DatabaseException e) {
				// TODO ANALISAR NOVO CATCH
				e.printStackTrace();
			}
		});
	}
}
