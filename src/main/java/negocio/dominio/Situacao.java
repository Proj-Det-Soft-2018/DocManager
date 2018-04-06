package negocio.dominio;

import java.util.ArrayList;
import java.util.List;

public enum Situacao {
	
	NULL("- Inválido -"),
	ANALISE("Análise"),
	CONVOCAR("A convocar"),
	AGUARDANDODOCUMENTO("Aguardando Documento"),
	AGENDADO("Agendado"),
	CONCLUIDO("Concluido");
	
	private String status;
	
	Situacao(String status){
		this.status = status;
	}
	
	public static List<String> getSituacoes() {
		List<String> listaSituacoes = new ArrayList<String>();
		for(Situacao situacao : Situacao.values()) {
			listaSituacoes.add(situacao.status);
		}
		listaSituacoes.remove(0);
		return listaSituacoes;
	}
	
	public String getStatus() {
		return this.status;
	}
	
	public static Situacao getSituacaoPorId(int id) throws RuntimeException{
		if(id == 0) {
			throw new RuntimeException("USUARIO DEVE ESCOLHER UMA SITUACAO");
		}
		else {
			return Situacao.values()[id];
		}
	}
}
