package presentation;

import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

import org.apache.log4j.Logger;

import business.exception.ValidationException;
import business.model.Process;
import business.model.Situation;
import business.model.Subject;
import business.model.HealthInterested;
import business.model.HealthProcess;
import business.model.Interested;
import business.model.Organization;
import business.service.ConcreteInterestedService;
import business.service.ConcreteListService;
import business.service.ConcreteProcessService;
import business.service.InterestedService;
import business.service.Observer;
import business.service.ProcessService;
import business.service.ListService;
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
import persistence.exception.DatabaseException;
import presentation.utils.widget.MaskedTextField;

/**
 * @author hugotho
 * 
 */
public class ControleTelaEdicao implements Initializable, Observer{

	private Logger logger = Logger.getLogger(ControleTelaEdicao.class);

	private static final String LABEL_BTN_ATUALIZAR = "Atualizar"; 
	private static final String LABEL_BTN_EDITAR_INTERESSADO = "Editar"; 
	private static final String LABEL_BTN_LIMPAR_INTERESSADO = "Limpar"; 
	private static final String MASCARA_NUM_OFICIO = "####/####";
	private static final String MASCARA_NUM_PROCESSO = "#####.######/####-##";

	private ListService listService;
	private ProcessService processService;
	private InterestedService interestedService;
	private Process processoOriginal;
	private Interested interessado;
	
	private ControllerFactory controllerFactory;

	// TODO Remover após refatoração
	public void setControllerFactory(ControllerFactory controllerFactory) {
		this.controllerFactory = controllerFactory;
	}

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
		processoOriginal = null;
		interessado = null;
		listService = ConcreteListService.getInstance();
		processService = ConcreteProcessService.getInstance();
		interestedService = ConcreteInterestedService.getInstance();
		interestedService.attach(this);
		configurarRadioButtons();
		fillChoiceBoxes();
		configurarChoiceBoxOrgao();
	}

	@Override
	public void update() {
		buscarPorCpf();
	}

	public void montarFormulario(Process processo) {
		if (processo != null) {
			this.processoOriginal = processo;
			this.btnCadastrar.setText(LABEL_BTN_ATUALIZAR);

			if (processo.isOficio()) {
				this.rbOficio.setSelected(true);
				this.rbProcesso.setDisable(true);
				this.cbOrgao.setDisable(true);
			} else {
				this.rbOficio.setDisable(true);
			}
			this.cbOrgao.getSelectionModel().select(processo.getOriginEntity().ordinal());
			this.txtNumProcesso.setPlainText(processo.getNumber());
			this.txtNumProcesso.setDisable(true);

			this.interessado = processo.getIntersted();
			this.preencherInteressado();

			this.cbAssunto.getSelectionModel().select(processo.getSubject().ordinal());
			this.cbSituacao.getSelectionModel().select(processo.getSituation().ordinal());
			this.txtObservacao.setText(processo.getObservation());
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
			btnEditarInteressado.setOnAction(evento -> this.showInterestedEditScreen());
			Button btnLimparInteressado = new Button(LABEL_BTN_LIMPAR_INTERESSADO);
			btnLimparInteressado.setOnAction(evento -> this.limparInteressado());

			this.hBoxInteressado.getChildren().addAll(btnEditarInteressado, btnLimparInteressado);
		}

		this.lblTxtNomeInteressado.setText(this.interessado.getName());
		String contato = this.interessado.getFormatedContact();
		if (contato != null && contato.length() != 0) {	
			this.lblTxtContatoInteressado.setText(contato);
		} else {
			this.lblTxtContatoInteressado.setText("");
		}
	}

	@FXML
	public void buscarPorCpf() {
		try {
			this.interessado = interestedService.searchByCpf(this.txtCpfInteressado.plainTextProperty().getValue());
			if (interessado == null) {
				this.showInterestedCreateScreen();
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
		} catch (DatabaseException e) {
			logger.error(e.getMessage(), e);
		}
	}

	private void showInterestedEditScreen() {
		InterestedEditCtrl.showIntestedEditScreen(
				raiz.getScene().getWindow(),
				controllerFactory.createInterestedEditCtrl(),
				interessado);
	}

	private void showInterestedCreateScreen() {
		Interested newInterested = createInterested();
		InterestedEditCtrl.showIntestedEditScreen(raiz.getScene().getWindow(),
				controllerFactory.createInterestedEditCtrl(),
				newInterested);
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
		cbOrgao.getSelectionModel().selectedIndexProperty().addListener(
				(valorObservado, valorAntigo, valorNovo) -> { 
					if (rbOficio.isSelected()) {
						if (valorNovo.intValue() == 0) {
							this.txtNumProcesso.setMask(MASCARA_NUM_OFICIO);
						} else {
							String initials = cbOrgao.getItems().get(valorNovo.intValue()).split(" - ")[0];
							this.txtNumProcesso.setMask(MASCARA_NUM_OFICIO + "-" + initials);
						}
					}
				});
	}

	private void configurarFechamento() {
		this.raiz.getScene().getWindow().setOnHidden(
				event -> this.interestedService.dettach(this)
				);
	}

	private void fillChoiceBoxes() {
		ObservableList<String> obsListaOrgaos = this.cbOrgao.getItems();
		obsListaOrgaos.addAll(listService.getOrganizationsList());
		this.cbOrgao.getSelectionModel().select(0);

		ObservableList<String> obsListaAssuntos = this.cbAssunto.getItems();
		obsListaAssuntos.addAll(listService.getSubjectsList());
		this.cbAssunto.getSelectionModel().select(0);

		ObservableList<String> obsListaSituacoes = this.cbSituacao.getItems();
		obsListaSituacoes.addAll(listService.getSituationsList());
		this.cbSituacao.getSelectionModel().select(0);
	}

	@FXML
	private void closeWindow() {
		Stage janela = (Stage) this.raiz.getScene().getWindow();
		if (janela != null)
			janela.close();
	}

	@FXML
	private void save() {
		//TODO refatorar
		String number;
		boolean oficio = false;

		//processo.setTipoOficio(this.rbOficio.isSelected());
		if (this.rbOficio.isSelected()) {
			oficio = true;
			number = this.txtNumProcesso.plainTextProperty().getValue() +
					(cbOrgao.getSelectionModel().getSelectedItem().split(" - ")[0]);
		} else {
			number = this.txtNumProcesso.plainTextProperty().getValue();
		}
		
		Process processo = new HealthProcess(
				oficio,
				number,
				this.interessado,
				Organization.getOrganizationById(this.cbOrgao.getSelectionModel().getSelectedIndex()),
				Subject.getSubjectById(this.cbAssunto.getSelectionModel().getSelectedIndex()),
				Situation.getSituationById(this.cbSituacao.getSelectionModel().getSelectedIndex()),
				this.txtObservacao.getText());
		try {
			processo.validate();
			
			interestedService.dettach(this);
			
			if (processoOriginal == null ) {
				/* Criar novo Processo */
				try {
					processService.save(processo);
				} catch (ValidationException e) {
					logger.error(e.getMessage(), e);
				} catch (DatabaseException e) {
					logger.error(e.getMessage(), e);
				}
		
			} else {
				/* Alterar Processo Existente */
				processo.setId(processoOriginal.getId());
				try {
					processService.update(processo);
				} catch (DatabaseException e) {
					logger.error(e.getMessage(), e);
				}
			}
		
			this.closeWindow();
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
	
	protected Interested createInterested() {
		Interested interested = new HealthInterested();
		interested.setCpf(this.txtCpfInteressado.plainTextProperty().getValue());
		return interested;
	}
}
