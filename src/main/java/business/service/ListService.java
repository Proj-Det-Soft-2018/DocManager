package business.service;

import java.util.List;

import business.model.Organization;
import business.model.Situation;
import business.model.Subject;

/**
 * @author clah - clahzita@gmail.com
 * @since 06.01.2018
 *
 */
public abstract class ListService {
	private static final String DEFAULT_CHOICEBOX_START = "-- SELECIONE --";
	
	protected List<String> organizationsList;
	protected List<String> subjectsList;
	protected List<String> situationsList;
	
	public ListService() {
		organizationsList = Organization.getOrganization();
		organizationsList.add(0, DEFAULT_CHOICEBOX_START);
		
		subjectsList = Subject.getSubjects();
		subjectsList.add(0, DEFAULT_CHOICEBOX_START);
		
		situationsList = Situation.getSituations();
		situationsList.add(0, DEFAULT_CHOICEBOX_START);
	}

	public List<String> getOrganizationsList(){
		return organizationsList;
	}

	public List<String> getSubjectsList(){
		return subjectsList;
		
	}
	
	public List<String> getSituationsList(){
		return situationsList;
	}

	public List<String> getSituationsListByCurrentSituation(Situation currentSituation){
		// TODO Ligar com a tela de edição
		return this.reorganizeByCurrentSituation(currentSituation);
	}

	protected abstract List<String> reorganizeByCurrentSituation(Situation current);
}