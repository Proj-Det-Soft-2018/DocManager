package apresentacao;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import org.apache.log4j.Logger;

import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import negocio.fachada.FachadaArmazenamento;
import negocio.fachada.FachadaNegocio;
import negocio.servico.Observador;
import negocio.dominio.Processo;
import utils.widget.MaskedTextField;

/**
 * @author hugotho
 * 
 */
public class ControleTelaPrincipal implements Initializable, Observador {

	private static final URL ARQUIVO_FXML_TELA_EDICAO = ControleTelaPrincipal.class.getResource("/visoes/tela_editar_processo.fxml");
	private static final String CRIAR_PROCESSO = "Novo Processo / Ofício";
	private static final String EDITAR_PROCESSO = "Editar Processo";
	private static final String MASCARA_NUM_OFICIO = "####/####";
	private static final String MASCARA_NUM_PROCESSO = "#####.######/####-##";

	private FachadaArmazenamento fachada;
	private Processo processoSelecionado;

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
	private TableView<Processo> tabelaProcessosOficios;

	@FXML
	private TableColumn<Processo, String> tabColunaTipo;

	@FXML
	private TableColumn<Processo, String> tabColunaNumero;

	@FXML
	private TableColumn<Processo, String> tabColunaInteressado;

	@FXML
	private TableColumn<Processo, String> tabColunaSituacao;

	private Logger logger = Logger.getLogger(ControleTelaPrincipal.class);

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		this.processoSelecionado = null;
		this.fachada = FachadaNegocio.getInstance();
		this.fachada.cadastrarObservador(this);
		
		this.configurarTabela();
		this.atualizarTabela(this.fachada.buscarListaProcessos());
	}
	
	@Override
	public void atualizar() {
		this.atualizarTabela(this.fachada.buscarListaProcessos());
	}

	@FXML
	private void criarFormularioNovo() {
		this.criarTelaEdicao(CRIAR_PROCESSO, null);
	}

	@FXML
	private void criarFormularioEdicao() {
		this.criarTelaEdicao(EDITAR_PROCESSO, processoSelecionado);
	}

	private void criarTelaEdicao(String titulo, Processo processo) {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(ARQUIVO_FXML_TELA_EDICAO);
			Pane novoPainel = loader.load();

			Stage novaTelaEdicao = new Stage();
			novaTelaEdicao.setTitle(titulo);
			novaTelaEdicao.initModality(Modality.WINDOW_MODAL);
			novaTelaEdicao.initOwner(this.painel.getScene().getWindow());
			novaTelaEdicao.setScene(new Scene(novoPainel, 720, 540));

			ControleTelaEdicao controleTelaEdicao = loader.getController();
			controleTelaEdicao.montarFormulario(processo);
			
			novaTelaEdicao.show();
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
		}
	}

	private void configurarTabela() {
		// inicia as colunas
		tabColunaTipo.setCellValueFactory(
				conteudo -> new ReadOnlyStringWrapper(conteudo.getValue().getTipo()));
		tabColunaNumero.setCellValueFactory(
				conteudo -> {
					String mascara;
					if(conteudo.getValue().isTipoOficio()) {
						mascara = MASCARA_NUM_OFICIO + "-" + conteudo.getValue().getUnidadeOrigem().name();
					} else {
						mascara = MASCARA_NUM_PROCESSO;
					}
					MaskedTextField numProcessoMascara = new MaskedTextField(mascara);
					numProcessoMascara.setPlainText(conteudo.getValue().getNumero());
					
					return new ReadOnlyStringWrapper(numProcessoMascara.getText());
				});
		tabColunaInteressado.setCellValueFactory(
				conteudo -> new ReadOnlyStringWrapper(conteudo.getValue().getInteressado().getNome()));
		tabColunaSituacao.setCellValueFactory(
				conteudo -> new ReadOnlyStringWrapper(conteudo.getValue().getSituacao().getStatus()));

		// eventHandle para detectar o processo selecionado
		tabelaProcessosOficios.getSelectionModel().selectedItemProperty().addListener(
				(observavel, selecionandoAnterior, selecionadoNovo) -> {
					this.processoSelecionado = selecionadoNovo;
					this.btnVerEditar.setDisable(selecionadoNovo!=null? false : true);
				});
	}

	private void atualizarTabela(List<Processo> lista) {
		tabelaProcessosOficios.getItems().setAll(lista);
	}
}
