package business.service;

import java.util.List;

import business.model.Situation;

public interface ListService {
    
    List<String> getOrganizationsList();
    
    String getOrganizationInitialsById(int id);

    List<String> getSubjectsDescritionList();
    
    String getSujectShortDescritionById(int id);
    
    List<String> getSituationsDescritionList();
    
    String getSituationDescritionById(int id);

    List<String> getSituationsListByCurrentSituation(Situation currentSituation);

}
