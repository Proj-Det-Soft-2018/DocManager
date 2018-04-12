package presentation;

import java.net.URL;
import java.util.ResourceBundle;

import business.model.Interested;
import business.service.ConcreteInterestedService;
import business.service.InterestedService;
import business.service.ValidationException;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import presentation.utils.widget.MaskedContactTextField;
import presentation.utils.widget.MaskedTextField;

/**
 * @author hugotho
 * 
 */
public class ControleDialogInteressado implements Initializable {
	
	private static final String MASK_CPF = "###.###.###-##";
	
	private InterestedService interestedService;
	private Interested interessadoOriginal;
	
	String cpf;
	
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
		interestedService = ConcreteInterestedService.getInstance();
		interessadoOriginal = null;
	}
	
	public void setCpfOnForm(String cpf) {
		this.cpf = cpf;
		MaskedTextField maskedCpf = new MaskedTextField(MASK_CPF);
		maskedCpf.setPlainText(cpf);
		lblTxtCpf.setText(maskedCpf.getText());
	}
	
	public void populeForm(Interested interessadoOriginal) {
		raiz.getChildren().remove(this.lblAlerta);
		this.interessadoOriginal = interessadoOriginal;
		cpf = interessadoOriginal.getCpf();
		
		MaskedTextField maskedCpf = new MaskedTextField(MASK_CPF);
		maskedCpf.setPlainText(cpf);
		this.lblTxtCpf.setText(maskedCpf.getText());
		
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
		Interested interessado = new Interested();
		boolean failure = false;
		StringBuilder failureMsg = new StringBuilder();
		
		try {
			interessado.setCpf(cpf);
		} catch (ValidationException ve) {
			failure = true;
			failureMsg.append(ve.getMessage());
		}
		
		try {
			interessado.setNome(this.txtNome.getText());
		} catch (ValidationException ve) {
			failure = true;
			if (failureMsg.length() != 0) {
				failureMsg.append("\n\n");
			}
			failureMsg.append(ve.getMessage());
		}
		
		try {
			interessado.setContato(this.txtContato.plainTextProperty().getValue());
		} catch (ValidationException ve) {
			failure = true;
			if (failureMsg.length() != 0) {
				failureMsg.append("\n\n");
			}
			failureMsg.append(ve.getMessage());
		}
		
		if (failure) {
			failureMsg.append("\n\n");
			Alert alert = new Alert(AlertType.ERROR, failureMsg.toString());
			alert.getDialogPane().getChildren().stream().filter(node -> node instanceof Label).forEach(
					node -> {
						((Label)node).setMinHeight(Region.USE_PREF_SIZE);
						((Label)node).setTextFill(Color.RED);
					});
			alert.setHeaderText(null);
			alert.setGraphic(null);
	        alert.initOwner(raiz.getScene().getWindow());

	        alert.showAndWait();
		}
		
		else {
			if(interessadoOriginal == null) {
				interestedService.save(interessado);
			} else {
				interessado.setId(interessadoOriginal.getId());
				interestedService.update(interessado);
			}
			this.fecharJanela();
		}
	}
}
