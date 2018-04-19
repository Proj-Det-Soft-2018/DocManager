package business.service;

import java.util.List;

import business.model.Subject;
import business.model.Organization;
import business.model.Situation;

public class ConcreteListService implements ListService {
	
	private static final String DEFAULT_CHOICEBOX_START = "-- SELECIONE --";
	
	private static ConcreteListService instance = new ConcreteListService();
	
	private List<String> organizationsList;
	private List<String> subjectsList;
	private List<String> situationsList;
	
	private ConcreteListService() {
		organizationsList = Organization.getOrgaos();
		organizationsList.add(0, DEFAULT_CHOICEBOX_START);
		
		subjectsList = Subject.getAssuntos();
		subjectsList.add(0, DEFAULT_CHOICEBOX_START);
		
		situationsList = Situation.getSituacoes();
		situationsList.add(0, DEFAULT_CHOICEBOX_START);
	}
	
	public static ConcreteListService getInstance() {
		return instance;
	}

	@Override
	public List<String> getOrganizationsList() {
		return organizationsList;
	}

	@Override
	public List<String> getSubjectsList() {
		return subjectsList;
	}

	@Override
	public List<String> getSituationsList() {
		return situationsList;
	}

}
