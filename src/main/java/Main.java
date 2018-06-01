import java.net.URL;

import business.service.ConcreteInterestedService;
import business.service.ConcreteHealthListService;
import business.service.ConcreteProcessService;
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
		URL fxmlUrl = this.getClass().getResource("/visions/tela_principal.fxml");
		
		ControllerFactory hcf = new HealthControllerFactory(
				ConcreteProcessService.getInstance(),
				ConcreteInterestedService.getInstance(),
				ConcreteHealthListService.getInstance());
		
		MainScreenCtrl.showMainScreen(primaryStage, hcf.createMainScreenCtrl());
	}
}