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
	private static final URL ARQUIVO_FXML_DIALOG_PASSWORD = MainScreenCtrl.class.getResource("/visions/dialog_adm_password.fxml");
	private static final URL ARQUIVO_FXML_TELA_BUSCA = MainScreenCtrl.class.getResource("/visions/tela_buscar_processos.fxml");
	private static final URL ARQUIVO_FXML_TELA_VISUALIZAR_PDF = MainScreenCtrl.class.getResource("/visions/tela_visualizar_pdf.fxml");
	private static final URL ARQUIVO_FXML_TELA_VISUALIZAR_GRAFICOS = MainScreenCtrl.class.getResource("/visions/tela_statistics_graphs.fxml");
	
	private static final String CRIAR_PROCESSO = "Novo Processo / Ofício";
	private static final String EDITAR_PROCESSO = "Editar Processo";
	private static final String BUSCAR_PROCESSO = "Buscar Processos / Ofícios";
	private static final String VISUALIZAR_PDF = "Certidão";
	private static final String VISUALIZAR_GRAFICOS = "Gráficos Administrativos";
	private static final String DIALOG_ADM_PASS_TITLE = "Autorização";

	private final Logger logger;
	
	private ProcessService processService;
	private ControllerFactory controllerFactory;
	
	protected Process processoSelecionado;

	@FXML
	protected Node root;

	@FXML
	protected Button btnNovo;

	@FXML
	protected Button btnVerEditar;
	
	@FXML
	protected Button btnCertidaoPdf;

	@FXML
	protected Button btnApagar;

	@FXML
	protected TableView<Process> tabelaProcessosOficios;

	public static void showMainScreen(Stage primaryStage, MainScreenCtrl controller) {
		
		FXMLLoader loader = new FXMLLoader(controller.getFxmlPath());
		loader.setController(controller);
		try {
			Pane newPane = loader.load();
			primaryStage.setScene(new Scene(newPane, 940, 570));
			primaryStage.setTitle(StringConstants.TITULO_APLICACAO.getText());
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
		this.processoSelecionado = null;
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		configurarTabela();
		atualizarTabela();
	}
	
	@Override
	public void update() {
		atualizarTabela();
	}

	@FXML
	private void criarFormularioNovo() {
		this.criarTelaEdicao(CRIAR_PROCESSO, null);
	}

	@FXML
	private void criarFormularioEdicao() {
		this.criarTelaEdicao(EDITAR_PROCESSO, processoSelecionado);
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
	private void criarDialogAdmPassword() {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(ARQUIVO_FXML_DIALOG_PASSWORD);
			Pane novoPainel = loader.load();

			Stage dialogAdmPassword = new Stage();
			dialogAdmPassword.setTitle(DIALOG_ADM_PASS_TITLE);
			dialogAdmPassword.initModality(Modality.WINDOW_MODAL);
			dialogAdmPassword.initOwner(this.root.getScene().getWindow());
			dialogAdmPassword.setScene(new Scene(novoPainel, 300, 190));

			ControleDialogAdmPassword dialAdmPassController = loader.getController();
			dialAdmPassController.setProcesso(processoSelecionado);
			
			dialogAdmPassword.show();
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
		}
	}
	
	@FXML
	private void criarTelaPdf() {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(ARQUIVO_FXML_TELA_VISUALIZAR_PDF);
			Pane novoPainel = loader.load();

			Stage pdfViewerScreen = new Stage();
			pdfViewerScreen.setTitle(VISUALIZAR_PDF);
			pdfViewerScreen.initModality(Modality.WINDOW_MODAL);
			pdfViewerScreen.initOwner(this.root.getScene().getWindow());
			pdfViewerScreen.setScene(new Scene(novoPainel, 820, 660));

			PdfViewerController pdfViewerController = loader.getController();
			pdfViewerController.engineConfigurations();
			pdfViewerController.setVisualizedProcess(processoSelecionado);
			
			pdfViewerScreen.show();
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

			ControleTelaBusca controleTelaBusca = loader.getController();
			controleTelaBusca.configurarFechamento();
			
			telaBusca.show();
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
		}
	}
	
	private void atualizarTabela() {
		try {
			List<Process> lista = this.processService.pullList();
			tabelaProcessosOficios.getItems().setAll(lista);
		} catch (ValidationException | DatabaseException e) {
			// TODO Alert Banco de Dados)
			logger.error(e.getMessage(), e);
		}
		
	}
	
	protected abstract void configurarTabela();
	
	public abstract URL getFxmlPath();
}
