package business.service;

import java.util.ArrayList;
import java.util.List;

import business.model.HealthOrganization;
import business.model.HealthSituation;
import business.model.HealthSubject;
import business.model.Situation;

public class HealthListService extends ListService {
		
	private static HealthListService instance = new HealthListService();
	
	public static HealthListService getInstance() {
		return instance;
	}

	private HealthListService() {
        super( HealthOrganization.getAll(),
                HealthSubject.getAll(), 
                HealthSituation.getAll());
    }

    @Override
	protected List<String> reorganizeByCurrentSituation(Situation current) {
		List<String> list = new ArrayList<>();
		
		if(current == null) {
		    list.add(super.subjectsDescritionList.get(0));
			list.add(HealthSituation.ANALISE.getDescription());
			list.add(HealthSituation.AGUARDANDOPERITO.getDescription());
			return list;
		}

		if(current == HealthSituation.AGUARDANDOPERITO) {
			list.add(HealthSituation.ANALISE.getDescription());
			list.add(HealthSituation.AGUARDANDOPERITO.getDescription());
			list.add(HealthSituation.PRONTODESPACHAR.getDescription());
			return list;
		}
		
		if(current ==HealthSituation.ANALISE) {
			list.add(HealthSituation.ANALISE.getDescription());
			list.add(HealthSituation.AGUARDANDOPERITO.getDescription());
			list.add(HealthSituation.CONVOCAR.getDescription());
			list.add(HealthSituation.SOLICITARDOCUMENTO.getDescription());
			list.add(HealthSituation.INTERESSADOIMPEDIDO.getDescription());
			return list;
		}
		
		if(current ==HealthSituation.INTERESSADOIMPEDIDO) {
			list.add(HealthSituation.ANALISE.getDescription());
			list.add(HealthSituation.CONVOCAR.getDescription());
			return list;
		}
		
		if(current ==HealthSituation.SOLICITARDOCUMENTO) {
			list.add(HealthSituation.ANALISE.getDescription());
			list.add(HealthSituation.AGUARDANDODOCUMENTO.getDescription());
			list.add(HealthSituation.SEMEXITO1.getDescription());
			return list;
		}
		
		if(current ==HealthSituation.CONVOCAR) {
			list.add(HealthSituation.INTERESSADOIMPEDIDO.getDescription());
			list.add(HealthSituation.ANALISE.getDescription());
			list.add(HealthSituation.CONVOCADO.getDescription());
			list.add(HealthSituation.SEMEXITO1.getDescription());
			list.add(HealthSituation.AGUARDANDOEXTERNA.getDescription());
			list.add(HealthSituation.ENCAMINHADOCOVEPS.getDescription());			
			return list;
		}
		
		if(current == HealthSituation.AGUARDANDODOCUMENTO) {
			list.add(HealthSituation.SOLICITARDOCUMENTO.getDescription());
			list.add(HealthSituation.CONVOCADO.getDescription());
			list.add(HealthSituation.SEMEXITO1.getDescription());
			list.add(HealthSituation.AGUARDANDOPERITO.getDescription());
			return list;
		}
		
		if(current == HealthSituation.SEMEXITO1) {
			list.add(HealthSituation.SOLICITARDOCUMENTO.getDescription());
			list.add(HealthSituation.CONVOCAR.getDescription());
			list.add(HealthSituation.CONVOCADO.getDescription());
			list.add(HealthSituation.AGUARDANDODOCUMENTO.getDescription());
			list.add(HealthSituation.SEMEXITO1.getDescription());
			list.add(HealthSituation.SEMEXITO2.getDescription());
			return list;
		}
		
		if(current == HealthSituation.SEMEXITO2) {
			list.add(HealthSituation.CONVOCADO.getDescription());
			list.add(HealthSituation.AGUARDANDODOCUMENTO.getDescription());
			list.add(HealthSituation.SEMEXITO3.getDescription());
			return list;
		}
		
		if(current == HealthSituation.SEMEXITO3) {
			list.add(HealthSituation.CONVOCADO.getDescription());
			list.add(HealthSituation.AGUARDANDODOCUMENTO.getDescription());
			list.add(HealthSituation.PRONTODESPACHAR.getDescription());
			return list;
		}
		
		if(current == HealthSituation.AGUARDANDOEXTERNA) {
			list.add(HealthSituation.CONVOCAR.getDescription());
			list.add(HealthSituation.AGUARDANDOEXTERNA.getDescription());
			list.add(HealthSituation.AGENDADAEXTERNA.getDescription());
			return list;
		}
		
		if(current == HealthSituation.AGENDADAEXTERNA) {
			list.add(HealthSituation.AGUARDANDOEXTERNA.getDescription());
			list.add(HealthSituation.AGENDADAEXTERNA.getDescription());
			list.add(HealthSituation.PRONTODESPACHAR.getDescription());
			return list;
		}
		
		if(current == HealthSituation.ENCAMINHADOCOVEPS) {
			list.add(HealthSituation.AGUARDANDOPERITO.getDescription());
			list.add(HealthSituation.ANALISE.getDescription());
			list.add(HealthSituation.PROBLEMASIAPE.getDescription());
			list.add(HealthSituation.ENCAMINHADOCOVEPS.getDescription());
			return list;
		}
		
		if(current == HealthSituation.AGUARDANDOPERITO) {
			list.add(HealthSituation.AGUARDANDODOCUMENTO.getDescription());
			list.add(HealthSituation.ENCAMINHADOCOVEPS.getDescription());
			list.add(HealthSituation.AGUARDANDOPERITO.getDescription());
			list.add(HealthSituation.ANALISE.getDescription());
			list.add(HealthSituation.AGUARDANDODOCUMENTO.getDescription());
			list.add(HealthSituation.PRONTODESPACHAR.getDescription());

			return list;
		}
		
		if(current == HealthSituation.ENCAMINHADOEQUIPEMULTI) {
			list.add(HealthSituation.AGENDADAEXTERNA.getDescription());
			list.add(HealthSituation.CONVOCADO.getDescription());
			list.add(HealthSituation.ENCAMINHADOEQUIPEMULTI.getDescription());
			list.add(HealthSituation.AGUARDANDOPERITO.getDescription());

			return list;
		}
		
		if(current == HealthSituation.CONVOCADO) {
			list.add(HealthSituation.AGUARDANDODOCUMENTO.getDescription());
			list.add(HealthSituation.CONVOCAR.getDescription());
			list.add(HealthSituation.SEMEXITO1.getDescription());
			list.add(HealthSituation.SEMEXITO2.getDescription());
			list.add(HealthSituation.SEMEXITO3.getDescription());
			list.add(HealthSituation.ENCAMINHADOEQUIPEMULTI.getDescription());
			list.add(HealthSituation.PRONTODESPACHAR.getDescription());
			list.add(HealthSituation.PROBLEMASIAPE.getDescription());

			return list;
		}
		
		if(current == HealthSituation.PROBLEMASIAPE) {
			list.add(HealthSituation.CONVOCADO.getDescription());
			list.add(HealthSituation.PROBLEMASIAPE.getDescription());
			list.add(HealthSituation.PRONTODESPACHAR.getDescription());
			list.add(HealthSituation.ENCAMINHADOCOVEPS.getDescription());
			return list;
		}
		
		if(current == HealthSituation.PRONTODESPACHAR) {
			list.add(HealthSituation.AGENDADAEXTERNA.getDescription());
			list.add(HealthSituation.CONVOCADO.getDescription());
			list.add(HealthSituation.AGUARDANDOPERITO.getDescription());
			list.add(HealthSituation.PRONTODESPACHAR.getDescription());
			list.add(HealthSituation.CONCLUIDO.getDescription());

			return list;
		}
		
		if(current == HealthSituation.CONCLUIDO) {
			list.add(HealthSituation.PRONTODESPACHAR.getDescription());
			list.add(HealthSituation.CONCLUIDO.getDescription());
			
			return list;
		}

		return super.situationsDescritionList;
	}

}
