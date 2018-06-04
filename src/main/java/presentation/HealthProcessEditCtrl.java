package presentation;

import java.net.URL;
import java.util.Objects;

import org.apache.log4j.Logger;

import business.model.HealthInterested;
import business.model.HealthInterestedSearch;
import business.model.HealthProcess;
import business.model.Interested;
import business.model.Organization;
import business.model.Process;
import business.model.Search;
import business.model.Situation;
import business.model.Subject;
import business.service.InterestedService;
import business.service.ListService;
import business.service.ProcessService;
import presentation.ControllerFactory;
import presentation.utils.widget.MaskedTextField;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.HBox;

/**
 * @author hugotho
 * 
 */
public class HealthProcessEditCtrl extends ProcessEditCtrl{

    private static final URL FXML_PATH = HealthProcessEditCtrl.class.getResource("/visions/health_process_edit_screen.fxml");
    
    private static final Logger LOGGER = Logger.getLogger(HealthProcessEditCtrl.class);
    
    private static final String MASCARA_NUM_OFICIO = "####/####";
    private static final String MASCARA_NUM_PROCESSO = "#####.######/####-##";
    
    private ListService listService;

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
    protected ChoiceBox<String> cbOrgao;

    @FXML
    protected ChoiceBox<String> cbAssunto;

    @FXML
    protected ChoiceBox<String> cbSituacao;

    @FXML
    private TextArea txtObservacao;

    @FXML
    private Button btnCancelar;

    @FXML
    private Button btnCadastrar;

    public HealthProcessEditCtrl(ListService listService, ProcessService processService,
            InterestedService interestedService, ControllerFactory controllerFactory) {
        super(processService, interestedService, controllerFactory, LOGGER);
        this.listService = listService;
    }
    
    @Override
    protected void initializeForm() {
        fillChoiceBoxes();
        configureRadioButtons();
        configureEntityChoiceBox();
        if (super.process != null) {
            HealthProcess healthProcess = (HealthProcess) super.process;
            this.btnCadastrar.setText("Atualizar");

            if (healthProcess.isOficio()) {
                this.rbOficio.setSelected(true);
                this.rbProcesso.setDisable(true);
                this.cbOrgao.setDisable(true);
            } else {
                this.rbOficio.setDisable(true);
            }
            this.cbOrgao.getSelectionModel().select(healthProcess.getOriginEntity().ordinal());
            this.txtNumProcesso.setPlainText(healthProcess.getNumber());
            this.txtNumProcesso.setDisable(true);

            this.interested = super.process.getIntersted();
            this.fillInterestedField();

            this.cbAssunto.getSelectionModel().select(healthProcess.getSubject().ordinal());
            this.cbSituacao.getSelectionModel().select(healthProcess.getSituationString());
            this.txtObservacao.setText(healthProcess.getObservation());
        }
    }
    
    private void fillChoiceBoxes() {
        ObservableList<String> obsListaOrgaos = cbOrgao.getItems();
        obsListaOrgaos.addAll(listService.getOrganizationsList());
        cbOrgao.getSelectionModel().select(0);
    
        ObservableList<String> obsListaAssuntos = cbAssunto.getItems();
        obsListaAssuntos.addAll(listService.getSubjectsList());
        cbAssunto.getSelectionModel().select(0);
    
        ObservableList<String> obsListaSituacoes = cbSituacao.getItems();
        if(process != null) {
            obsListaSituacoes.addAll(listService.getSituationsListByCurrentSituation(process.getSituation()));
        }else {
            obsListaSituacoes.addAll(listService.getSituationsListByCurrentSituation(null));
        }
        this.cbSituacao.getSelectionModel().select(0);
    }

    @Override
    protected Search mountSearch() {
        HealthInterestedSearch search = new HealthInterestedSearch();
        search.setCpf(txtCpfInteressado.plainTextProperty().getValue());
        return search; 
    }

    @Override
    protected Interested createInterested() {
        Interested interested = new HealthInterested();
        interested.setCpf(this.txtCpfInteressado.plainTextProperty().getValue());
        return interested;
    }

    @Override
    protected void fillInterestedField() {

        HealthInterested healthInterested = (HealthInterested) super.interested;
        this.txtCpfInteressado.setPlainText(healthInterested.getCpf());
        this.txtCpfInteressado.setDisable(true);

        if (hBoxInteressado.getChildren().contains(btnBuscarInteressado)) {
            this.hBoxInteressado.getChildren().remove(btnBuscarInteressado);

            Button btnEditarInteressado = new Button("Editar");
            btnEditarInteressado.setOnAction(evento -> super.showInterestedEditScreen());
            Button btnLimparInteressado = new Button("Limpar");
            btnLimparInteressado.setOnAction(evento -> super.clearInterested());

            this.hBoxInteressado.getChildren().addAll(btnEditarInteressado, btnLimparInteressado);
        }

        this.lblTxtNomeInteressado.setText(healthInterested.getName());
        String contact = healthInterested.getFormatedContact();
        if (contact != null && contact.length() != 0) { 
            this.lblTxtContatoInteressado.setText(contact);
        } else {
            this.lblTxtContatoInteressado.setText("");
        }
    }

    @Override
    protected void clearInterestedField() {
        this.hBoxInteressado.getChildren().clear();
        this.hBoxInteressado.getChildren().addAll(this.lblCpfInteressado, this.txtCpfInteressado, this.btnBuscarInteressado);
        this.txtCpfInteressado.setDisable(false);
        this.txtCpfInteressado.clear();
        this.lblTxtNomeInteressado.setText("");
        this.lblTxtContatoInteressado.setText("");
    }
    @Override
    protected Process mountProcess() {
        
        String number;
        boolean oficio = false;

        if (this.rbOficio.isSelected()) {
            oficio = true;
            number = this.txtNumProcesso.plainTextProperty().getValue() +
                    (cbOrgao.getSelectionModel().getSelectedItem().split(" - ")[0]);
        } else {
            number = this.txtNumProcesso.plainTextProperty().getValue();
        }
        
        return new HealthProcess(
                oficio,
                number,
                this.interested,
                Organization.getOrganizationById(this.cbOrgao.getSelectionModel().getSelectedIndex()),
                Subject.getSubjectById(this.cbAssunto.getSelectionModel().getSelectedIndex()),
                Situation.getSituationById(this.cbSituacao.getSelectionModel().getSelectedIndex()),
                this.txtObservacao.getText()
                );
    }

    private void configureRadioButtons() {
        this.tgProcessoOficio.selectedToggleProperty().addListener(
                (valorObservavel, anterior, novo) -> alterarFormulario(novo));
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

    private void configureEntityChoiceBox() {
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

    @Override
    public URL getFxmlPath() {
       return FXML_PATH;
    }
    
    
}
