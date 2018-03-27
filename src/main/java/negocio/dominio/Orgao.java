package negocio.dominio;

import java.util.ArrayList;
import java.util.List;

public enum OrgaoEnum {
	
	UFRN("Universidade Federal do Rio Grande do Norte"),
	DPF("Departamento da Polícia Federal"),
	MPE("Ministério do Trabalho e Previdência Social"),
	DPRF("Departamento da Polícia Rodoviária Federal"),
	FUNAI("Fundação Nacional do Indio");
	
	private String nome;
	
	OrgaoEnum(String nome) {
		this.nome = nome;
	}	
	
	public static List<String> getOrgaos() {
		List<String> listaOrgaos = new ArrayList<String>();
		for(OrgaoEnum orgao : OrgaoEnum.values()) {
			listaOrgaos.add(orgao.nome);
		}
		return listaOrgaos;
	}
	
}
