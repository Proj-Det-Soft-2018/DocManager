package business.model;

import java.util.ArrayList;
import java.util.List;

public enum Situation {
	
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
	
	private String status;
	
	Situation(String status){
		this.status = status;
	}
	
	public static List<String> getSituations() {
		List<String> situationList = new ArrayList<>();
		for(Situation situation : Situation.values()) {
			situationList.add(situation.status);
		}
		situationList.remove(0);
		return situationList;
	}
	
	public String getStatus() {
		return this.status;
	}
	
	public static Situation getSituationById(int id){
		return Situation.values()[id];
	}
}
