package presentation;

import business.service.InterestedService;
import business.service.ListService;
import business.service.ProcessService;

public class HealthControllerFactory implements ControllerFactory {

	ProcessService processService;
	InterestedService interestedService;
	ListService listService;

	public HealthControllerFactory(ProcessService processService, InterestedService interestedService,
			ListService listService) {
		this.processService = processService;
		this.interestedService = interestedService;
		this.listService = listService;
	}

	@Override
	public MainScreenCtrl createMainScreenCtrl() {
		return new HealthMainScreenCtrl (processService, this);
	}
	
	@Override
	public InterestedEditCtrl createInterestedEditCtrl() {
		return new HealthInterestedEditCtrl (interestedService);
	}
}
