package business.service;

import business.model.Organization;
import business.model.Situation;
import business.model.Subject;
import java.util.ArrayList;
import java.util.List;

/**
 * Classe que concretiza a interface ListService, responsável por gerenciar serviços referente a
 * listas de organizações, situações e assuntos.
 * 
 * @author clah - clahzita@gmail.com
 * @since 01.06.2018
 *
 */
public class ConcreteListService implements ListService {


  private static final String DEFAULT_CHOICEBOX_START = "-- SELECIONE --";

  protected List<String> organizationsInitialsList;

  protected List<String> organizationsExtendedList;

  protected List<String> subjectsDescritionList;

  protected List<String> subjectsShortDescritionList;

  protected List<String> situationsDescritionList;

  /**
   * Constrói uma instância de ConcreteListService em que iniciliaza listas de organizações,
   * situações e assuntos.
   * 
   * @param organizationsList Lista com todas as organizações.
   * @param subjectsList Lista com todos os assuntos.
   * @param situationsList Lista com todas as situações.
   */
  public ConcreteListService(List<Organization> organizationsList, List<Subject> subjectsList,
      List<Situation> situationsList) {
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
      organizationsExtendedList
          .add(organization.getInitials() + " - " + organization.getFullName());
    });

    subjectsList.forEach(subject -> {
      subjectsDescritionList.add(subject.getDescription());
      subjectsShortDescritionList.add(subject.getShortDescription());
    });

    situationsList.forEach(situation -> situationsDescritionList.add(situation.getDescription()));
  }

  @Override
  public List<String> getOrganizationsList() {
    return organizationsExtendedList;
  }

  @Override
  public String getOrganizationInitialsById(int id) {
    return organizationsInitialsList.get(id);
  }

  @Override
  public List<String> getSubjectsDescritionList() {
    return subjectsDescritionList;
  }

  @Override
  public String getSujectShortDescritionById(int id) {
    return subjectsShortDescritionList.get(id);
  }

  @Override
  public List<String> getSituationsDescritionList() {
    return situationsDescritionList;
  }

  @Override
  public String getSituationDescritionById(int id) {
    return situationsDescritionList.get(id);
  }

  @Override
  public List<String> getSituationsListByCurrentSituation(Situation currentSituation) {

    List<String> situationsSubList = new ArrayList<>();
    List<Situation> linkedSituations = currentSituation.getlinkedNodes();
    linkedSituations.forEach(situation -> {
      String description = situation.getDescription();
      if (description != null) {
        situationsSubList.add(situation.getDescription());
      } else {
        situationsSubList.add(DEFAULT_CHOICEBOX_START);
      }
    });

    return situationsSubList;
  }
}
