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

import negocio.dominio.Processo;
import negocio.fachada.FachadaNegocio;
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
	private Processo processoOriginal; 

	@FXML
	private VBox raiz;

	@FXML
	private RadioButton rbProcesso;

	@FXML
	private RadioButton rbOficio;

	@FXML
	private ToggleGroup tgProcessoOficio;

	@FXML
	private Label lblTipoProcesso;

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

	public void montarFormulario(Processo processo) {
		if (processo != null) {
			this.processoOriginal = processo;
			this.btnCadastrar.setText(LABEL_BTN_ATUALIZAR);

			if (processo.isTipoOficio()) {
				this.rbOficio.setSelected(true);
				this.rbProcesso.setDisable(true);
				this.cbOrgao.setDisable(true);
			} else {
				this.rbOficio.setDisable(true);
			}
			this.cbOrgao.getSelectionModel().select(processo.getUnidadeOrigem().ordinal()+1);
			this.txtNumProcesso.setText(processo.getNumero());
			this.lblNumProcesso.setDisable(true);
			this.txtNumProcesso.setDisable(true);
			this.txtNomeInteressado.setText(processo.getInteressado().getNome());
			this.txtCpfInteressado.setPlainText(processo.getInteressado().getCpf());
			this.txtContatoInteressado.setText(processo.getInteressado().getContato());
			this.cbAssunto.getSelectionModel().select(processo.getAssunto().ordinal()+1);
			this.cbSituacao.getSelectionModel().select(processo.getSituacao().ordinal()+1);
			this.txtObservacao.setText(processo.getObservacao());
		}
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		this.processoOriginal = null;
		this.fachada = FachadaNegocio.getInstance();
		this.configurarRadioButtons();
		this.preencherChoiceBoxes();
		this.configurarChoiceBoxOrgao();
	}

	private void alterarFormulario (Toggle novoValor) {
		if (novoValor != null) {
			RadioButton radio = (RadioButton)novoValor;
			this.lblTipoProcesso.setText(radio.getText());

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
		if (processoOriginal == null) {	// Criando novo processo
			Processo novoProcesso = new Processo();
			/**
				this.processo,
				this.rbOficio.isSelected(),
				this.txtNumProcesso.getText(),
				this.txtNomeInteressado.getText(),
				this.txtCpfInteressado.plainTextProperty().getValue(),
				this.txtContatoInteressado.getText(),
				this.cbOrgao.getSelectionModel().getSelectedIndex(),
				this.cbAssunto.getSelectionModel().getSelectedIndex(),
				this.cbSituacao.getSelectionModel().getSelectedIndex(),
				this.txtObservacao.getText());
			 */
			fachada.salvar(novoProcesso);
			
			
		} else {				// Atualizando existente	
			Processo processoEditado = new Processo();
			/**
				this.processo,
				this.rbOficio.isSelected(),
				this.txtNumProcesso.getText(),
				this.txtNomeInteressado.getText(),
				this.txtCpfInteressado.plainTextProperty().getValue(),
				this.txtContatoInteressado.getText(),
				this.cbOrgao.getSelectionModel().getSelectedIndex(),
				this.cbAssunto.getSelectionModel().getSelectedIndex(),
				this.cbSituacao.getSelectionModel().getSelectedIndex(),
				this.txtObservacao.getText());
			 */
			fachada.atualizar(processoEditado);
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
