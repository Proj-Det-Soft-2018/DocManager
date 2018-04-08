package apresentacao;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import negocio.dominio.Processo;
import negocio.fachada.FachadaCaixasDeEscolha;
import negocio.fachada.FachadaNegocio;

public class ControleTelaBusca implements Initializable {
	
	private static final String CHOICEBOX_TEXTO_PADRAO = "-- SELECIONE --";
	private static final String MASCARA_NUM_OFICIO = "####/####-******";
	private static final String MASCARA_NUM_PROCESSO = "#####.######/####-##";
	
	private FachadaCaixasDeEscolha fachada;
	
	@FXML
	private CheckBox checkNumero;
	
	@FXML
	private CheckBox checkInteressado;
	
	@FXML
	private CheckBox checkOrgao;
	
	@FXML
	private CheckBox checkAssunto;
	
	@FXML
	private CheckBox checkSituacao;
	
	@FXML
	private ToggleGroup tgProcessoOficio;
	
	@FXML
	private ToggleGroup tgNomeCpf;
	
	@FXML
	private RadioButton radioProcesso;
	
	@FXML
	private RadioButton radioOficio;

	@FXML
	private RadioButton radioNome;

	@FXML
	private RadioButton radioCpf;
	
	@FXML
	private TextField txtNumero;
	
	@FXML
	private TextField txtInteressado;
	
	@FXML
	private ChoiceBox<String> choiceOrgao;
	
	@FXML
	private ChoiceBox<String> choiceAssunto;
	
	@FXML
	private ChoiceBox<String> choiceSituacao;
	
	@FXML
	private TableView<Processo> tableResultados;
	
	@FXML
	private TableColumn<Processo, String> tabColTipo;
	
	@FXML
	private TableColumn<Processo, String> tabColNumero;

	@FXML
	private TableColumn<Processo, String> tabColInteressado;

	@FXML
	private TableColumn<Processo, String> tabColSituacao;
	
	@FXML
	private Button btnVerEditar;
	
	@FXML
	private Button btnApagar;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		this.fachada = FachadaNegocio.getInstance();
		this.preencherChoiceBoxes();
	}
	
	private void preencherChoiceBoxes() {
		ObservableList<String> obsListaOrgaos = this.choiceOrgao.getItems();
		obsListaOrgaos.add(CHOICEBOX_TEXTO_PADRAO);
		obsListaOrgaos.addAll(fachada.getListaOrgaos());
		this.choiceOrgao.getSelectionModel().select(0);

		ObservableList<String> obsListaAssuntos = this.choiceAssunto.getItems();
		obsListaAssuntos.add(CHOICEBOX_TEXTO_PADRAO);
		obsListaAssuntos.addAll(fachada.getListaAssuntos());
		this.choiceAssunto.getSelectionModel().select(0);

		ObservableList<String> obsListaSituacoes = this.choiceSituacao.getItems();
		obsListaSituacoes.add(CHOICEBOX_TEXTO_PADRAO);
		obsListaSituacoes.addAll(fachada.getListaSituacoes());
		this.choiceSituacao.getSelectionModel().select(0);
	}

}
