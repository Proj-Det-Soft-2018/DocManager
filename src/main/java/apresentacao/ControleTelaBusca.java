package apresentacao;

import java.net.URL;
import java.util.Objects;
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
import javafx.scene.layout.VBox;
import negocio.dominio.Processo;
import negocio.fachada.FachadaCaixasDeEscolha;
import negocio.fachada.FachadaNegocio;
import utils.widget.DynamicMaskTextField;
import utils.widget.MaskedTextField;

public class ControleTelaBusca implements Initializable {

	private static final String CHOICEBOX_TEXTO_PADRAO = "-- SELECIONE --";
	private static final String MASCARA_NUM_OFICIO = "####/####-";
	private static final int OFFSET_MASCARA_OFICIO = 8;
	private static final String MASCARA_CPF = "###.###.###-##";
	private static final double TEXTFIELD_MAX_WIDTH = 520.0;

	private FachadaCaixasDeEscolha fachada;
	private MaskedTextField mTxtCpf;
	private DynamicMaskTextField dmTxtOficioNum;

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
		this.mTxtCpf = new MaskedTextField(MASCARA_CPF);
		this.mTxtCpf.setMaxWidth(TEXTFIELD_MAX_WIDTH);
		this.dmTxtOficioNum = new DynamicMaskTextField(MASCARA_NUM_OFICIO + "*", OFFSET_MASCARA_OFICIO+1);
		this.dmTxtOficioNum.setMaxWidth(TEXTFIELD_MAX_WIDTH);
		this.preencherChoiceBoxes();
		this.configurarRadioButtons();
		this.configurarChoiceBoxOrgao();
		this.configurarCheckBoxOrgao();
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

	private void configurarRadioButtons() {
		this.tgProcessoOficio.selectedToggleProperty().addListener(
				(observavel, valorAnterior, novoValor) ->  {
					if(Objects.equals(novoValor, this.radioProcesso)) {
						this.vbNumero.getChildren().remove(this.dmTxtOficioNum);
						this.vbNumero.getChildren().add(this.mTxtProcessoNum);
					} else {
						this.vbNumero.getChildren().remove(this.mTxtProcessoNum);
						this.vbNumero.getChildren().add(this.dmTxtOficioNum);
					}

				});
		this.tgNomeCpf.selectedToggleProperty().addListener(
				(observavel, valorAnterior, novoValor) -> {
					if(Objects.equals(novoValor, this.radioNome)) {
						this.vbInteressado.getChildren().remove(this.mTxtCpf);
						this.vbInteressado.getChildren().add(this.txtNome);
					} else {
						this.vbInteressado.getChildren().remove(this.txtNome);
						this.vbInteressado.getChildren().add(this.mTxtCpf);
					}
				});
	}

	private void configurarChoiceBoxOrgao() {
		choiceOrgao.getSelectionModel().selectedItemProperty().addListener(
				(valorObservado, valorAntigo, valorNovo) -> { 
					if (checkOrgao.isSelected()) {
						if (valorNovo.equalsIgnoreCase(CHOICEBOX_TEXTO_PADRAO)) {
							this.dmTxtOficioNum.setDynamic(true);
							if (!valorAntigo.equalsIgnoreCase(CHOICEBOX_TEXTO_PADRAO)) {
								String oldText = dmTxtOficioNum.plainTextProperty().getValue();

								if (oldText.length() == OFFSET_MASCARA_OFICIO) {
									StringBuilder newText = new StringBuilder(oldText);
									newText.append(valorAntigo.split(" - ")[0]);
									dmTxtOficioNum.adjustMask(newText.length());
									dmTxtOficioNum.setPlainText(newText.toString());
								}
							}
						} else {
							this.dmTxtOficioNum.setDynamic(false);
							this.dmTxtOficioNum.setMask(MASCARA_NUM_OFICIO + valorNovo.split(" - ")[0]);
						}
					}
				});
	}
	
	private void configurarCheckBoxOrgao() {
		checkOrgao.selectedProperty().addListener(
				(valorObservado, valorAntigo, valorNovo) -> {
					if (valorNovo) {
						if (choiceOrgao.getSelectionModel().getSelectedIndex() != 0) {
							this.dmTxtOficioNum.setDynamic(false);
							String orgao = choiceOrgao.getSelectionModel().getSelectedItem();
							this.dmTxtOficioNum.setMask(MASCARA_NUM_OFICIO + orgao.split(" - ")[0]);
						}
					} else {
						this.dmTxtOficioNum.setDynamic(true);
						if (choiceOrgao.getSelectionModel().getSelectedIndex() != 0) {
							String oldText = dmTxtOficioNum.plainTextProperty().getValue();
							if (oldText.length() == OFFSET_MASCARA_OFICIO) {
								StringBuilder newText = new StringBuilder(oldText);
								String orgao = choiceOrgao.getSelectionModel().getSelectedItem();
								newText.append(orgao.split(" - ")[0]);
								dmTxtOficioNum.adjustMask(newText.length());
								dmTxtOficioNum.setPlainText(newText.toString());
							}
						}

					}
				});
	}

}
