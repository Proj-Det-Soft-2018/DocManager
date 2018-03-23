

import org.apache.log4j.Logger;

import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {
	
	private static final Logger logger = Logger.getLogger(Main.class);
	
	public static void main(String[] args) {
        launch(args);
	}
	
	@Override
	public void start(Stage palcoPrincipal) throws Exception {
		logger.info("Hello Logger's World!");
	}
}
