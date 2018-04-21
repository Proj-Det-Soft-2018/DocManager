package presentation;

import java.net.URL;
import java.util.ResourceBundle;

import org.apache.shiro.authc.AuthenticationException;

import business.model.Process;
import business.service.ConcreteProcessService;
import business.service.ProcessService;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import persistence.exception.DatabaseException;

public class ControleDialogAdmPassword implements Initializable {

	private Process process;
	private ProcessService processService;

	@FXML
	private VBox root;

	@FXML
	private TextField txtUser;

	@FXML
	private PasswordField txtPassword;

	public void setProcesso(Process process) {
		this.process = process;
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		processService = ConcreteProcessService.getInstance();
		process = null;
	}

	@FXML
	private void deleteProcess() throws DatabaseException {
		String user = txtUser.getText();
		String password = txtPassword.getText();

		try {
			processService.delete(process, user, password);
			this.closeWindow();
		} catch (AuthenticationException ae) {
			Stage selfStage = (Stage)root.getScene().getWindow();
			selfStage.setHeight(250);
			Label alertLabel = new Label("Usuário ou Senha Incorretos!");
			alertLabel.setTextFill(Color.RED);
			this.root.getChildren().add(0, alertLabel);
		}
	}

	@FXML
	private void closeWindow() {
		Stage window = (Stage) root.getScene().getWindow();
		if (window != null)
			window.close();
	}
}
