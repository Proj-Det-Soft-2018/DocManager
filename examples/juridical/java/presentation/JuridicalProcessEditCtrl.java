package presentation;

import java.net.URL;

import org.apache.log4j.Logger;

import business.model.Interested;
import business.model.Process;
import business.model.Search;
import business.service.InterestedService;
import business.service.ListService;
import business.service.ProcessService;
import health.presentation.HealthProcessEditCtrl;

public class JuridicalProcessEditCtrl extends ProcessEditCtrl {
    private static final Logger LOGGER = Logger.getLogger(JuridicalProcessEditCtrl.class);

	protected JuridicalProcessEditCtrl(ListService listService, ProcessService processService,
            InterestedService interestedService, ControllerFactory controllerFactory) {
		super(processService, interestedService, controllerFactory, LOGGER);
	}

	@Override
	protected void initializeForm() {
		// TODO Auto-generated method stub

	}

	@Override
	protected Interested createInterested() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected Search mountSearch() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected void fillInterestedField() {
		// TODO Auto-generated method stub

	}

	@Override
	protected void clearInterestedField() {
		// TODO Auto-generated method stub

	}

	@Override
	protected Process mountProcess() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public URL getFxmlPath() {
		// TODO Auto-generated method stub
		return null;
	}

}
