package presentation;

import business.service.InterestedService;
import business.service.ListService;
import business.service.ProcessService;
import business.service.StatisticService;

public abstract class ControllerFactory {

	ProcessService processService;
	InterestedService interestedService;
	ListService listService;
	StatisticService statisticService;

	public ControllerFactory(ProcessService processService, InterestedService interestedService,
			ListService listService, StatisticService statisticService) {
		this.processService = processService;
		this.interestedService = interestedService;
		this.listService = listService;
		this.statisticService = statisticService;
	}
	
	public PdfViewerCtrl createPdfViewerCtrl() {
		return new PdfViewerCtrl(processService);
	}
	
	public DeleteDialogCtrl createDeleteDialogCtrl() {
		return new DeleteDialogCtrl(processService);
	}
	
	public StatisticsScreenCtrl createStatisticsScreenCtrl() {
		return new StatisticsScreenCtrl(statisticService);
	}
	
	public abstract MainScreenCtrl createMainScreenCtrl();
	
	public abstract InterestedEditCtrl createInterestedEditCtrl();
}
