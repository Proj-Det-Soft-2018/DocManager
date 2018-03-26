package negocio.dominio;

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
	
	public String getNome() {
		return this.nome;
	}
	
}
