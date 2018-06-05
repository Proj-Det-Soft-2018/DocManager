package presentation;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import org.apache.log4j.Logger;

import business.exception.ValidationException;
import business.model.Interested;
import business.service.InterestedService;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;
import persistence.exception.DatabaseException;
import presentation.utils.StringConstants;
import presentation.utils.widget.ExceptionAlert;

/**
 * @author hugotho
 * 
 */
public abstract class InterestedEditCtrl implements Initializable{
	
	private Logger logger;
	
	private InterestedService interestedService;
	protected Interested interested;
	
	@FXML
	protected Pane root;
	
	public static void showIntestedEditScreen(Window ownerWindow, InterestedEditCtrl controller, Interested interested) {
		try {
			FXMLLoader loader = new FXMLLoader(controller.getFxmlPath());
			controller.setInterested(interested);
			loader.setController(controller);
			Parent rootParent = loader.load();

			Stage interestedEditScreen = new Stage();
			interestedEditScreen.initModality(Modality.WINDOW_MODAL);
			interestedEditScreen.initOwner(ownerWindow);
			if (interested.getId() == null) {
				interestedEditScreen.setTitle(StringConstants.TITLE_CREATE_INTERESTED_SCREEN.getText());
			} else {
				interestedEditScreen.setTitle(StringConstants.TITLE_EDIT_INTERESTED_SCREEN.getText());
			}

			interestedEditScreen.setScene(controller.dimensionScene(rootParent));
			interestedEditScreen.show();
		} catch (IOException e) {
			ExceptionAlert.show("ERRO! Contate o administrador do sistema.", ownerWindow);
			Logger.getLogger(InterestedEditCtrl.class).error(e.getMessage(), e);
		}
	}

	protected InterestedEditCtrl(InterestedService interestedService, Logger logger) {
		this.interestedService = interestedService;
		this.logger = logger;
	}
	
	private void setInterested(Interested interested) {
		this.interested = interested;
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		populeForm();
	}
	
	@FXML
	private void closeWindow() {
		Stage janela = (Stage) root.getScene().getWindow();
		if (janela != null)
			janela.close();
	}

	@FXML
	private void save() {
		Interested editedIntested = mountInterested();
		
		try {
			editedIntested.validate();
			
			if(interested.getId() == null) {
					interestedService.save(editedIntested);
				} else {
					editedIntested.setId(interested.getId());
					interestedService.update(editedIntested);
				}
			this.closeWindow();
		}
		catch (ValidationException e) {
			ExceptionAlert.show(e.getMessage(), root.getScene().getWindow());
		} catch (DatabaseException e) {
			ExceptionAlert.show("ERRO! Contate o administrador do sistema.", root.getScene().getWindow());
			logger.error(e.getMessage(), e);
		}
	}
	
	protected abstract void populeForm(); 
	
	protected abstract Interested mountInterested();
	
	protected abstract Scene dimensionScene(Parent rootParent);
	
	public abstract URL getFxmlPath();
}
