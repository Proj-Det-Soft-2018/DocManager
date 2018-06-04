package presentation;

import java.net.URL;
import java.util.Objects;

import org.apache.log4j.Logger;

import business.model.HealthInterested;
import business.model.HealthInterestedSearch;
import business.model.HealthOrganization;
import business.model.HealthProcess;
import business.model.HealthSituation;
import business.model.HealthSubject;
import business.model.Interested;
import business.model.Process;
import business.model.Search;
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
            btnCadastrar.setText("Atualizar");

            if (healthProcess.isOficio()) {
                rbOficio.setSelected(true);
                rbProcesso.setDisable(true);
                cbOrgao.setDisable(true);
            } else {
                rbOficio.setDisable(true);
            }
            cbOrgao.getSelectionModel().select(healthProcess.getOriginEntity().getId());
            txtNumProcesso.setPlainText(healthProcess.getNumber());
            txtNumProcesso.setDisable(true);

            interested = super.process.getIntersted();
            fillInterestedField();

            cbAssunto.getSelectionModel().select(healthProcess.getSubject().getId());
            cbSituacao.getSelectionModel().select(healthProcess.getSituationString());
            txtObservacao.setText(healthProcess.getObservation());
        }
    }
    
    private void fillChoiceBoxes() {
        ObservableList<String> obsListaOrgaos = cbOrgao.getItems();
        obsListaOrgaos.addAll(listService.getOrganizationsList());
        cbOrgao.getSelectionModel().select(0);
    
        ObservableList<String> obsListaAssuntos = cbAssunto.getItems();
        obsListaAssuntos.addAll(listService.getSubjectsDescritionList());
        cbAssunto.getSelectionModel().select(0);
    
        ObservableList<String> obsListaSituacoes = cbSituacao.getItems();
        if(process != null) {
            obsListaSituacoes.addAll(listService.getSituationsListByCurrentSituation(super.process.getSituation()));
        }else {
            obsListaSituacoes.addAll(listService.getSituationsListByCurrentSituation(null));
        }
        cbSituacao.getSelectionModel().select(0);
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
        interested.setCpf(txtCpfInteressado.plainTextProperty().getValue());
        return interested;
    }

    @Override
    protected void fillInterestedField() {

        HealthInterested healthInterested = (HealthInterested) super.interested;
        txtCpfInteressado.setPlainText(healthInterested.getCpf());
        txtCpfInteressado.setDisable(true);

        if (hBoxInteressado.getChildren().contains(btnBuscarInteressado)) {
            hBoxInteressado.getChildren().remove(btnBuscarInteressado);

            Button btnEditarInteressado = new Button("Editar");
            btnEditarInteressado.setOnAction(evento -> super.showInterestedEditScreen());
            Button btnLimparInteressado = new Button("Limpar");
            btnLimparInteressado.setOnAction(evento -> super.clearInterested());

            hBoxInteressado.getChildren().addAll(btnEditarInteressado, btnLimparInteressado);
        }

        lblTxtNomeInteressado.setText(healthInterested.getName());
        String contact = healthInterested.getFormatedContact();
        if (contact != null && contact.length() != 0) { 
            lblTxtContatoInteressado.setText(contact);
        } else {
            lblTxtContatoInteressado.setText("");
        }
    }

    @Override
    protected void clearInterestedField() {
        hBoxInteressado.getChildren().clear();
        hBoxInteressado.getChildren().addAll(lblCpfInteressado, txtCpfInteressado, btnBuscarInteressado);
        txtCpfInteressado.setDisable(false);
        txtCpfInteressado.clear();
        lblTxtNomeInteressado.setText("");
        lblTxtContatoInteressado.setText("");
    }
    @Override
    protected Process mountProcess() {
        
        String number;
        boolean oficio = false;

        if (rbOficio.isSelected()) {
            oficio = true;
            number = txtNumProcesso.plainTextProperty().getValue() +
                    (cbOrgao.getSelectionModel().getSelectedItem().split(" - ")[0]);
        } else {
            number = txtNumProcesso.plainTextProperty().getValue();
        }
        
        return new HealthProcess(
                oficio,
                number,
                super.interested,
                HealthOrganization.getOrganizationById(cbOrgao.getSelectionModel().getSelectedIndex()),
                HealthSubject.getSubjectById(cbAssunto.getSelectionModel().getSelectedIndex()),
                HealthSituation.getSituationById(cbSituacao.getSelectionModel().getSelectedIndex()),
                txtObservacao.getText()
                );
    }

    private void configureRadioButtons() {
        tgProcessoOficio.selectedToggleProperty().addListener(
                (valorObservavel, anterior, novo) -> alterarFormulario(novo));
    }
    
    private void alterarFormulario (Toggle novoValor) {
        if (novoValor != null) {
            RadioButton radio = (RadioButton)novoValor;
            lblTipoProcesso.setText(radio.getText());

            if(Objects.equals(radio, rbProcesso)) {
                txtNumProcesso.setMask("#####.######/####-##");
            } else {
                if (cbOrgao.getSelectionModel().getSelectedIndex() != 0) {
                    txtNumProcesso.setMask("####/####-" +
                            cbOrgao.getSelectionModel().getSelectedItem().split(" - ")[0]);
                } else {
                    txtNumProcesso.setMask("####/####");
                }
            }
        }
    }

    private void configureEntityChoiceBox() {
        cbOrgao.getSelectionModel().selectedIndexProperty().addListener(
                (valorObservado, valorAntigo, valorNovo) -> { 
                    if (rbOficio.isSelected()) {
                        if (valorNovo.intValue() == 0) {
                            txtNumProcesso.setMask("####/####");
                        } else {
                            String initials = cbOrgao.getItems().get(valorNovo.intValue()).split(" - ")[0];
                            txtNumProcesso.setMask("####/####-" + initials);
                        }
                    }
                });
    }

    @Override
    public URL getFxmlPath() {
       return FXML_PATH;
    }
    
    
}
