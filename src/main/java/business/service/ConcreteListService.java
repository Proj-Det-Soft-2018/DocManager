package business.service;

import java.util.ArrayList;
import java.util.List;

import business.model.Organization;
import business.model.Situation;
import business.model.Subject;

/**
 * @author clah - clahzita@gmail.com
 * @since 06.01.2018
 *
 */
public class ConcreteListService implements ListService {


  private static final String DEFAULT_CHOICEBOX_START = "-- SELECIONE --";

  protected List<String> organizationsInitialsList;
  protected List<String> organizationsExtendedList;
  protected List<String> subjectsDescritionList;
  protected List<String> subjectsShortDescritionList;
  protected List<String> situationsDescritionList;

  public ConcreteListService(List<Organization> organizationsList,
      List<Subject> subjectsList, List<Situation> situationsList) {
    organizationsInitialsList = new ArrayList<>();
    organizationsExtendedList = new ArrayList<>();
    subjectsDescritionList = new ArrayList<>();
    subjectsShortDescritionList = new ArrayList<>();
    situationsDescritionList = new ArrayList<>();

    organizationsInitialsList.add("");
    organizationsExtendedList.add(DEFAULT_CHOICEBOX_START);
    subjectsDescritionList.add(DEFAULT_CHOICEBOX_START);
    subjectsShortDescritionList.add("");
    situationsDescritionList.add(DEFAULT_CHOICEBOX_START);

    organizationsList.forEach(organization -> {
      organizationsInitialsList.add(organization.getInitials());
      organizationsExtendedList.add(organization.getInitials() + " - " + organization.getFullName());
    });

    subjectsList.forEach(subject -> {
      subjectsDescritionList.add(subject.getDescription());
      subjectsShortDescritionList.add(subject.getShortDescription());
    });

    situationsList.forEach(situation -> situationsDescritionList.add(situation.getDescription()));
  }

  public List<String> getOrganizationsList(){
    return organizationsExtendedList;
  }

  public String getOrganizationInitialsById(int id){
    return organizationsInitialsList.get(id);
  }

  public List<String> getSubjectsDescritionList(){
    return subjectsDescritionList;
  }

  public String getSujectShortDescritionById(int id){
    return subjectsShortDescritionList.get(id);
  }

  public List<String> getSituationsDescritionList(){
    return situationsDescritionList;
  }

  public String getSituationDescritionById(int id){
    return situationsDescritionList.get(id);
  }

  public List<String> getSituationsListByCurrentSituation(Situation currentSituation){

    List<String> situationsSubList = new ArrayList<>();
    List<Situation> linkedSituations = currentSituation.getlinkedNodes();
    linkedSituations.forEach(situation -> {
          String description = situation.getDescription();
          if(description != null) {
            situationsSubList.add(situation.getDescription());
          } else {
            situationsSubList.add(DEFAULT_CHOICEBOX_START);
          }
        });

    return situationsSubList;
  }
}