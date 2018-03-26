package apresentacao;

import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import negocio.facade.FachadaGerenciadorProcesso;

public class ControleTelaEdicao implements Initializable {
	
	private static final String PROMPT_NUM_PROCESSO = "0000000.00000000/0000-00";
	private static final String PROMPT_NUM_OFICIO = "0000/0000-Orgão";
	private static final String DEFAUT_CHOICE_TEXT = "-- SELECIONE --";
	
	private FachadaArmazenamento fachadaNegocios = new FachadaGerenciadorProcesso();
	private FachadaCaixasDeEscolha fachadaListas = (FachadaCaixasDeEscolha)fachadaNegocios;
	
	@FXML
	private VBox raiz;

	@FXML
	private RadioButton rbProcesso;

	@FXML
	private RadioButton rbOficio;

	@FXML
	private ToggleGroup tgProcessoOficio;

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
	private ChoiceBox<String> cbAssunto;

	@FXML
	private ChoiceBox<String> cbSituacao;

	@FXML
	private TextArea txtObservacao;

	@FXML
	private Button btnCancelar;

	@FXML
	private Button btnCadastrar;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// Comportamento dos radio buttons
		this.tgProcessoOficio.selectedToggleProperty().addListener(
				(valorObservavel, anterior, novo) -> alterarFormulario(novo));
		
		// Preenchimento dos ChoiceBoxes
		ObservableList<String> obsListaOrgaos = cbOrgao.getItems();
		obsListaOrgaos.add(DEFAUT_CHOICE_TEXT);
		obsListaOrgaos.addAll(fachadaListas.getListaOrgaos());
		cbOrgao.getSelectionModel().select(0);
		
		ObservableList<String> obsListaAssuntos = cbAssunto.getItems();
		obsListaAssuntos.add(DEFAUT_CHOICE_TEXT);
		obsListaAssuntos.addAll(fachadaListas.getListaAssuntos());
		cbAssunto.getSelectionModel().select(0);
		
		ObservableList<String> obsListaSituacoes = cbSituacao.getItems();
		obsListaSituacoes.add(DEFAUT_CHOICE_TEXT);
		obsListaSituacoes.addAll(fachadaListas.getListaSituacao());
		cbSituacao.getSelectionModel().select(0);
	}

	private void alterarFormulario (Toggle novo) {
		if (novo != null) {
			RadioButton radio = (RadioButton)novo;
			this.lblDocumento.setText(radio.getText());

			if(Objects.equals(radio.getText(), this.rbProcesso.getText())) {
				txtNumProcesso.setPromptText(PROMPT_NUM_PROCESSO);
			} else {
				txtNumProcesso.setPromptText(PROMPT_NUM_OFICIO);
			}
		}
	}
	
	@FXML
	private void fecharJanela() {
		Stage janela = (Stage) this.raiz.getScene().getWindow();
		if (janela != null)
			janela.close();
	}
}
