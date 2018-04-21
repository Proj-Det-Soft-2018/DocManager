package business.model;

import java.util.ArrayList;
import java.util.List;

public enum Subject {
	
	NULL("INV","- Inválido -"),
	APO("Aposentadoria","Aposentadoria por Invalidez"),
	PENSAO("Pensão","Avaliação para fins de pensão"),
	REMOCAOFAMILIA("Rem. Família","Remoção por motivo de saúde de pessoa de sua família"),
	REMOCAOPROPRIOSERVIDOR("Rem. Servidor","Remoção por motivo de saúde do servidor"),
	HORARIO_ESPECIAL_FAMILIA("Horário Esp. Família","Horário especial para servidor com familiar com deficiência"),
	HORARIO_ESPECIAL_SERVIDOR("Horário Esp. Servidor","Horário especial para servidor com deficiência"),
	CONSTATACAO_DEFICIENCIA("Constatação Deficiência","Constatação de deficiência dos candidatos aprovados em vaga de pessoa com deficiência"),
	ACIDENTE_SERVICO("Acidente em Serviço","Recomendação para tratamento de acidentados em serviço em instituição " + 
			"privada à conta de recursos públicos"),
	REVERSAO("Reversão Aposentadoria","Avaliação de servidor aposentado por invalidez para fins de reversão"),
	INTEGRALIZACAO("Integralização de Proventos","Avaliação de servidor aposentado para fins de integralização de proventos"),
	DISPONIBILIDADE("Av. Disponibilidade","Avaliação da capacidade laborativa de servidor em disponibilidade"),
	IRPF_APOSENTADORIA("IRPF Aposentadoria","Avaliação para isenção de imposto de renda sobre Aposentadoria"),
	IRPF_PENSAO("IRPF Pensao","Avaliação para isenção de imposto de renda sobre Pensão"),
	AUXILIO_PRE_ESCOLAR("Auxílio Pré Escolar","Avaliação de idade mental de dependente para concessão de auxílio pré-escolar"),
	REGIME_DOMICILIAR("Regime Domiciliar","Avaliação de Regime Domiciliar para Aluno Doente"),
	AV_DEPENDENTE("Av. Dependente","Avaliação de Dependente"),
	AV_SANIDADE_MENTAL("Av. Sanidade Mental PAD",
						"Avaliação de sanidade mental do servidor para fins de Processo Administrativo Disciplinar"),
	AV_REC_SUPERIOR("Av. por Rec. Superior","Avaliação da capacidade laborativa por recomendação superior"),
	AV_READAPTACAO("Av. Readaptação","Avaliação da capacidade laborativa para Fins de Readaptação");
	
	private String text;
	private String shortText;
	
	Subject(String shortText, String text){
		this.shortText = shortText;
		this.text = text;
	}
	
	public String getText() {
		return text;
	}
	
	public String getShortText() {
		return shortText;
	}


	public static List<String> getAssuntos() {
		List<String> listaAssuntos = new ArrayList<>();
		for(Subject assunto : Subject.values()) {
			listaAssuntos.add(assunto.text);
		}
		listaAssuntos.remove(0);
		return listaAssuntos;
	}
	
	public static Subject getAssuntoPorId(int id){
		return Subject.values()[id];
	}
	

}
