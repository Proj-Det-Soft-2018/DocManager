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
import negocio.facade.FachadaGerenciadorDocumento;

public class ControleTelaEdicao implements Initializable {
	
	private static final String PROMPT_NUM_PROCESSO = "0000000.00000000/0000-00";
	private static final String PROMPT_NUM_OFICIO = "0000/0000-Orgão";
	private static final String CHOICEBOX_TEXTO_PADRAO = "-- SELECIONE --";
	private static final String LABEL_BTN_ATUALIZAR = "Atualizar"; 
	
	private FachadaCaixasDeEscolha fachada = new FachadaGerenciadorDocumento();
	
	private Documento documento = null;
	private Boolean estaEditando = false; 
	
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
	
	public void montarFormulario(Documento documento) {
		if (documento != null) {
			this.documento = documento;
			this.estaEditando = true;
			this.btnCadastrar.setText(LABEL_BTN_ATUALIZAR);
			
			if (documento.ehOficio()) {
				this.rbOficio.setSelected(true);
			}
			this.txtNumProcesso.setText(documento.getNumDocumento());
			this.txtNomeInteressado.setText(documento.getNomeInteressado());
			this.txtCpfInteressado.setText(documento.getCpfInteressado());
			this.txtContatoInteressado.setText(documento.getContatoInteressado());
			this.cbOrgao.getSelectionModel().select(documento.getOrgaoOrigemId());
			this.cbAssunto.getSelectionModel().select(documento.getAssuntoId());
			this.cbSituacao.getSelectionModel().select(documento.getSituacaoId());
			this.txtObservacao.setText(documento.getObservacao());
		}
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// Comportamento dos radio buttons
		this.tgProcessoOficio.selectedToggleProperty().addListener(
				(valorObservavel, anterior, novo) -> alterarFormulario(novo));
		
		// Preenchimento dos ChoiceBoxes
		ObservableList<String> obsListaOrgaos = this.cbOrgao.getItems();
		obsListaOrgaos.add(CHOICEBOX_TEXTO_PADRAO);
		obsListaOrgaos.addAll(fachada.getListaOrgaos());
		this.cbOrgao.getSelectionModel().select(0);
		
		ObservableList<String> obsListaAssuntos = this.cbAssunto.getItems();
		obsListaAssuntos.add(CHOICEBOX_TEXTO_PADRAO);
		obsListaAssuntos.addAll(fachada.getListaTipoDocumento());
		this.cbAssunto.getSelectionModel().select(0);
		
		ObservableList<String> obsListaSituacoes = this.cbSituacao.getItems();
		obsListaSituacoes.add(CHOICEBOX_TEXTO_PADRAO);
		obsListaSituacoes.addAll(fachada.getListaSituacao());
		this.cbSituacao.getSelectionModel().select(0);
	}

	private void alterarFormulario (Toggle novo) {
		if (novo != null) {
			RadioButton radio = (RadioButton)novo;
			this.lblDocumento.setText(radio.getText());

			if(Objects.equals(radio.getText(), this.rbProcesso.getText())) {
				this.txtNumProcesso.setPromptText(PROMPT_NUM_PROCESSO);
			} else {
				this.txtNumProcesso.setPromptText(PROMPT_NUM_OFICIO);
			}
		}
	}
	
	@FXML
	private void fecharJanela() {
		Stage janela = (Stage) this.raiz.getScene().getWindow();
		if (janela != null)
			janela.close();
	}
	
	@FXML
	private void cadastrar() {
		if (this.estaEditando) {	// Atulizando documento existente
			fachada.atualizarDocumento(
					this.documento,
					this.rbOficio.isSelected(),
					this.txtNumProcesso.getText(),
					this.txtNomeInteressado.getText(),
					this.txtCpfInteressado.getText(),
					this.txtContatoInteressado.getText(),
					this.cbOrgao.getSelectionModel().getSelectedIndex(),
					this.cbAssunto.getSelectionModel().getSelectedIndex(),
					this.cbSituacao.getSelectionModel().getSelectedIndex(),
					this.txtObservacao.getText());
			
		} else {					// Criando novo documento
			fachada.criarDocumento(
					this.rbOficio.isSelected(),
					this.txtNumProcesso.getText(),
					this.txtNomeInteressado.getText(),
					this.txtCpfInteressado.getText(),
					this.txtContatoInteressado.getText(),
					this.cbOrgao.getSelectionModel().getSelectedIndex(),
					this.cbAssunto.getSelectionModel().getSelectedIndex(),
					this.cbSituacao.getSelectionModel().getSelectedIndex(),
					this.txtObservacao.getText());
		}
		
		this.fecharJanela();
	}
}
