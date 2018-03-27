package negocio.servico;

import java.util.ArrayList;
import java.util.List;

public abstract class Observavel {

	List<Observador> observadores = new ArrayList<>();
	
	public void cadastrarObservador(Observador obs) {
		this.observadores.add(obs);
	}
	
	public void descadastrarObservador(Observador obs) {
		this.observadores.remove(obs);
	}
	
	public void notificarTodos () {
		this.observadores.forEach(Observador::atualizar);
	}
}
