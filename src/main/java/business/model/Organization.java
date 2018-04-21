package business.model;

import java.util.ArrayList;
import java.util.List;

public enum Organization {
	
	NULL("- Inválido -"),
	UFRN("Universidade Federal do Rio Grande do Norte"),
	DPF("Departamento de Polícia Federal"),
	MTE("Ministério Do Trabalho e Emprego"),
	DPRF("Departamento de Policia Rodoviaria Federal"),
	FUNAI("Fundação Nacional do Índio"),
	MAPA("Ministério da Agricultura, Pecuaria e Abastecimento"),
	MF("Ministério da Fazenda"),
	MJ("Ministério da Justiça"),
	MPOG("Ministério do Planejamento Desenvolvimento e Gestão"),
	IPHAN("Instituto do Patrimônio Histórico e Artístico Nacional"),
	UFERSA("Universidade Federal Uural do Semi-Árido"),
	FUNASA("Fundação Nacional de Saúde"),
	DNPM("Departamento Nacional de Produção Mineral"),
	ANVISA("Agência Nacional de Vigilância Sanitária"),
	DPU("Defensoria Pública da União"),
	DNIT("Departamento Nacional de Infraestrutura de Transportes"),
	AGU("Advocacia-Geral da União"),
	MCTI("Ministério da Ciência, Tecnologia, Inovações e Comunicações"),
	IBAMA("Instituto Brasileiro do Meio Ambiente e dos Recursos Naturais Renováveis"),
	INCRA("Instituto Nacional de Colonização e Reforma Agrária"),
	DNOCS("Departamento Nacional de Obras Contra as Secas"),
	ICMBIO("Instituto Chico Mendes de Conservação da Biodiversidade"),
	IBGE("Instituto Brasileiro de Geografia e Estatística"),
	CGU("Ministério da Transparência e Controladoria-Geral da União");
	
	private String fullName;
	
	Organization(String name) {
		this.fullName = name;
	}
	
	public String getFullName() {
		return fullName;
	}


	public static List<String> getOrganization() {
		List<String> organizationList = new ArrayList<>();
		for(Organization organization : Organization.values()) {
			organizationList.add(organization.name() + " - " + organization.fullName);
		}
		organizationList.remove(0);
		return organizationList;
	}
	
	public static Organization getOrganizationById(int id){
		return Organization.values()[id];
	}
	
}
