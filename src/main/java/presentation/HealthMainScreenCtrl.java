package presentation;

import java.net.URL;

import org.apache.log4j.Logger;

import business.model.HealthProcess;
import business.service.ProcessService;

import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;

/**
 * @author hugotho
 * 
 */
public class HealthMainScreenCtrl extends MainScreenCtrl {
	
	private static final URL FXML_PATH = MainScreenCtrl.class.getResource("/visions/tela_principal.fxml");
	private static final Logger LOGGER = Logger.getLogger(HealthMainScreenCtrl.class);
	
	@FXML
	private TableColumn<HealthProcess, String> tabColunaTipo;

	@FXML
	private TableColumn<HealthProcess, String> tabColunaNumero;

	@FXML
	private TableColumn<HealthProcess, String> tabColunaInteressado;

	@FXML
	private TableColumn<HealthProcess, String> tabColunaSituacao;

	public HealthMainScreenCtrl(ProcessService processService, ControllerFactory controllerFactory) {
		super(processService, controllerFactory, LOGGER);
	}
	
	@Override
	protected void configurarTabela() {
		// inicia as colunas
		tabColunaTipo.setCellValueFactory(
				conteudo -> new ReadOnlyStringWrapper(conteudo.getValue().getType()));
		tabColunaNumero.setCellValueFactory(
				conteudo -> new ReadOnlyStringWrapper(conteudo.getValue().getFormattedNumber()));
		tabColunaInteressado.setCellValueFactory(
				conteudo -> new ReadOnlyStringWrapper(conteudo.getValue().getIntersted().getName()));
		tabColunaSituacao.setCellValueFactory(
				conteudo -> new ReadOnlyStringWrapper(conteudo.getValue().getSituation().getStatus()));

		// eventHandle para detectar o processo selecionado
		tabelaProcessosOficios.getSelectionModel().selectedItemProperty().addListener(
				(observavel, selecionandoAnterior, selecionadoNovo) -> {
					super.processoSelecionado = selecionadoNovo;
					super.btnVerEditar.setDisable(selecionadoNovo!=null? false : true);
					super.btnCertidaoPdf.setDisable(selecionadoNovo!=null? false : true);
					super.btnApagar.setDisable(selecionadoNovo!=null? false : true);
				});
	}
	
	@Override
	public URL getFxmlPath() {
		return FXML_PATH;
	}
}
