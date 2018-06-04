package presentation;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import org.apache.log4j.Logger;

import business.exception.ValidationException;
import business.model.Process;
import business.model.Search;
import business.service.Observer;
import business.service.ProcessService;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;
import persistence.exception.DatabaseException;
import presentation.utils.StringConstants;

public abstract class SearchScreenCtrl implements Initializable, Observer {

    private Logger logger;

	private ControllerFactory controllerFactory;
	private ProcessService processService;
	
	private Process selectedProcess;
	private Search ultimaBusca;

	@FXML
	private Node root;

	@FXML
	protected TableView<Process> tableResultados;

	@FXML
	private Button btnVerEditar;
	
	@FXML
	private Button btnCertidaoPdf;

	@FXML
	private Button btnApagar;
	
	public static void showProcessEditScreen(Window ownerWindow, SearchScreenCtrl controller) {
        try {
            FXMLLoader loader = new FXMLLoader(controller.getFxmlPath());
            loader.setController(controller);
            Parent rootParent = loader.load();

            Stage searchScreen = new Stage();
            searchScreen.initModality(Modality.WINDOW_MODAL);
            searchScreen.initOwner(ownerWindow);
            searchScreen.setTitle(StringConstants.TITLE_SEARCH_SCREEN.getText());
            searchScreen.setScene(new Scene(rootParent, 720, 660));

            searchScreen.show();
        } catch (IOException e) {
            //TODO Alert Erro de geração de tela
            Logger.getLogger(SearchScreenCtrl.class).error(e.getMessage(), e);
        }
    }

	public SearchScreenCtrl(ControllerFactory controllerFactory,
	        ProcessService processService, Logger logger) {
        this.controllerFactory = controllerFactory;
        this.processService = processService;
        this.logger = logger;
        selectedProcess = null;
        ultimaBusca = null;
    }
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		processService.attach(this);
		configureForm();
		configureTable();
		Platform.runLater(this::configureClosure);
	}

	@Override
	public void update() {
		if (this.ultimaBusca != null) {
			List<Process> resultado = null;
			try {
				resultado = this.processService.searchAll(ultimaBusca);
			} catch (ValidationException | DatabaseException e) {
			    // TODO Gerar alert genérico, pois a última busca já foi validada 
				logger.error(e.getMessage(), e);
			}
			atualizarTabela(resultado);
		}
	}
	
	private void configureTable() {
        // eventHandle para detectar o processo selecionado
        tableResultados.getSelectionModel().selectedItemProperty().addListener(
                (observavel, selecionandoAnterior, selecionadoNovo) -> {
                    this.selectedProcess = selecionadoNovo;
                    this.btnVerEditar.setDisable(selecionadoNovo!=null? false : true);
                    this.btnCertidaoPdf.setDisable(selecionadoNovo!=null? false : true);
                    this.btnApagar.setDisable(selecionadoNovo!=null? false : true);
                });
        // chama o método abstrato para configurar as colunas
        configureColumns();
    }

	@FXML
	private void search() {
	    
	    Search search = mountSearch();
		try {
			List<Process> resultado = processService.searchAll(search);
			ultimaBusca = search;
			atualizarTabela(resultado);
		} catch (ValidationException ve) {
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
		    // TODO Criar alert para Database
			logger.error(e.getMessage(), e);
		}
	}

	@FXML
	private void fecharJanela() {
		Stage window = (Stage) this.root.getScene().getWindow();
		if (window != null)
			window.close();
	}

	private void configureClosure() {
        root.getScene().getWindow().setOnHidden(
                event -> processService.dettach(this)
                );
    }

	private void atualizarTabela(List<Process> lista) {
		tableResultados.getItems().setAll(lista);
	}

	@FXML
    private void criarTelaEdicao() {
        ProcessEditCtrl.showProcessEditScreen(root.getScene().getWindow(), 
                controllerFactory.createProcessEditCtrl(), selectedProcess);
    }
	
	@FXML
	private void criarDialogAdmPassword() {
		DeleteDialogCtrl.showDeleteDialog(root.getScene().getWindow(),
				controllerFactory.createDeleteDialogCtrl(),	selectedProcess);
	}
	
	@FXML
	private void criarTelaPdf() {
		PdfViewerCtrl.showPdfView(root.getScene().getWindow(),
				controllerFactory.createPdfViewerCtrl(), selectedProcess);
	}
	
	protected abstract void configureForm();
	
	protected abstract Search mountSearch();
	
	protected abstract void configureColumns();
	
	public abstract URL getFxmlPath();
}