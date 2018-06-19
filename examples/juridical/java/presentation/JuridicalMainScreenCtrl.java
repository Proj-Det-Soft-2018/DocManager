/**
 * 
 */
package presentation;

import java.net.URL;

import org.apache.log4j.Logger;

import business.service.ProcessService;
import health.presentation.HealthMainScreenCtrl;

/**
 * @author clah
 *
 */
public class JuridicalMainScreenCtrl extends MainScreenCtrl {
	private static final Logger LOGGER = Logger.getLogger(HealthMainScreenCtrl.class);
	
	protected JuridicalMainScreenCtrl(ProcessService processService, ControllerFactory controllerFactory) {
		super(processService, controllerFactory, LOGGER);
		
	}

	/* (non-Javadoc)
	 * @see presentation.MainScreenCtrl#configureColumns()
	 */
	@Override
	protected void configureColumns() {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see presentation.MainScreenCtrl#getFxmlPath()
	 */
	@Override
	public URL getFxmlPath() {
		// TODO Auto-generated method stub
		return null;
	}

}
