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
import utils.widget.MaskedTextField;

/**
 * @author hugotho
 * 
 */
public class ControleTelaEdicao implements Initializable {

	private static final String CHOICEBOX_TEXTO_PADRAO = "-- SELECIONE --";
	private static final String LABEL_BTN_ATUALIZAR = "Atualizar"; 
	private static final String MASCARA_INICIAL_OFICIO = "####/####-";
	private static final String MASCARA_NUM_PROCESSO = "#######.########/####-##";	

	private FachadaCaixasDeEscolha fachada;
	private DocumentoVisao documento;
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
	private Label lblTipoDocumento;

	@FXML
	private Label lblNumProcesso;

	@FXML
	private MaskedTextField txtNumProcesso;

	@FXML
	private TextField txtNomeInteressado;

	@FXML
	private MaskedTextField txtCpfInteressado;

	@FXML
	private MaskedTextField txtContatoInteressado;

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

	public void montarFormulario(DocumentoVisao documento) {
		if (documento != null) {
			this.documento = documento;
			this.estaEditando = true;
			this.btnCadastrar.setText(LABEL_BTN_ATUALIZAR);

			if (documento.ehOficio()) {
				this.rbOficio.setSelected(true);
				this.rbProcesso.setDisable(true);
				this.cbOrgao.setDisable(true);
			} else {
				this.rbOficio.setDisable(true);
			}
			this.cbOrgao.getSelectionModel().select(documento.getOrgaoOrigemId());
			this.txtNumProcesso.setText(documento.getNumDocumento());
			this.lblNumProcesso.setDisable(true);
			this.txtNumProcesso.setDisable(true);
			this.txtNomeInteressado.setText(documento.getNomeInteressado());
			this.txtCpfInteressado.setText(documento.getCpfInteressado());
			this.txtContatoInteressado.setText(documento.getContatoInteressado());
			this.cbAssunto.getSelectionModel().select(documento.getAssuntoId());
			this.cbSituacao.getSelectionModel().select(documento.getSituacaoId());
			this.txtObservacao.setText(documento.getObservacao());
		}
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		this.fachada = FachadaGerenciadorDocumento.getInstance();
		this.configurarRadioButtons();
		this.preencherChoiceBoxes();
		this.configurarChoiceBoxOrgao();
	}

	private void alterarFormulario (Toggle novoValor) {
		if (novoValor != null) {
			RadioButton radio = (RadioButton)novoValor;
			this.lblTipoDocumento.setText(radio.getText());

			if(Objects.equals(radio.getText(), this.rbProcesso.getText())) {
				this.txtNumProcesso.setMask(MASCARA_NUM_PROCESSO);
			} else {
				if (cbOrgao.getSelectionModel().getSelectedIndex() != 0) {
					this.txtNumProcesso.setMask(MASCARA_INICIAL_OFICIO +
							cbOrgao.getSelectionModel().getSelectedItem().split(" - ")[0]);
				} else {
					this.txtNumProcesso.setMask(MASCARA_INICIAL_OFICIO);
				}
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
					this.txtCpfInteressado.plainTextProperty().getValue(),
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

	private void configurarRadioButtons() {
		this.tgProcessoOficio.selectedToggleProperty().addListener(
				(valorObservavel, anterior, novo) -> alterarFormulario(novo));
	}

	private void configurarChoiceBoxOrgao() {
		cbOrgao.getSelectionModel().selectedItemProperty().addListener(
				(valorObservado, valorAntigo, valorNovo) -> { 
					if (rbOficio.isSelected()) {
						this.txtNumProcesso.setMask(MASCARA_INICIAL_OFICIO + valorNovo.split(" - ")[0]);
					}
				});
	}

	private void preencherChoiceBoxes() {
		ObservableList<String> obsListaOrgaos = this.cbOrgao.getItems();
		obsListaOrgaos.add(CHOICEBOX_TEXTO_PADRAO);
		obsListaOrgaos.addAll(fachada.getListaOrgaos());
		this.cbOrgao.getSelectionModel().select(0);

		ObservableList<String> obsListaAssuntos = this.cbAssunto.getItems();
		obsListaAssuntos.add(CHOICEBOX_TEXTO_PADRAO);
		obsListaAssuntos.addAll(fachada.getListaAssuntos());
		this.cbAssunto.getSelectionModel().select(0);

		ObservableList<String> obsListaSituacoes = this.cbSituacao.getItems();
		obsListaSituacoes.add(CHOICEBOX_TEXTO_PADRAO);
		obsListaSituacoes.addAll(fachada.getListaSituacoes());
		this.cbSituacao.getSelectionModel().select(0);
	}
}
