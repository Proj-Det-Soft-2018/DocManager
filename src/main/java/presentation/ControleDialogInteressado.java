package presentation;

import java.net.URL;
import java.util.ResourceBundle;

import org.apache.log4j.Logger;

import business.exception.ValidationException;
import business.model.HealthInterested;
import business.model.Interested;
import business.service.ConcreteInterestedService;
import business.service.InterestedService;
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
import persistence.exception.DatabaseException;
import presentation.utils.widget.MaskedContactTextField;

/**
 * @author hugotho
 * 
 */
public class ControleDialogInteressado implements Initializable {
	
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
	
	private Logger logger = Logger.getLogger(ControleDialogInteressado.class);

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		interestedService = ConcreteInterestedService.getInstance();
		interessadoOriginal = null;
	}
	
	public void setCpfOnForm(String cpf) {
		this.cpf = cpf;
		lblTxtCpf.setText(cpf.replaceAll("(\\d{3})(\\d{3})(\\d{3})(\\d{2})", "$1.$2.$3-$4"));
	}
	
	public void populeForm(Interested interessadoOriginal) {
		raiz.getChildren().remove(this.lblAlerta);
		this.interessadoOriginal = interessadoOriginal;
		
		cpf = interessadoOriginal.getCpf();
		this.lblTxtCpf.setText(interessadoOriginal.getFormatedCpf());
		this.txtNome.setText(interessadoOriginal.getName());
		this.txtContato.setContactPlainText(interessadoOriginal.getContact());
	}
	
	@FXML
	private void fecharJanela() {
		Stage janela = (Stage) this.raiz.getScene().getWindow();
		if (janela != null)
			janela.close();
	}

	@FXML
	private void salvar() {
		
		Interested interessado = new HealthInterested(this.txtNome.getText(), this.cpf, this.txtContato.plainTextProperty().getValue());
		
		try {
			interessado.validate();
			
			if(interessadoOriginal == null) {
				try {
					interestedService.save(interessado);
				} catch (DatabaseException e) {
					logger.error(e.getMessage(), e);
				}
			} else {
				interessado.setId(interessadoOriginal.getId());
				try {
					interestedService.update(interessado);
				} catch (DatabaseException e) {
					logger.error(e.getMessage(), e);
				}
			}
			this.fecharJanela();
			
		}
		catch (ValidationException ve) {
			Alert alert = new Alert(AlertType.ERROR, ve.getMessage());
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
	}
}
