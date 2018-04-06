package negocio.dominio;

import java.util.ArrayList;
import java.util.List;

public enum Orgao {
	
	NULL("- Inválido -"),
	UFRN("Universidade Federal do Rio Grande do Norte"),
	DPF("Departamento da Polícia Federal"),
	MPE("Ministério do Trabalho e Previdência Social"),
	DPRF("Departamento da Polícia Rodoviária Federal"),
	FUNAI("Fundação Nacional do Indio");
	
	private String nomeExt;
	
	Orgao(String nome) {
		this.nomeExt = nome;
	}	
	
	public static List<String> getOrgaos() {
		List<String> listaOrgaos = new ArrayList<String>();
		for(Orgao orgao : Orgao.values()) {
			listaOrgaos.add(orgao.name() + " - " + orgao.nomeExt);
		}
		listaOrgaos.remove(0);
		return listaOrgaos;
	}
	
	public static Orgao getOrgaoPorId(int id) throws RuntimeException{
		if(id == 0) {
			throw new RuntimeException("USUARIO DEVE ESCOLHER UM ORGAO");
		}
		else {
			return Orgao.values()[id];
		}
	}
	
}
