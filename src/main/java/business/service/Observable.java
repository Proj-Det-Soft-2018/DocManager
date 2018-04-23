package business.service;

import java.util.ArrayList;
import java.util.List;

public abstract class Observable {

	List<Observer> observers = new ArrayList<>();
	
	public void attach(Observer obs) {
		this.observers.add(obs);
	}
	
	public void dettach(Observer obs) {
		this.observers.remove(obs);
	}
	
	public void notifyObservers () {
		this.observers.forEach(Observer::update);
	}
}
