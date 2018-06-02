package presentation;


import business.exception.ValidationException;
import business.model.Process;
import business.service.Observer;
import business.service.ProcessService;
import persistence.exception.DatabaseException;
import presentation.utils.StringConstants;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import org.apache.log4j.Logger;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * @author hugotho
 * 
 */
public abstract class MainScreenCtrl implements Initializable, Observer {

	private static final URL ARQUIVO_FXML_TELA_EDICAO = MainScreenCtrl.class.getResource("/visions/tela_editar_processo.fxml");
	private static final URL ARQUIVO_FXML_TELA_BUSCA = MainScreenCtrl.class.getResource("/visions/tela_buscar_processos.fxml");
	private static final URL ARQUIVO_FXML_TELA_VISUALIZAR_GRAFICOS = MainScreenCtrl.class.getResource("/visions/tela_statistics_graphs.fxml");
	
	private static final String CRIAR_PROCESSO = "Novo Processo / Ofício";
	private static final String EDITAR_PROCESSO = "Editar Processo";
	private static final String BUSCAR_PROCESSO = "Buscar Processos / Ofícios";
	private static final String VISUALIZAR_GRAFICOS = "Gráficos Administrativos";
	private static final String DIALOG_ADM_PASS_TITLE = "Autorização";

	private final Logger logger;
	
	private ProcessService processService;
	private ControllerFactory controllerFactory;
	
	protected Process selectedProcess;

	@FXML
	protected Node root;

	@FXML
	protected Button btnNew;

	@FXML
	protected Button btnEdit;
	
	@FXML
	protected Button btnPdfDoc;

	@FXML
	protected Button btnDelete;

	@FXML
	protected TableView<Process> tabProcesses;

	public static void showMainScreen(Stage primaryStage, MainScreenCtrl controller) {
		
		FXMLLoader loader = new FXMLLoader(controller.getFxmlPath());
		loader.setController(controller);
		try {
			Pane newPane = loader.load();
			primaryStage.setScene(new Scene(newPane, 940, 570));
			primaryStage.setTitle(StringConstants.TITLE_APPLICATION.getText());
			primaryStage.show();
		} catch (IOException e) {
			//TODO Alert Erro de geração de tela
			Logger.getLogger(HealthMainScreenCtrl.class).error(e.getMessage(), e);
		}
	}
	
	protected MainScreenCtrl(ProcessService processService, ControllerFactory controllerFactory, Logger logger) {
		this.processService = processService;
		this.controllerFactory = controllerFactory;
		this.logger = logger;
		this.selectedProcess = null;
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		processService.attach(this);
		configureTable();
		updateTable();
	}
	
	@Override
	public void update() {
		updateTable();
	}

	@FXML
	private void criarFormularioNovo() {
		this.criarTelaEdicao(CRIAR_PROCESSO, null);
	}

	@FXML
	private void criarFormularioEdicao() {
		this.criarTelaEdicao(EDITAR_PROCESSO, selectedProcess);
	}

	private void criarTelaEdicao(String titulo, Process processo) {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(ARQUIVO_FXML_TELA_EDICAO);
			Pane novoPainel = loader.load();

			Stage telaEdicao = new Stage();
			telaEdicao.setTitle(titulo);
			telaEdicao.initModality(Modality.WINDOW_MODAL);
			telaEdicao.initOwner(this.root.getScene().getWindow());
			telaEdicao.setScene(new Scene(novoPainel, 720, 540));

			ControleTelaEdicao controleTelaEdicao = loader.getController();
			controleTelaEdicao.montarFormulario(processo);
			
			telaEdicao.show();
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
		}
	}
	
	@FXML
	private void showDeleteDialog() {
		DeleteDialogCtrl.showDeleteDialog(root.getScene().getWindow(), selectedProcess, processService);
	}
	
	@FXML
	private void showPdfViewer() {
		PdfViewerCtrl.showPdfView(root.getScene().getWindow(), selectedProcess, processService);
	}
	
	@FXML
	private void criarTelaBusca() {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(ARQUIVO_FXML_TELA_BUSCA);
			Pane novoPainel = loader.load();

			Stage telaBusca = new Stage();
			telaBusca.setTitle(BUSCAR_PROCESSO);
			telaBusca.initModality(Modality.WINDOW_MODAL);
			telaBusca.initOwner(this.root.getScene().getWindow());
			telaBusca.setScene(new Scene(novoPainel, 720, 660));

			SearchScreenCtrl controleTelaBusca = loader.getController();
			controleTelaBusca.configurarFechamento();
			
			telaBusca.show();
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
		}
	}
	
	@FXML
	private void createStatisticScreen() {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(ARQUIVO_FXML_TELA_VISUALIZAR_GRAFICOS);
			Pane novoPainel = loader.load();

			Stage statisticsGraphsScreen = new Stage();
			statisticsGraphsScreen.setTitle(VISUALIZAR_GRAFICOS);
			statisticsGraphsScreen.initModality(Modality.WINDOW_MODAL);
			statisticsGraphsScreen.initOwner(this.root.getScene().getWindow());
			statisticsGraphsScreen.setScene(new Scene(novoPainel, 940, 570));			
			
			statisticsGraphsScreen.show();
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
		}
	}
	
	private void updateTable() {
		try {
			List<Process> lista = this.processService.pullList();
			tabProcesses.getItems().setAll(lista);
		} catch (ValidationException | DatabaseException e) {
			// TODO Alert Banco de Dados)
			logger.error(e.getMessage(), e);
		}
		
	}
	
	protected abstract void configureTable();
	
	public abstract URL getFxmlPath();
}
