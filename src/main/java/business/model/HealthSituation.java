package business.model;

import java.util.ArrayList;
import java.util.List;

public enum HealthSituation implements Situation {
	
/*     !!! NÃO MUDAR A ORDEM !!!                                  */
/* 0*/ NULL("- Inválido -", new int[]{0, 1, 13}),
/* 1*/ ANALISE("Análise", new int[]{1, 2, 3, 14}),
/* 2*/ CONVOCAR("A convocar", new int[]{1, 14, 2, 4, 6, 7, 9}),
/* 3*/ SOLICITARDOC("Solicitar Documento(s)", new int[]{1, 3, 4, 5}),
/* 4*/ SEMEXITO("Contato Sem Êxito", new int[]{2, 3, 4, 5, 6}),
/* 5*/ AGUARDANDODOC("Aguardando Documento(s)", new int[]{3, 4, 5, 6, 13}),
/* 6*/ CONVOCADO("Convocado", new int[]{2, 4, 5, 6, 9, 11}),
/* 7*/ AGUARDANDOEXTERNA("Aguardando Perícia Externa", new int[]{2, 7, 8}),
/* 8*/ AGENDADAEXTERNA("Agendada Perícia Externa", new int[]{7, 8, 9, 10}),
/* 9*/ ENCEQUIPEMULTI("Encaminhado p/ Eq. Multiprofissional", new int[]{6, 8, 9, 13}),
/*10*/ PRONTODESPACHAR("Pronto para Despachar", new int[]{6, 8, 11, 13, 10, 15}),
/*11*/ PROBLEMASIAPE("Aguardando Resolver Problema SIAPE", new int[]{6, 11, 10, 12}),
/*12*/ ENCAMINHADOCOVEPS("Encaminhado a Coordenação COVEPS", new int[]{1, 11, 12, 13}),
/*13*/ AGUARDANDOPERITO("Aguardando Perito Finalizar", new int[]{5, 12, 9, 13, 10}),
/*14*/ INTERIMPEDIDO("Interessado Impedido de Ser Periciado", new int[]{1, 14, 2}),
/*15*/ CONCLUIDO("Concluido", new int[]{10, 15});
	
    private String description;
	
	private int[] neighborNodes;
	
	HealthSituation(String description, int[] neighborNodes){
		this.description = description;
		this.neighborNodes = neighborNodes;
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
		return description;
	}
	
	@Override
	public int getId() {
	    return ordinal();
	}
	
	@Override
    public int[] getNeighborhoodIndexes() {
        return neighborNodes;
    }

    public static HealthSituation getSituationById(int id){
		return HealthSituation.values()[id];
	}
}
