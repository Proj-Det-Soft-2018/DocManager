package presentation;

import java.net.URL;

import org.apache.log4j.Logger;

import business.model.Search;
import business.service.ListService;
import business.service.ProcessService;
import health.model.HealthProcess;
import health.presentation.HealthSearchScreenCtrl;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.VBox;
import presentation.utils.widget.DynamicMaskTextField;
import presentation.utils.widget.MaskedTextField;

public class JuridicalSearchScreenCtrl extends SearchScreenCtrl {
	
	private static final URL FXML_PATH = HealthSearchScreenCtrl.class.getResource("/visions/juridical_process_search_screen.fxml");
	private static final Logger LOGGER = Logger.getLogger(HealthSearchScreenCtrl.class);
	
	private ListService listService;
	
	private MaskedTextField mTxtCpf;
	
	@FXML
	private VBox vbNumero;

	@FXML
	private VBox vbInteressado;

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
	private MaskedTextField mTxtProcessoNum;

	@FXML
	private TextField txtNome;

	@FXML
	private ChoiceBox<String> choiceOrgao;

	@FXML
	private ChoiceBox<String> choiceAssunto;

	@FXML
	private ChoiceBox<String> choiceSituacao;

	@FXML
	private TableColumn<HealthProcess, String> tabColTipo;

	@FXML
	private TableColumn<HealthProcess, String> tabColNumero;

	@FXML
	private TableColumn<HealthProcess, String> tabColInteressado;

	@FXML
	private TableColumn<HealthProcess, String> tabColSituacao;

    @FXML
    private TableColumn<HealthProcess, String> tabColumnRegDate;
    
    
	
	public JuridicalSearchScreenCtrl(ProcessService processService, ListService listService,
	        ControllerFactory controllerFactory) {
		super(controllerFactory, processService, LOGGER);
		this.listService = listService;
        
        /* Inicializa os campos de CPF                     */
        mTxtCpf = new MaskedTextField("###.###.###-##");
        mTxtCpf.setMaxWidth(520.0);
        
	}
	
	@FXML
	private void limparFormulario() {
		checkNumero.setSelected(false);
		checkInteressado.setSelected(false);
		checkOrgao.setSelected(false);
		checkAssunto.setSelected(false);
		checkSituacao.setSelected(false);
		radioProcesso.setSelected(true);
		radioNome.setSelected(true);
		choiceOrgao.getSelectionModel().select(0);
		choiceAssunto.getSelectionModel().select(0);
		choiceSituacao.getSelectionModel().select(0);
		txtNome.clear();
		mTxtProcessoNum.clear();
		mTxtCpf.clear();
	}
	
	@Override
	protected void configureForm() {
		preencherChoiceBoxes();
		//configurarRadioButtons();
		//configurarChoiceBoxOrgao();
//		configurarChoiceBoxAssunto();
//		configurarChoiceBoxSituacao();
//		configurarCheckBoxCourt();
//		configurarTextFieldsNumeroProcesso();
//		configurarTextFieldsInteressado();

	}

	

	private void preencherChoiceBoxes() {
		ObservableList<String> obsListaOrgaos = choiceOrgao.getItems();
		obsListaOrgaos.addAll(listService.getOrganizationsList());
		choiceOrgao.getSelectionModel().select(0);

		ObservableList<String> obsListaAssuntos = choiceAssunto.getItems();
		obsListaAssuntos.addAll(listService.getSubjectsDescritionList());
		choiceAssunto.getSelectionModel().select(0);

		ObservableList<String> obsListaSituacoes = choiceSituacao.getItems();
		obsListaSituacoes.addAll(listService.getSituationsDescritionList());
		choiceSituacao.getSelectionModel().select(0);
	}

	@Override
	protected Search mountSearch() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected void configureColumns() {
		// TODO Auto-generated method stub

	}

	@Override
	public URL getFxmlPath() {
		// TODO Auto-generated method stub
		return null;
	}

}
