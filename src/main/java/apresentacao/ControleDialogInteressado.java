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
import utils.widget.MaskedContactTextField;
import utils.widget.MaskedTextField;

/**
 * @author hugotho
 * 
 */
public class ControleDialogInteressado implements Initializable {
	
	private static final String MASK_CPF = "###.###.###-##";
	
	private FachadaArmazenamento fachada;
	private Interessado interessadoOriginal;
	MaskedTextField maskCpf;
	
	@FXML
	private VBox raiz;
	
	@FXML
	private Label lblAlerta;
	
	@FXML
	private Label lblTxtCpf;
	
	@FXML
	private TextField txtNome;
	
	@FXML
	private MaskedContactTextField txtContato;
	
	@FXML
	private Button btnCancelar;
	
	@FXML
	private Button btnSalvar;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		this.fachada = FachadaNegocio.getInstance();
		this.interessadoOriginal = null;
		this.maskCpf = new MaskedTextField(MASK_CPF);
	}
	
	public void setCpfOnForm(String cpf) {
		this.maskCpf.setPlainText(cpf);
		this.lblTxtCpf.setText(this.maskCpf.getText());
	}
	
	public void populeForm(Interessado interessadoOriginal) {
		this.raiz.getChildren().remove(this.lblAlerta);
		this.interessadoOriginal = interessadoOriginal;
		
		this.maskCpf.setPlainText(interessadoOriginal.getCpf());
		this.lblTxtCpf.setText(this.maskCpf.getText());
		
		this.txtNome.setText(interessadoOriginal.getNome());
		this.txtContato.setContactPlainText(interessadoOriginal.getContato());
	}
	
	@FXML
	private void fecharJanela() {
		Stage janela = (Stage) this.raiz.getScene().getWindow();
		if (janela != null)
			janela.close();
	}

	@FXML
	private void salvar() {
		Interessado interessado = new Interessado();
		
		//TODO Exceções
		interessado.setCpf(this.maskCpf.plainTextProperty().getValue());
		interessado.setNome(this.txtNome.getText());
		interessado.setContato(this.txtContato.plainTextProperty().getValue());
		
		if(interessadoOriginal == null) {
			fachada.salvar(interessado);
		} else {
			interessado.setId(interessadoOriginal.getId());
			fachada.atualizar(interessado);
		}
		this.fecharJanela();
	}
}
