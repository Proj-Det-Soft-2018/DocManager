package business.service;

import java.util.ArrayList;
import java.util.List;

import business.model.Situation;

public class ConcreteHealthListService extends ListServiceAbstract {
		
	private static ConcreteHealthListService instance = new ConcreteHealthListService();
	
	public static ConcreteHealthListService getInstance() {
		return instance;
	}

	@Override
	protected List<String> reorganizeByCurrentSituation(Situation current) {
		List<String> list = new ArrayList<>();
		
		if(current == null) {
			list.add(Situation.ANALISE.getStatus());
			list.add(Situation.AGUARDANDOPERITO.getStatus());
			return list;
		}

		if(current == Situation.AGUARDANDOPERITO) {
			list.add(Situation.ANALISE.getStatus());
			list.add(Situation.AGUARDANDOPERITO.getStatus());
			list.add(Situation.PRONTODESPACHAR.getStatus());
			return list;
		}
		
		if(current ==Situation.ANALISE) {
			list.add(Situation.ANALISE.getStatus());
			list.add(Situation.AGUARDANDOPERITO.getStatus());
			list.add(Situation.CONVOCAR.getStatus());
			list.add(Situation.SOLICITARDOCUMENTO.getStatus());
			list.add(Situation.INTERESSADOIMPEDIDO.getStatus());
			return list;
		}
		
		if(current ==Situation.INTERESSADOIMPEDIDO) {
			list.add(Situation.ANALISE.getStatus());
			list.add(Situation.CONVOCAR.getStatus());
			return list;
		}
		
		if(current ==Situation.SOLICITARDOCUMENTO) {
			list.add(Situation.ANALISE.getStatus());
			list.add(Situation.AGUARDANDODOCUMENTO.getStatus());
			list.add(Situation.SEMEXITO1.getStatus());
			return list;
		}
		
		if(current ==Situation.CONVOCAR) {
			list.add(Situation.INTERESSADOIMPEDIDO.getStatus());
			list.add(Situation.ANALISE.getStatus());
			list.add(Situation.CONVOCADO.getStatus());
			list.add(Situation.SEMEXITO1.getStatus());
			list.add(Situation.AGUARDANDOEXTERNA.getStatus());
			list.add(Situation.ENCAMINHADOCOVEPS.getStatus());			
			return list;
		}
		
		if(current == Situation.AGUARDANDODOCUMENTO) {
			list.add(Situation.SOLICITARDOCUMENTO.getStatus());
			list.add(Situation.CONVOCADO.getStatus());
			list.add(Situation.SEMEXITO1.getStatus());
			list.add(Situation.AGUARDANDOPERITO.getStatus());
			return list;
		}
		
		if(current == Situation.SEMEXITO1) {
			list.add(Situation.SOLICITARDOCUMENTO.getStatus());
			list.add(Situation.CONVOCAR.getStatus());
			list.add(Situation.CONVOCADO.getStatus());
			list.add(Situation.AGUARDANDODOCUMENTO.getStatus());
			list.add(Situation.SEMEXITO1.getStatus());
			list.add(Situation.SEMEXITO2.getStatus());
			return list;
		}
		
		if(current == Situation.SEMEXITO2) {
			list.add(Situation.CONVOCADO.getStatus());
			list.add(Situation.AGUARDANDODOCUMENTO.getStatus());
			list.add(Situation.SEMEXITO3.getStatus());
			return list;
		}
		
		if(current == Situation.SEMEXITO3) {
			list.add(Situation.CONVOCADO.getStatus());
			list.add(Situation.AGUARDANDODOCUMENTO.getStatus());
			list.add(Situation.PRONTODESPACHAR.getStatus());
			return list;
		}
		
		if(current == Situation.AGUARDANDOEXTERNA) {
			list.add(Situation.CONVOCAR.getStatus());
			list.add(Situation.AGUARDANDOEXTERNA.getStatus());
			list.add(Situation.AGENDADAEXTERNA.getStatus());
			return list;
		}
		
		if(current == Situation.AGENDADAEXTERNA) {
			list.add(Situation.AGUARDANDOEXTERNA.getStatus());
			list.add(Situation.AGENDADAEXTERNA.getStatus());
			list.add(Situation.PRONTODESPACHAR.getStatus());
			return list;
		}
		
		if(current == Situation.ENCAMINHADOCOVEPS) {
			list.add(Situation.AGUARDANDOPERITO.getStatus());
			list.add(Situation.ANALISE.getStatus());
			list.add(Situation.PROBLEMASIAPE.getStatus());
			list.add(Situation.ENCAMINHADOCOVEPS.getStatus());
			return list;
		}
		
		if(current == Situation.AGUARDANDOPERITO) {
			list.add(Situation.AGUARDANDODOCUMENTO.getStatus());
			list.add(Situation.ENCAMINHADOCOVEPS.getStatus());
			list.add(Situation.AGUARDANDOPERITO.getStatus());
			list.add(Situation.ANALISE.getStatus());
			list.add(Situation.AGUARDANDODOCUMENTO.getStatus());
			list.add(Situation.PRONTODESPACHAR.getStatus());

			return list;
		}
		
		if(current == Situation.ENCAMINHADOEQUIPEMULTI) {
			list.add(Situation.AGENDADAEXTERNA.getStatus());
			list.add(Situation.CONVOCADO.getStatus());
			list.add(Situation.ENCAMINHADOEQUIPEMULTI.getStatus());
			list.add(Situation.AGUARDANDOPERITO.getStatus());

			return list;
		}
		
		if(current == Situation.CONVOCADO) {
			list.add(Situation.AGUARDANDODOCUMENTO.getStatus());
			list.add(Situation.CONVOCAR.getStatus());
			list.add(Situation.SEMEXITO1.getStatus());
			list.add(Situation.SEMEXITO2.getStatus());
			list.add(Situation.SEMEXITO3.getStatus());
			list.add(Situation.ENCAMINHADOEQUIPEMULTI.getStatus());
			list.add(Situation.PRONTODESPACHAR.getStatus());
			list.add(Situation.PROBLEMASIAPE.getStatus());

			return list;
		}
		
		if(current == Situation.PROBLEMASIAPE) {
			list.add(Situation.CONVOCADO.getStatus());
			list.add(Situation.PROBLEMASIAPE.getStatus());
			list.add(Situation.PRONTODESPACHAR.getStatus());
			list.add(Situation.ENCAMINHADOCOVEPS.getStatus());
			return list;
		}
		
		if(current == Situation.PRONTODESPACHAR) {
			list.add(Situation.AGENDADAEXTERNA.getStatus());
			list.add(Situation.CONVOCADO.getStatus());
			list.add(Situation.AGUARDANDOPERITO.getStatus());
			list.add(Situation.PRONTODESPACHAR.getStatus());
			list.add(Situation.CONCLUIDO.getStatus());

			return list;
		}
		
		if(current == Situation.CONCLUIDO) {
			list.add(Situation.PRONTODESPACHAR.getStatus());
			list.add(Situation.CONCLUIDO.getStatus());
			
			return list;
		}

		
		return super.situationsList;
	}

}
