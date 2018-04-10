package apresentacao;

import java.net.URL;
import java.util.ResourceBundle;

import org.apache.shiro.authc.AuthenticationException;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import negocio.dominio.Processo;
import negocio.fachada.FachadaArmazenamento;
import negocio.fachada.FachadaNegocio;

public class ControleDialogAdmPassword implements Initializable {

	private Processo processo;
	private FachadaArmazenamento fachada;

	@FXML
	private VBox root;

	@FXML
	private TextField txtUser;

	@FXML
	private PasswordField txtPassword;

	public void setProcesso(Processo processo) {
		this.processo = processo;
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		this.fachada = FachadaNegocio.getInstance();
		this.processo = null;
	}

	@FXML
	private void deleteProcess() {
		String user = this.txtUser.getText();
		String password = this.txtPassword.getText();

		try {
			fachada.excluir(this.processo, user, password);
			this.closeWindow();
		} catch (AuthenticationException ae) {
			Stage selfStage = (Stage)this.root.getScene().getWindow();
			selfStage.setHeight(250);
			Label alertLabel = new Label("Usuário ou Senha Incorretos!");
			alertLabel.setTextFill(Color.RED);
			this.root.getChildren().add(0, alertLabel);
		}
	}

	@FXML
	private void closeWindow() {
		Stage window = (Stage) this.root.getScene().getWindow();
		if (window != null)
			window.close();
	}
}
