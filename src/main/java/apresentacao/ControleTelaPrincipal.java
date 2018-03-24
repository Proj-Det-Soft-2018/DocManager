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
import javafx.scene.control.TableView;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class ControleTelaPrincipal implements Initializable {
	
	private static final URL ARQUIVO_FXML = ControleTelaPrincipal.class.getResource("/visoes/tela_edicao.fxml");
	
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

	
	private Logger logger = Logger.getLogger(ControleTelaPrincipal.class);
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		//REMOVER WARNING
	}
	
	public void novoDocumento() {
		
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(ARQUIVO_FXML);
			Pane novoPainel = loader.load();
			
			Stage novoPalco = new Stage();
			novoPalco.setTitle("Novo Processo / Ofício");
			novoPalco.initModality(Modality.WINDOW_MODAL);
		    novoPalco.initOwner(painel.getScene().getWindow());
		    novoPalco.setScene(new Scene(novoPainel, 720, 540));
		    
		    ControleTelaEdicao controllerEdicao = loader.getController();
			controllerEdicao.setMessage("Novo...");
			
			novoPalco.show();
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
		}
	}
	
	public void editarDocumento() {

		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(ARQUIVO_FXML);
			Pane novoPainel = loader.load();
			
			Stage novoPalco = new Stage();
			novoPalco.setTitle("Novo Processo / Ofício");
			novoPalco.initModality(Modality.WINDOW_MODAL);
		    novoPalco.initOwner(painel.getScene().getWindow());
		    novoPalco.setScene(new Scene(novoPainel, 720, 540));
		    
		    ControleTelaEdicao controllerEdicao = loader.getController();
			controllerEdicao.setMessage("Antigo");
			
			novoPalco.show();
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
		}
	}
}
