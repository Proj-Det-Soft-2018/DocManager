package apresentacao;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import negocio.dominio.Interessado;
import negocio.fachada.FachadaArmazenamento;
import negocio.fachada.FachadaNegocio;

/**
 * @author hugotho
 * 
 */
public class ControleDialogInteressado implements Initializable {
	
	private FachadaArmazenamento fachada;
	private Interessado interessadoOriginal;
	
	@FXML
	private VBox raiz;
	
	@FXML
	private Label lblAlerta;
	
	@FXML
	private Label lblTxtCpf;
	
	@FXML
	private TextField txtNome;
	
	@FXML
	private TextField txtContato;
	
	@FXML
	private Button btnCancelar;
	
	@FXML
	private Button btnSalvar;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		this.fachada = FachadaNegocio.getInstance();
		this.interessadoOriginal = null;
	}
	
	@FXML
	private void fecharJanela() {
		Stage janela = (Stage) this.raiz.getScene().getWindow();
		if (janela != null)
			janela.close();
	}

	@FXML
	private void salvar() {
		this.fecharJanela();
	}
}
