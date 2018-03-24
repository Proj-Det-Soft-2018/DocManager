package controller;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class ControleTelaEdicao implements Initializable {

	@FXML
	private RadioButton rbProcesso;
	
	@FXML
	private RadioButton rbOficio;
	
	@FXML
	private Label lblDocumento;
	
	@FXML
	private TextField txtNumProcesso;
	
	@FXML
	private TextField txtNomeInteressado;
	
	@FXML
	private TextField txtCpfInteressado;
	
	@FXML
	private TextField txtContatoInteressado;
	
	@FXML
	private ChoiceBox<String> cbOrgao;
	
	@FXML
	private ChoiceBox<String> cbTipoProcesso;
	
	@FXML
	private ChoiceBox<String> cbSituacao;
	
	@FXML
	private TextArea txtObservacao;
	
	@FXML
	private Button btnCancelar;
	
	@FXML
	private Button btnCadastrar;

	public void setMessage(String message) {
		txtNomeInteressado.setText(message);
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {

	}
	
	
}
