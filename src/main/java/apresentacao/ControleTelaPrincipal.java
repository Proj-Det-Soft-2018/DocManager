package apresentacao;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import org.apache.log4j.Logger;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import negocio.facade.FachadaGerenciadorDocumento;
import negocio.servico.Observador;

/**
 * @author hugotho
 * 
 */
public class ControleTelaPrincipal implements Initializable, Observador {

	private static final URL ARQUIVO_FXML = ControleTelaPrincipal.class.getResource("/visoes/tela_edicao.fxml");
	private static final String TITULO_NOVO_DOCUMENTO = "Novo Processo / Ofício";
	private static final String TITULO_EDITAR_DOCUMENTO = "Ver / Editar";

	private FachadaArmazenamento fachada = new FachadaGerenciadorDocumento(); 

	private Stage novaTelaEdicao;
	private ControleTelaEdicao controleTelaEdicao;

	private DocumentoVisao documentoSelecionado = null;

	@FXML
	private Pane painel;

	@FXML
	private Button btnNovo;

	@FXML
	private Button btnVerEditar;

	@FXML
	private Button btnApagar;

	@FXML
	private Button btnBuscar;

	@FXML
	private TableView<DocumentoVisao> tabelaProcessosOficios;

	@FXML
	private TableColumn<DocumentoVisao, String> tabColunaTipo;

	@FXML
	private TableColumn<DocumentoVisao, String> tabColunaNumero;

	@FXML
	private TableColumn<DocumentoVisao, String> tabColunaInteressado;

	@FXML
	private TableColumn<DocumentoVisao, String> tabColunaSituacao;

	private Logger logger = Logger.getLogger(ControleTelaPrincipal.class);

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		this.configurarTabela();
		this.atualizarTabela(this.fachada.getListaDocumentos());
		this.fachada.cadastrarObservador(this);
	}

	@FXML
	private void criarFormularioNovo() {
		this.criarTelaEdicao(TITULO_NOVO_DOCUMENTO);
		this.controleTelaEdicao.montarFormulario(null);
		this.mostrarTelaEdicao();
	}

	@FXML
	private void criarFormularioEdicao() {
		this.criarTelaEdicao(TITULO_EDITAR_DOCUMENTO);
		this.controleTelaEdicao.montarFormulario(documentoSelecionado);
		this.mostrarTelaEdicao();	
	}

	private void criarTelaEdicao(String titulo) {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(ARQUIVO_FXML);
			Pane novoPainel = loader.load();

			this.novaTelaEdicao = new Stage();
			this.novaTelaEdicao.setTitle(titulo);
			this.novaTelaEdicao.initModality(Modality.WINDOW_MODAL);
			this.novaTelaEdicao.initOwner(painel.getScene().getWindow());
			this.novaTelaEdicao.setScene(new Scene(novoPainel, 720, 540));

			this.controleTelaEdicao = loader.getController();
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
		}
	}

	private void mostrarTelaEdicao() {
		this.novaTelaEdicao.show();
	}

	private void configurarTabela() {
		// inicia as colunas
		tabColunaTipo.setCellValueFactory(new PropertyValueFactory<>("tipo"));
		tabColunaNumero.setCellValueFactory(new PropertyValueFactory<>("numDocumento"));
		tabColunaInteressado.setCellValueFactory(new PropertyValueFactory<>("nomeInteressado"));
		tabColunaSituacao.setCellValueFactory(new PropertyValueFactory<>("situacao"));

		// eventHandle para detectar o documento selecionado
		tabelaProcessosOficios.getSelectionModel().selectedItemProperty().addListener(
				(observavel, selecionandoAnterior, selecionadoNovo) -> {
					this.documentoSelecionado = selecionadoNovo;
					this.btnVerEditar.setDisable(selecionadoNovo!=null? false : true);
				});
	}

	private void atualizarTabela(List<? extends DocumentoVisao> lista) {
		tabelaProcessosOficios.getItems().setAll(lista);
	}
	
	@Override
	public void atualizar() {
		this.atualizarTabela(this.fachada.getListaDocumentos());
	}
}
