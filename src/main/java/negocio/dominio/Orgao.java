package negocio.dominio;

import java.util.ArrayList;
import java.util.List;

public enum Orgao {
	
	UFRN("Universidade Federal do Rio Grande do Norte"),
	DPF("Departamento da Polícia Federal"),
	MPE("Ministério do Trabalho e Previdência Social"),
	DPRF("Departamento da Polícia Rodoviária Federal"),
	FUNAI("Fundação Nacional do Indio");
	
	private String nome;
	
	Orgao(String nome) {
		this.nome = nome;
	}	
	
	public static List<String> getOrgaos() {
		List<String> listaOrgaos = new ArrayList<String>();
		for(Orgao orgao : Orgao.values()) {
			listaOrgaos.add(orgao.nome);
		}
		return listaOrgaos;
	}
	
}
