package negocio.dominio;

import java.util.ArrayList;
import java.util.List;

public enum Situacao {
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
		return listaSituacoes;
	}
	
	public String getStatus() {
		return this.status;
	}
	
	public static Situacao getSituacaoPorId(int id) throws Exception{
		if(id == 0) {
			throw new Exception("USUARIO DEVE ESCOLHER UMA SITUACAO");
		}
		else {
			return Situacao.values()[id-1];
		}
	}
}
