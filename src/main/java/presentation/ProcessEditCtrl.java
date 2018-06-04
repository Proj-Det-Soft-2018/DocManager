package presentation;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import org.apache.log4j.Logger;

import business.exception.ValidationException;
import business.model.Process;
import business.model.Interested;
import business.service.InterestedService;
import business.service.ListService;
import business.service.Observer;
import business.service.ProcessService;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;
import persistence.exception.DatabaseException;
import presentation.utils.StringConstants;

/**
 * @author hugotho
 * 
 */
public abstract class ProcessEditCtrl implements Initializable, Observer{

    private Logger logger;

    private ListService listService;
    private ProcessService processService;
    private InterestedService interestedService;
    private ControllerFactory controllerFactory;

    protected Process process;
    protected Interested interested;

    @FXML
    private Node root;

    @FXML
    protected ChoiceBox<String> cbOrgao;

    @FXML
    protected ChoiceBox<String> cbAssunto;

    @FXML
    protected ChoiceBox<String> cbSituacao;

    public static void showProcessEditScreen(Window ownerWindow, ProcessEditCtrl controller, Process process) {
        try {
            FXMLLoader loader = new FXMLLoader(controller.getFxmlPath());
            controller.setProcess(process);
            loader.setController(controller);
            Parent rootParent = loader.load();

            Stage processEditScreen = new Stage();
            processEditScreen.initModality(Modality.WINDOW_MODAL);
            processEditScreen.initOwner(ownerWindow);
            if (process == null) {
                processEditScreen.setTitle(StringConstants.TITLE_CREATE_PROCESS_SCREEN.getText());
            } else {
                processEditScreen.setTitle(StringConstants.TITLE_EDIT_PROCESS_SCREEN.getText());
            }
            processEditScreen.setScene(new Scene(rootParent, 720, 540));

            processEditScreen.show();
        } catch (IOException e) {
            //TODO Alert Erro de geração de tela
            Logger.getLogger(InterestedEditCtrl.class).error(e.getMessage(), e);
        }
    }

    protected ProcessEditCtrl(ListService listService, ProcessService processService,
            InterestedService interestedService, ControllerFactory controllerFactory,
            Logger logger) {
        this.listService = listService;
        this.processService = processService;
        this.interestedService = interestedService;
        this.controllerFactory = controllerFactory;
        this.logger = logger;
        this.process = null;
        this.interested = null;
    }

    private void setProcess(Process process) {
        this.process = process;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        interestedService.attach(this);
        fillChoiceBoxes();
        initializeForm();
        Platform.runLater(this::configureClosure);
    }

    @Override
    public void update() {
        searchInterestedByUniqueKey();
    }

    @FXML
    private void searchInterestedByUniqueKey() {
        try {
            this.interested = interestedService.searchByCpf(catchInterestedUniqueKey());
            if (interested == null) {
                this.showInterestedCreateScreen();
            } else {
                this.fillInterestedField();
            }
        } catch (ValidationException ve) {
            // TODO Refatorar Alerts
            Alert alert = new Alert(AlertType.ERROR, ve.getMessage() + "\n\n");
            alert.getDialogPane().getChildren().stream().filter(node -> node instanceof Label).forEach(
                    node -> {
                        ((Label)node).setMinHeight(Region.USE_PREF_SIZE);
                        ((Label)node).setTextFill(Color.RED);
                    });
            alert.setHeaderText(null);
            alert.setGraphic(null);
            alert.initOwner(root.getScene().getWindow());

            alert.showAndWait();
        } catch (DatabaseException e) {
            // TODO Alert para Database
            logger.error(e.getMessage(), e);
        }
    }

    @FXML
    private void showInterestedCreateScreen() {
        Interested newInterested = createInterested();
        InterestedEditCtrl.showIntestedEditScreen(root.getScene().getWindow(),
                controllerFactory.createInterestedEditCtrl(),
                newInterested);
    }

    @FXML
    protected void showInterestedEditScreen() {
        InterestedEditCtrl.showIntestedEditScreen(
                root.getScene().getWindow(),
                controllerFactory.createInterestedEditCtrl(),
                interested);
    }

    @FXML
    protected void clearInterested() {
        interested = null;
        clearInterestedField();
    }

    private void configureClosure() {
        root.getScene().getWindow().setOnHidden(
                event -> this.interestedService.dettach(this)
                );
    }

    @FXML
    private void closeWindow() {
        Stage janela = (Stage) this.root.getScene().getWindow();
        if (janela != null)
            janela.close();
    }

    @FXML
    private void save() {
        Process processo = mountProcess();
        try {
            processo.validate();

            if (process == null ) {
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
                processo.setId(process.getId());
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
            alert.initOwner(root.getScene().getWindow());

            alert.showAndWait();
        }
    }

    private void fillChoiceBoxes() {
        if (cbOrgao != null) {
            ObservableList<String> obsListaOrgaos = cbOrgao.getItems();
            obsListaOrgaos.addAll(listService.getOrganizationsList());
            cbOrgao.getSelectionModel().select(0);
        }

        if (cbAssunto != null) {
            ObservableList<String> obsListaAssuntos = cbAssunto.getItems();
            obsListaAssuntos.addAll(listService.getSubjectsList());
            cbAssunto.getSelectionModel().select(0);
        }

        if (cbSituacao != null) {
            ObservableList<String> obsListaSituacoes = cbSituacao.getItems();
            if(process != null) {
                obsListaSituacoes.addAll(listService.getSituationsListByCurrentSituation(process.getSituation()));
            }else {
                obsListaSituacoes.addAll(listService.getSituationsListByCurrentSituation(null));
            }
            this.cbSituacao.getSelectionModel().select(0);
        }
    }

    protected abstract void initializeForm();

    protected abstract Interested createInterested();

    protected abstract String catchInterestedUniqueKey();

    protected abstract void fillInterestedField();

    protected abstract void clearInterestedField();

    protected abstract Process mountProcess();

    public abstract URL getFxmlPath();
}
