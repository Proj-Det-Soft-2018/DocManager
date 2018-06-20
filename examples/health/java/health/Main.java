package health;

import business.service.ConcreteInterestedService;
import business.service.ConcreteListService;
import business.service.ListService;
import business.service.ProcessService;
import business.service.StatisticService;
import business.service.XmlToPdfBinary;
import health.model.HealthOrganization;
import health.model.HealthSituation;
import health.model.HealthSubject;
import health.persistence.DaoFactoryJDBC;
import health.presentation.HealthControllerFactory;
import health.service.HealthXmlToPdfBinary;
import business.service.ConcreteProcessService;
import business.service.ConcreteStatisticService;
import business.service.InterestedService;
import presentation.ControllerFactory;
import presentation.MainScreenCtrl;

import javafx.application.Application;
import javafx.stage.Stage;
import persistence.DaoFactory;


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
		XmlToPdfBinary xmlToPdfBinary = new HealthXmlToPdfBinary();
		ProcessService processService = new ConcreteProcessService(daoFactory, xmlToPdfBinary);
		InterestedService interestedService = new ConcreteInterestedService(daoFactory);
		StatisticService statisticService = new ConcreteStatisticService(daoFactory);

		ListService listService = new ConcreteListService(
				HealthOrganization.getAll(),
				HealthSubject.getAll(),
				HealthSituation.getAll());

		ControllerFactory controllerFactory = new HealthControllerFactory(
				processService,
				interestedService,
				listService,
				statisticService);

		MainScreenCtrl.showMainScreen(primaryStage, controllerFactory.createMainScreenCtrl());
	}
}