import business.service.ConcreteInterestedService;
import business.service.ConcreteListService;
import business.service.ConcreteProcessService;
import business.service.ConcreteStatisticService;
import presentation.ControllerFactory;
import presentation.HealthControllerFactory;
import presentation.MainScreenCtrl;

import javafx.application.Application;
import javafx.stage.Stage;
import presentation.ControllerFactory;
import presentation.HealthControllerFactory;
import presentation.MainScreenCtrl;
import presentation.utils.StringConstants;

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
		
		ControllerFactory hcf = new HealthControllerFactory(
				ConcreteProcessService.getInstance(),
				ConcreteInterestedService.getInstance(),
				ConcreteListService.getInstance(),
				ConcreteStatisticService.getInstance());
		
		MainScreenCtrl.showMainScreen(primaryStage, hcf.createMainScreenCtrl());
	}
}