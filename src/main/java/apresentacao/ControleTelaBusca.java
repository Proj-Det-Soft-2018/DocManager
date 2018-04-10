package apresentacao;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;

import org.apache.log4j.Logger;

import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import negocio.dominio.Processo;
import negocio.fachada.FachadaCaixasDeEscolha;
import negocio.fachada.FachadaNegocio;
import negocio.servico.Observador;
import negocio.servico.ValidationException;
import utils.widget.DynamicMaskTextField;
import utils.widget.MaskedTextField;

public class ControleTelaBusca implements Initializable, Observador {
	
	private static Logger logger = Logger.getLogger(ControleTelaBusca.class);

	private static final URL ARQUIVO_FXML_TELA_EDICAO = ControleTelaPrincipal.class.getResource("/visoes/tela_editar_processo.fxml");
	private static final URL ARQUIVO_FXML_DIALOG_PASSWORD = ControleTelaPrincipal.class.getResource("/visoes/dialog_adm_password.fxml");
	private static final String CHOICEBOX_TEXTO_PADRAO = "-- SELECIONE --";
	private static final String MASCARA_NUM_OFICIO = "####/####-";
	private static final String MASCARA_NUM_PROCESSO = "#####.######/####-##";
	private static final String DIALOG_ADM_PASS_TITLE = "Autorização";
	private static final String EDITAR_PROCESSO = "Editar Processo";
	private static final int NUM_OFICIO_OFFSET = 8;
	private static final String MASCARA_CPF = "###.###.###-##";
	private static final double TEXTFIELD_MAX_WIDTH = 520.0;
	
	private FachadaCaixasDeEscolha fachada;
	private Processo processoSelecionado;
	private MaskedTextField mTxtCpf;
	private DynamicMaskTextField dmTxtOficioNum;
	private UltimaBusca ultimaBusca;
	
	@FXML
	private Node root;
	
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
		this.fachada.cadastrarObservador(this);
		this.processoSelecionado = null;
		this.ultimaBusca = null;
		this.mTxtCpf = new MaskedTextField(MASCARA_CPF);
		this.mTxtCpf.setMaxWidth(TEXTFIELD_MAX_WIDTH);
		this.dmTxtOficioNum = new DynamicMaskTextField(MASCARA_NUM_OFICIO + "*", NUM_OFICIO_OFFSET+1);
		this.dmTxtOficioNum.setMaxWidth(TEXTFIELD_MAX_WIDTH);
		preencherChoiceBoxes();
		configurarRadioButtons();
		configurarChoiceBoxOrgao();
		configurarCheckBoxOrgao();
		configurarTabela();
	}
	
	@Override
	public void atualizar() {
		if (this.ultimaBusca != null) {
			List<Processo> resultado = this.fachada.buscarProcessos(
					ultimaBusca.numero,
					ultimaBusca.nomeInteressado,
					ultimaBusca.cpfInteressado,
					ultimaBusca.idSituacao,
					ultimaBusca.idOrgao,
					ultimaBusca.idAssunto);
			
			atualizarTabela(resultado);
		}
	}
	
	@FXML
	private void buscar() {
		String numProcesso = "";
		if (checkNumero.isSelected()) {
			if (radioProcesso.isSelected()) {
				numProcesso = mTxtProcessoNum.plainTextProperty().getValue();
			}
			else {
				numProcesso = dmTxtOficioNum.plainTextProperty().getValue();
				if (checkOrgao.isSelected() && choiceOrgao.getSelectionModel().getSelectedIndex() != 0) {
					numProcesso += choiceOrgao.getSelectionModel().getSelectedItem().split("-")[0];
				}
			}
		}
		
		String nomeInteressado = "";
		String cpfInteressado = "";
		if (checkInteressado.isSelected()) {
			if (radioNome.isSelected()) {
				nomeInteressado = txtNome.getText();
			}
			else {
				cpfInteressado = mTxtCpf.plainTextProperty().getValue();
			}
		}
		
		int idOrgao = checkOrgao.isSelected()? choiceOrgao.getSelectionModel().getSelectedIndex() : 0;
		int idAssunto = checkAssunto.isSelected()? choiceAssunto.getSelectionModel().getSelectedIndex() : 0;
		int idSituacao = checkSituacao.isSelected()? choiceSituacao.getSelectionModel().getSelectedIndex() : 0;
		
		try {
			List<Processo> resultado = this.fachada.buscarProcessos(numProcesso, nomeInteressado, cpfInteressado, idSituacao, idOrgao, idAssunto);
			this.ultimaBusca = new UltimaBusca(numProcesso, nomeInteressado, cpfInteressado, idOrgao, idAssunto, idSituacao);
			atualizarTabela(resultado);
		} catch (ValidationException ve) {
			Alert alert = new Alert(AlertType.ERROR, ve.getMessage() + "\n\n");
			alert.getDialogPane().getChildren().stream().filter(node -> node instanceof Label).forEach(
					node -> {
						((Label)node).setMinHeight(Region.USE_PREF_SIZE);
						((Label)node).setTextFill(Color.RED);
					});
			alert.setHeaderText(null);
			alert.setGraphic(null);
	        alert.initOwner(root.getScene().getWindow());

	        alert.showAndWait();
		}
	}
	
	@FXML
	private void limpar() {
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
		dmTxtOficioNum.clear();
		dmTxtOficioNum.setDynamic(true);
	}
	
	@FXML
	private void fecharJanela() {
		Stage window = (Stage) this.root.getScene().getWindow();
		if (window != null)
			window.close();
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

								if (oldText.length() == NUM_OFICIO_OFFSET) {
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
							if (oldText.length() == NUM_OFICIO_OFFSET) {
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
	
	private void configurarTabela() {
		// inicia as colunas
		tabColTipo.setCellValueFactory(
				conteudo -> new ReadOnlyStringWrapper(conteudo.getValue().getTipo()));
		tabColNumero.setCellValueFactory(
				conteudo -> {
					String rawText = conteudo.getValue().getNumero();
					MaskedTextField numProcessoMascara;
					StringBuilder finalText;
					if(conteudo.getValue().isTipoOficio()) {
						numProcessoMascara = new MaskedTextField(MASCARA_NUM_OFICIO);
						numProcessoMascara.setPlainText(rawText);
						finalText = new StringBuilder(numProcessoMascara.getText());
						finalText.append(rawText.substring(NUM_OFICIO_OFFSET));
					} else {
						numProcessoMascara = new MaskedTextField(MASCARA_NUM_PROCESSO);
						numProcessoMascara.setPlainText(rawText);
						finalText = new StringBuilder(numProcessoMascara.getText());
					}
					return new ReadOnlyStringWrapper(finalText.toString());
				});
		tabColInteressado.setCellValueFactory(
				conteudo -> new ReadOnlyStringWrapper(conteudo.getValue().getInteressado().getNome()));
		tabColSituacao.setCellValueFactory(
				conteudo -> new ReadOnlyStringWrapper(conteudo.getValue().getSituacao().getStatus()));

		// eventHandle para detectar o processo selecionado
		tableResultados.getSelectionModel().selectedItemProperty().addListener(
				(observavel, selecionandoAnterior, selecionadoNovo) -> {
					this.processoSelecionado = selecionadoNovo;
					this.btnVerEditar.setDisable(selecionadoNovo!=null? false : true);
					this.btnApagar.setDisable(selecionadoNovo!=null? false : true);
				});
	}
	
	public void configurarFechamento() {
		this.root.getScene().getWindow().setOnHidden(
				event -> this.fachada.descadastrarObservador(this)
				);
	}
	
	private void atualizarTabela(List<Processo> lista) {
		tableResultados.getItems().setAll(lista);
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
			dialAdmPassController.setProcesso(this.processoSelecionado);
			
			dialogAdmPassword.show();
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
		}
	}
	
	@FXML
	private void criarTelaEdicao() {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(ARQUIVO_FXML_TELA_EDICAO);
			Pane novoPainel = loader.load();

			Stage telaEdicao = new Stage();
			telaEdicao.setTitle(EDITAR_PROCESSO);
			telaEdicao.initModality(Modality.WINDOW_MODAL);
			telaEdicao.initOwner(this.root.getScene().getWindow());
			telaEdicao.setScene(new Scene(novoPainel, 720, 540));

			ControleTelaEdicao controleTelaEdicao = loader.getController();
			controleTelaEdicao.montarFormulario(this.processoSelecionado);
			
			telaEdicao.show();
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
		}
	}
	
	/* Estrutura de Dados para armazenar a ultima busca */
	private class UltimaBusca {
	    private String numero; 
	    private String nomeInteressado;
	    private String cpfInteressado;
	    private int idOrgao;
	    private int idAssunto;
	    private int idSituacao; 
	    
	    public UltimaBusca(String num, String nomeInter, String cpfInter, int idOrg, int idAss, int idSit) {
	    	numero = num; 
	    	nomeInteressado = nomeInter;
	    	cpfInteressado = cpfInter;
	    	idOrgao = idOrg;
	    	idAssunto = idAss;
	    	idSituacao = idSit;
	    }
	 }
}
