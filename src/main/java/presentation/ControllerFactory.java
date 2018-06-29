package presentation;

import business.service.InterestedService;
import business.service.ListService;
import business.service.ProcessService;
import business.service.StatisticService;

/**
 * Classe abstrata que representa a fábrica de controladores JavaFX para o framework. Como é um dos
 * pontos flexíveis do projeto, o desenvolvedor deve implementar seus métodos abstratos para obter
 * os controladores da telas Principal, de Edição de Processos, de edição de Interessados e de 
 * Busca, sendo já oferecidos os controladores das telas de visualização de PDF, confirmação de 
 * apagamento de processo e de estatísticas.
 * 
 * @author hugo
 */
public abstract class ControllerFactory {

  protected ProcessService processService;
  protected InterestedService interestedService;
  protected ListService listService;
  protected StatisticService statisticService;

  
  /**
   * Construtor da fábrica, deve ser chamado no construtor as classes que extenderão esta.
   * 
   * @param processService
   *    Serviço de processamento de processos.
   * @param interestedService
   *    Serviço de processamento de interessados.
   * @param listService
   *    Serviço para obtenção de listas de situação e demais dados.
   * @param statisticService
   *    Serviço para processamento das estatísticas do sistema.
   */
  protected ControllerFactory(ProcessService processService, InterestedService interestedService,
      ListService listService, StatisticService statisticService) {
    this.processService = processService;
    this.interestedService = interestedService;
    this.listService = listService;
    this.statisticService = statisticService;
  }

  /**
   * cria um novo controlador da tela de visualização de PDF, que é abastecido pelo serviço de pro-
   * cessamento de processos injetado na fábrica.
   * 
   * @return
   *    Novo controlador para tela de visualização de PDF.
   */
  public PdfViewerCtrl createPdfViewerCtrl() {
    return new PdfViewerCtrl(processService);
  }

  
  public DeleteDialogCtrl createDeleteDialogCtrl() {
    return new DeleteDialogCtrl(processService);
  }

  public StatisticsScreenCtrl createStatisticsScreenCtrl() {
    return new StatisticsScreenCtrl(statisticService, listService);
  }

  public abstract MainScreenCtrl createMainScreenCtrl();

  public abstract ProcessEditCtrl createProcessEditCtrl();

  public abstract InterestedEditCtrl createInterestedEditCtrl();

  public abstract SearchScreenCtrl createSearchScreenCtrl();
}
