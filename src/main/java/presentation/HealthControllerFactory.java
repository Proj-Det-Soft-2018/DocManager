package presentation;

import business.service.InterestedService;
import business.service.ListServiceAbstract;
import business.service.ProcessService;

public class HealthControllerFactory implements ControllerFactory {

	ProcessService processService;
	InterestedService interestedService;
	ListServiceAbstract listService;

	public HealthControllerFactory(ProcessService processService, InterestedService interestedService,
			ListServiceAbstract listService) {
		this.processService = processService;
		this.interestedService = interestedService;
		this.listService = listService;
	}

	@Override
	public MainScreenCtrl createMainScreenCtrl() {
		return new HealthMainScreenCtrl (processService, this);
	}
}
