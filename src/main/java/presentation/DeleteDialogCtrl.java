package presentation;

import java.io.IOException;
import java.net.URL;

import org.apache.log4j.Logger;
import org.apache.shiro.authc.AuthenticationException;

import persistence.exception.DatabaseException;
import presentation.utils.StringConstants;
import business.model.Process;
import business.service.ProcessService;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;

public class DeleteDialogCtrl {

	private static final URL FXML_PATH = DeleteDialogCtrl.class.getResource("/visions/delete_dialog.fxml");
	private static final Logger LOGGER = Logger.getLogger(DeleteDialogCtrl.class);
	
	private Process process;
	private ProcessService processService;

	@FXML
	private VBox root;

	@FXML
	private TextField txtUser;

	@FXML
	private PasswordField txtPassword;

	public static void showDeleteDialog(Window ownerWindow, DeleteDialogCtrl controller, Process process){
		try {
			FXMLLoader loader = new FXMLLoader(FXML_PATH);
			controller.setProcess(process);
			loader.setController(controller);
			Parent rootParent = loader.load();

			Stage deleteDialogStage = new Stage();
			deleteDialogStage.setTitle(StringConstants.TITLE_DELETE_DIALOG.getText());
			deleteDialogStage.initModality(Modality.WINDOW_MODAL);
			deleteDialogStage.initOwner(ownerWindow);
			deleteDialogStage.setScene(new Scene(rootParent, rootParent.prefWidth(-1), rootParent.prefHeight(-1)));
			
			deleteDialogStage.show();
		} catch (IOException e) {
			LOGGER.error(e.getMessage(), e);
		}
	}
	
	public DeleteDialogCtrl(ProcessService processService) {
		this.processService = processService;
	}
	
	
	private void setProcess(Process process) {
		this.process = process;
	}

	@FXML
	private void deleteProcess() {
		String user = txtUser.getText();
		String password = txtPassword.getText();

		try {
			processService.delete(process, user, password);
			this.closeWindow();
		} catch (AuthenticationException ae) {
			Stage selfStage = (Stage)root.getScene().getWindow();
			selfStage.setHeight(250);
			Label errorLabel = new Label(StringConstants.ERROR_PASSWORD.getText());
			errorLabel.setTextFill(Color.RED);
			this.root.getChildren().add(0, errorLabel);
		} catch (DatabaseException e) {
			// TODO Alert erro de banco
			LOGGER.error(e.getMessage(), e);
		}
	}

	@FXML
	private void closeWindow() {
		Stage window = (Stage) root.getScene().getWindow();
		if (window != null)
			window.close();
	}
}
