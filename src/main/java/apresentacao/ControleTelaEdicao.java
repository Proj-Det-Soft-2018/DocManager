package apresentacao;

import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

import org.apache.log4j.Logger;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import negocio.dominio.Interessado;
import negocio.dominio.Processo;
import negocio.fachada.FachadaCaixasDeEscolha;
import negocio.fachada.FachadaNegocio;
import negocio.servico.Observador;
import negocio.servico.ValidationException;
import utils.widget.MaskedContactTextField;
import utils.widget.MaskedTextField;

/**
 * @author hugotho
 * 
 */
public class ControleTelaEdicao implements Initializable, Observador{

	private Logger logger = Logger.getLogger(ControleTelaEdicao.class);

	private static final URL ARQUIVO_FXML_DIALOG_INTERESSADO = ControleTelaPrincipal.class.getResource("/visoes/dialog_editar_interessado.fxml");
	private static final String CRIAR_INTERESSADO = "Novo Interessado";
	private static final String EDITAR_INTERESSADO = "Editar Interessado";
	private static final String CHOICEBOX_TEXTO_PADRAO = "-- SELECIONE --";
	private static final String LABEL_BTN_ATUALIZAR = "Atualizar"; 
	private static final String LABEL_BTN_EDITAR_INTERESSADO = "Editar"; 
	private static final String LABEL_BTN_LIMPAR_INTERESSADO = "Limpar"; 
	private static final String MASCARA_NUM_OFICIO = "####/####";
	private static final String MASCARA_NUM_PROCESSO = "#####.######/####-##";

	private FachadaCaixasDeEscolha fachada;
	private Processo processoOriginal;
	private Interessado interessado;

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
	private HBox hBoxInteressado;

	@FXML
	private Label lblCpfInteressado;

	@FXML
	private MaskedTextField txtCpfInteressado;

	@FXML
	private Button btnBuscarInteressado;

	@FXML
	private Label lblTxtNomeInteressado;

	@FXML
	private Label lblTxtContatoInteressado; 

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
		this.processoOriginal = null;
		this.interessado = null;
		this.fachada = FachadaNegocio.getInstance();
		this.fachada.cadastrarObservador(this);
		this.configurarRadioButtons();
		this.preencherChoiceBoxes();
		this.configurarChoiceBoxOrgao();
	}

	@Override
	public void atualizar() {
		this.buscarPorCpf();
	}

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
			this.cbOrgao.getSelectionModel().select(processo.getUnidadeOrigem().ordinal());
			this.txtNumProcesso.setPlainText(processo.getNumero());
			this.txtNumProcesso.setDisable(true);

			this.interessado = processo.getInteressado();
			this.preencherInteressado();

			this.cbAssunto.getSelectionModel().select(processo.getAssunto().ordinal());
			this.cbSituacao.getSelectionModel().select(processo.getSituacao().ordinal());
			this.txtObservacao.setText(processo.getObservacao());
		}

		this.configurarFechamento();
	}

	private void alterarFormulario (Toggle novoValor) {
		if (novoValor != null) {
			RadioButton radio = (RadioButton)novoValor;
			this.lblTipoProcesso.setText(radio.getText());

			if(Objects.equals(radio, this.rbProcesso)) {
				this.txtNumProcesso.setMask(MASCARA_NUM_PROCESSO);
			} else {
				if (cbOrgao.getSelectionModel().getSelectedIndex() != 0) {
					this.txtNumProcesso.setMask(MASCARA_NUM_OFICIO + "-" +
							cbOrgao.getSelectionModel().getSelectedItem().split(" - ")[0]);
				} else {
					this.txtNumProcesso.setMask(MASCARA_NUM_OFICIO);
				}
			}
		}
	}

	private void preencherInteressado() {

		this.txtCpfInteressado.setPlainText(this.interessado.getCpf());
		this.txtCpfInteressado.setDisable(true);

		if (hBoxInteressado.getChildren().contains(btnBuscarInteressado)) {
			this.hBoxInteressado.getChildren().remove(btnBuscarInteressado);

			Button btnEditarInteressado = new Button(LABEL_BTN_EDITAR_INTERESSADO);
			btnEditarInteressado.setOnAction(evento -> this.editarInteressado());
			Button btnLimparInteressado = new Button(LABEL_BTN_LIMPAR_INTERESSADO);
			btnLimparInteressado.setOnAction(evento -> this.limparInteressado());

			this.hBoxInteressado.getChildren().addAll(btnEditarInteressado, btnLimparInteressado);
		}

		this.lblTxtNomeInteressado.setText(this.interessado.getNome());
		String contato = this.interessado.getContato();
		if (contato != null && contato.length() != 0) {
			MaskedContactTextField contatoMascara = new MaskedContactTextField();
			contatoMascara.setContactPlainText(contato);			
			this.lblTxtContatoInteressado.setText(contatoMascara.getText());
		} else {
			this.lblTxtContatoInteressado.setText("");
		}
	}

	@FXML
	public void buscarPorCpf() {
		try {
			this.interessado = fachada.buscarPorCpf(this.txtCpfInteressado.plainTextProperty().getValue());
			if (interessado == null) {
				this.criarNovoInteressado();
			} else {
				this.preencherInteressado();
			}
		} catch (ValidationException ve) {
			Alert alert = new Alert(AlertType.ERROR, ve.getMessage() + "\n\n");
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

	private void editarInteressado() {
		criarDialogEdicao(EDITAR_INTERESSADO, interessado);
	}

	private void criarNovoInteressado() {
		criarDialogEdicao(CRIAR_INTERESSADO, null);
	}

	private void criarDialogEdicao(String titulo, Interessado interessado) {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(ARQUIVO_FXML_DIALOG_INTERESSADO);
			Pane novoPainel = loader.load();

			Stage dialogEdicao = new Stage();
			dialogEdicao.setTitle(titulo);
			dialogEdicao.initModality(Modality.WINDOW_MODAL);
			dialogEdicao.initOwner(this.raiz.getScene().getWindow());
			ControleDialogInteressado controleDialog = loader.getController();
			if (interessado == null) {
				controleDialog.setCpfOnForm(this.txtCpfInteressado.plainTextProperty().getValue());
				dialogEdicao.setScene(new Scene(novoPainel, 400, 260));
			} else {
				controleDialog.populeForm(interessado);
				dialogEdicao.setScene(new Scene(novoPainel, 400, 230));
			}
			dialogEdicao.show();
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}

	private void limparInteressado() {
		this.interessado = null;

		this.hBoxInteressado.getChildren().clear();
		this.hBoxInteressado.getChildren().addAll(this.lblCpfInteressado, this.txtCpfInteressado, this.btnBuscarInteressado);
		this.txtCpfInteressado.setDisable(false);
		this.txtCpfInteressado.clear();
		this.lblTxtNomeInteressado.setText("");
		this.lblTxtContatoInteressado.setText("");
	}

	private void configurarRadioButtons() {
		this.tgProcessoOficio.selectedToggleProperty().addListener(
				(valorObservavel, anterior, novo) -> alterarFormulario(novo));
	}

	private void configurarChoiceBoxOrgao() {
		cbOrgao.getSelectionModel().selectedItemProperty().addListener(
				(valorObservado, valorAntigo, valorNovo) -> { 
					if (rbOficio.isSelected()) {
						if (valorNovo.equalsIgnoreCase(CHOICEBOX_TEXTO_PADRAO)) {
							this.txtNumProcesso.setMask(MASCARA_NUM_OFICIO);
						} else {
							this.txtNumProcesso.setMask(MASCARA_NUM_OFICIO + "-" + valorNovo.split(" - ")[0]);
						}
					}
				});
	}

	private void configurarFechamento() {
		this.raiz.getScene().getWindow().setOnHidden(
				event -> this.fachada.descadastrarObservador(this)
				);
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

	@FXML
	private void fecharJanela() {
		Stage janela = (Stage) this.raiz.getScene().getWindow();
		if (janela != null)
			janela.close();
	}

	@FXML
	private void salvar() {
		Processo processo = new Processo();
		boolean failure = false;
		StringBuilder failureMsg = new StringBuilder();

		processo.setTipoOficio(this.rbOficio.isSelected());

		try {
			if (this.rbOficio.isSelected()) {
				String oficioNum = this.txtNumProcesso.plainTextProperty().getValue() +
						(cbOrgao.getSelectionModel().getSelectedItem().split(" - ")[0]);
				processo.setNumero(oficioNum);
			} else {
				processo.setNumero(this.txtNumProcesso.plainTextProperty().getValue());
			}
		} catch (ValidationException ve) {
			failure = true;
			failureMsg.append(ve.getMessage());
		}
		try {
			processo.setInteressado(this.interessado);
		} catch (ValidationException ve) {
			failure = true;
			if (failureMsg.length() != 0) {
				failureMsg.append("\n\n");
			}
			failureMsg.append(ve.getMessage());
		}
		try {
			processo.setUnidadeOrigemById(this.cbOrgao.getSelectionModel().getSelectedIndex());
		} catch (ValidationException ve) {
			failure = true;
			if (failureMsg.length() != 0) {
				failureMsg.append("\n\n");
			}
			failureMsg.append(ve.getMessage());
		}
		try {
			processo.setAssuntoById(this.cbAssunto.getSelectionModel().getSelectedIndex());
		} catch (ValidationException ve) {
			failure = true;
			if (failureMsg.length() != 0) {
				failureMsg.append("\n\n");
			}
			failureMsg.append(ve.getMessage());
		}
		try {
			processo.setSituacaoById(this.cbSituacao.getSelectionModel().getSelectedIndex());
		} catch (ValidationException ve) {
			failure = true;
			if (failureMsg.length() != 0) {
				failureMsg.append("\n\n");
			}
			failureMsg.append(ve.getMessage());
		}
		processo.setObservacao(this.txtObservacao.getText());

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
		} else {		
			this.fachada.descadastrarObservador(this);
			
			if (processoOriginal == null ) {
				/* Criar novo Processo */
				fachada.salvar(processo);
		
			} else {
				/* Alterar Processo Existente */
				processo.setId(processoOriginal.getId());
				fachada.atualizar(processo);
			}
		
			this.fecharJanela();
		}
	}
}
