package apresentacao;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import org.apache.log4j.Logger;

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

public class ControleTelaPrincipal implements Initializable {
	
	private static final URL ARQUIVO_FXML = ControleTelaPrincipal.class.getResource("/visoes/tela_edicao.fxml");
	private static final String TITULO_NOVO_DOCUMENTO = "Novo Processo / Ofício";
	private static final String TITULO_EDITAR_DOCUMENTO = "Ver / Editar";
	
	//private FachadaArmazenamento fachada = new FachadaGerenciadorProcesso(); 
	
	private Stage novaTelaEdicao;
	private ControleTelaEdicao controleTelaEdicao;
	
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
	private TableView<Documento> tabelaProcessosOficios;
	
	@FXML
	private TableColumn<Documento, String> tabColunaTipo;
	
	@FXML
	private TableColumn<Documento, String> tabColunaNumero;
	
	@FXML
	private TableColumn<Documento, String> tabColunaInteressado;
	
	@FXML
	private TableColumn<Documento, String> tabColunaSituacao;
	
	private Logger logger = Logger.getLogger(ControleTelaPrincipal.class);
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO iniciar colunas
	}
	
	public void criarDocumento() {
		
		this.criarTelaEdicao(TITULO_NOVO_DOCUMENTO);
		this.controleTelaEdicao.montarFormulario(null);
		this.mostrarTelaEdicao();
	}
	
	public void editarDocumento() {
		this.criarTelaEdicao(TITULO_EDITAR_DOCUMENTO);
		//TODO pegar documento da tabela;
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
}
