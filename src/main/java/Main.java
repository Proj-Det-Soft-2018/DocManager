import business.model.HealthOrganization;
import business.model.HealthSituation;
import business.model.HealthSubject;
import business.service.ConcreteInterestedService;
import business.service.ConcreteListService;
import business.service.ListService;
import business.service.ProcessService;
import business.service.StatisticService;
import business.service.ConcreteProcessService;
import business.service.ConcreteStatisticService;
import business.service.InterestedService;
import presentation.ControllerFactory;
import presentation.HealthControllerFactory;
import presentation.MainScreenCtrl;

import javafx.application.Application;
import javafx.stage.Stage;
import persistence.DaoFactory;
import persistence.DaoFactoryJDBC;


/**
 * @author hugotho
 * 
 */
public class Main extends Application {
	
	public static void main(String[] args) {
        launch(args);
	}
	
	@Override
	public void start(Stage primaryStage) {
	    
	    DaoFactory daoFactory = new DaoFactoryJDBC(); 
		
	    ProcessService processService = new ConcreteProcessService(daoFactory);
	    InterestedService interestedService = new ConcreteInterestedService(daoFactory);
	    StatisticService statisticService = new ConcreteStatisticService(daoFactory);
	    
	    ListService listService = new ConcreteListService(
	            HealthOrganization.getAll(),
	            HealthSubject.getAll(),
	            HealthSituation.getAll());
	    
	    ControllerFactory hcf = new HealthControllerFactory(
				processService,
				interestedService,
				listService,
				statisticService);
		
		MainScreenCtrl.showMainScreen(primaryStage, hcf.createMainScreenCtrl());
	}
}