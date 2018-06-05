package business.model;

import java.util.ArrayList;
import java.util.List;

public enum HealthSituation implements Situation {
	
	NULL("- Inválido -"),
	ANALISE("Análise"),
	CONVOCAR("A convocar"),
	AGUARDANDODOCUMENTO("Aguardando Documento(s)"),
	AGENDADO("Agendado"),
	CONCLUIDO("Concluido"),
	CONVOCADO("Convocado"),
	SOLICITARDOCUMENTO("Solicitar Documento(s)"),
	SEMEXITO1("Contato Sem Êxito - 1ª vez"),
	SEMEXITO2("Contato Sem Êxito - 2ª vez"),
	SEMEXITO3("Contato Sem Êxito - 3ª vez"),
	ENCAMINHADOCOVEPS("Encaminhado a Coordenação COVEPS"),
	ENCAMINHADOEQUIPEMULTI("Encaminhado p/ Eq. Multiprofissional"),
	AGUARDANDOPERITO("Aguardando Perito Finalizar"),
	AGUARDANDOEXTERNA("Aguardando Perícia Externa"),
	AGENDADAEXTERNA("Agendada Perícia Externa"),
	PROBLEMASIAPE("Aguardando Resolver Problema SIAPE"),
	PRONTODESPACHAR("Pronto para Despachar"),
	INTERESSADOIMPEDIDO("Interessado Impedido de Ser Periciado");
	
	private String description;
	
	HealthSituation(String description){
		this.description = description;
	}
	
	public static List<Situation> getAll() {
		List<Situation> situationList = new ArrayList<>();
		for(HealthSituation situation : HealthSituation.values()) {
			situationList.add(situation);
		}
		situationList.remove(0);
		return situationList;
	}
	
	@Override
	public String getDescription() {
		return this.description;
	}
	
	@Override
	public int getId() {
	    return ordinal();
	}
	
	public static HealthSituation getSituationById(int id){
		return HealthSituation.values()[id];
	}
}
