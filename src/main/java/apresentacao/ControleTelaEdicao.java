package apresentacao;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

import org.apache.log4j.Logger;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import negocio.dominio.Interessado;
import negocio.dominio.Processo;
import negocio.fachada.FachadaCaixasDeEscolha;
import negocio.fachada.FachadaNegocio;
import utils.widget.MaskedTextField;

/**
 * @author hugotho
 * 
 */
public class ControleTelaEdicao implements Initializable {
	
	private Logger logger = Logger.getLogger(ControleTelaEdicao.class);

	private static final URL ARQUIVO_FXML_DIALOG_INTERESSADO = ControleTelaPrincipal.class.getResource("/visoes/dialog_editar_interessado.fxml");
	private static final String CHOICEBOX_TEXTO_PADRAO = "-- SELECIONE --";
	private static final String LABEL_BTN_ATUALIZAR = "Atualizar"; 
	private static final String LABEL_BTN_EDITAR_INTERESSADO = "Editar"; 
	private static final String LABEL_BTN_LIMPAR_INTERESSADO = "Limpar"; 
	private static final String MASCARA_NUM_OFICIO = "####/####";
	private static final String MASCARA_NUM_PROCESSO = "#######.########/####-##";
	private static final String MASCARA_CONTATO_8DIGITOS = "(##) ####-####";
	private static final String MASCARA_CONTATO_9DIGITOS = "(##) #####-####";

	private FachadaCaixasDeEscolha fachada;
	private Processo processoOriginal;
	private Interessado interessado;

	@FXML
	private VBox raiz;

	@FXML
	private RadioButton rbProcesso;

	@FXML
	private RadioButton rbOficio;

	@FXML
	private ToggleGroup tgProcessoOficio;

	@FXML
	private Label lblTipoProcesso;

	@FXML
	private Label lblNumProcesso;

	@FXML
	private MaskedTextField txtNumProcesso;

	@FXML
	private HBox hBoxInteressado;
	
	@FXML
	private Label lblCpfInteressado;
	
	@FXML
	private MaskedTextField txtCpfInteressado;
	
	@FXML
	private Button btnBuscarInteressado;
	
	@FXML
	private Label lblTxtNomeInteressado;
	
	@FXML
	private Label lblTxtContatoInteressado; 

	@FXML
	private ChoiceBox<String> cbOrgao;

	@FXML
	private ChoiceBox<String> cbAssunto;

	@FXML
	private ChoiceBox<String> cbSituacao;

	@FXML
	private TextArea txtObservacao;

	@FXML
	private Button btnCancelar;

	@FXML
	private Button btnCadastrar;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		this.processoOriginal = null;
		this.interessado = null;
		this.fachada = FachadaNegocio.getInstance();
		this.configurarRadioButtons();
		this.preencherChoiceBoxes();
		this.configurarChoiceBoxOrgao();
	}
	
	public void montarFormulario(Processo processo) {
		if (processo != null) {
			this.processoOriginal = processo;
			this.btnCadastrar.setText(LABEL_BTN_ATUALIZAR);

			if (processo.isTipoOficio()) {
				this.rbOficio.setSelected(true);
				this.rbProcesso.setDisable(true);
				this.cbOrgao.setDisable(true);
			} else {
				this.rbOficio.setDisable(true);
			}
			this.cbOrgao.getSelectionModel().select(processo.getUnidadeOrigem().ordinal());
			this.txtNumProcesso.setPlainText(processo.getNumero());
			this.txtNumProcesso.setDisable(true);
			
			this.interessado = processo.getInteressado();
			this.preencherInteressado();
			
			this.cbAssunto.getSelectionModel().select(processo.getAssunto().ordinal());
			this.cbSituacao.getSelectionModel().select(processo.getSituacao().ordinal());
			this.txtObservacao.setText(processo.getObservacao());
		}
	}

    private void alterarFormulario (Toggle novoValor) {
		if (novoValor != null) {
			RadioButton radio = (RadioButton)novoValor;
			this.lblTipoProcesso.setText(radio.getText());

			if(Objects.equals(radio.getText(), this.rbProcesso.getText())) {
				this.txtNumProcesso.setMask(MASCARA_NUM_PROCESSO);
			} else {
				if (cbOrgao.getSelectionModel().getSelectedIndex() != 0) {
					this.txtNumProcesso.setMask(MASCARA_NUM_OFICIO + "-" +
							cbOrgao.getSelectionModel().getSelectedItem().split(" - ")[0]);
				} else {
					this.txtNumProcesso.setMask(MASCARA_NUM_OFICIO);
				}
			}
		}
	}
    
    private void preencherInteressado() {
    	
    	this.txtCpfInteressado.setPlainText(this.interessado.getCpf());
		this.txtCpfInteressado.setDisable(true);
		
		this.hBoxInteressado.getChildren().remove(btnBuscarInteressado);
		
		Button btnEditarInteressado = new Button(LABEL_BTN_EDITAR_INTERESSADO);
		btnEditarInteressado.setOnAction((evento) -> this.editarInteressado());
		Button btnLimparInteressado = new Button(LABEL_BTN_LIMPAR_INTERESSADO);
		btnLimparInteressado.setOnAction((evento) -> this.limparInteressado());
		
		this.hBoxInteressado.getChildren().addAll(btnEditarInteressado, btnLimparInteressado);
		
		this.lblTxtNomeInteressado.setText(this.interessado.getNome());
		String contato = this.interessado.getContato();
		MaskedTextField contatoMascara;
		if(contato.length() == 11) {
			contatoMascara = new MaskedTextField(MASCARA_CONTATO_9DIGITOS);
		} else {
			contatoMascara = new MaskedTextField(MASCARA_CONTATO_8DIGITOS);
		}
		contatoMascara.setPlainText(contato);			
		this.lblTxtContatoInteressado.setText(contatoMascara.getText());
    }
    
    private void editarInteressado() {
    }
    
    private void criarNovoInteressado() {
	}

	private void criarDialogEdicao(String titulo) {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(ARQUIVO_FXML_DIALOG_INTERESSADO);
			Pane novoPainel = loader.load();

			Stage dialogEdicao = new Stage();
			dialogEdicao.setTitle(titulo);
			dialogEdicao.initModality(Modality.WINDOW_MODAL);
			dialogEdicao.initOwner(this.raiz.getScene().getWindow());
			dialogEdicao.setScene(new Scene(novoPainel, 400, 260));

			ControleDialogInteressado controleDialog = loader.getController();
			
			dialogEdicao.show();
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
		}
	}

	private void mostrarTelaEdicao() {
		
	}
    
    private void limparInteressado() {
    	this.interessado = null;
    	
    	this.hBoxInteressado.getChildren().clear();
    	this.hBoxInteressado.getChildren().addAll(this.lblCpfInteressado, this.txtCpfInteressado, this.btnBuscarInteressado);
    	this.txtCpfInteressado.setDisable(false);
    	this.txtCpfInteressado.clear();
    	this.lblTxtNomeInteressado.setText("");
    	this.lblTxtContatoInteressado.setText("");
    }
    
    private void configurarRadioButtons() {
		this.tgProcessoOficio.selectedToggleProperty().addListener(
				(valorObservavel, anterior, novo) -> alterarFormulario(novo));
	}

	private void configurarChoiceBoxOrgao() {
		cbOrgao.getSelectionModel().selectedItemProperty().addListener(
				(valorObservado, valorAntigo, valorNovo) -> { 
					if (rbOficio.isSelected()) {
						if (valorNovo.equalsIgnoreCase(CHOICEBOX_TEXTO_PADRAO)) {
							this.txtNumProcesso.setMask(MASCARA_NUM_OFICIO);
						} else {
							this.txtNumProcesso.setMask(MASCARA_NUM_OFICIO + "-" + valorNovo.split(" - ")[0]);
						}
					}
				});
	}

	private void preencherChoiceBoxes() {
		ObservableList<String> obsListaOrgaos = this.cbOrgao.getItems();
		obsListaOrgaos.add(CHOICEBOX_TEXTO_PADRAO);
		obsListaOrgaos.addAll(fachada.getListaOrgaos());
		this.cbOrgao.getSelectionModel().select(0);

		ObservableList<String> obsListaAssuntos = this.cbAssunto.getItems();
		obsListaAssuntos.add(CHOICEBOX_TEXTO_PADRAO);
		obsListaAssuntos.addAll(fachada.getListaAssuntos());
		this.cbAssunto.getSelectionModel().select(0);

		ObservableList<String> obsListaSituacoes = this.cbSituacao.getItems();
		obsListaSituacoes.add(CHOICEBOX_TEXTO_PADRAO);
		obsListaSituacoes.addAll(fachada.getListaSituacoes());
		this.cbSituacao.getSelectionModel().select(0);
	}
	
	@FXML
	public void buscarPorCpf() {
		try {
			this.interessado = fachada.buscarPorCpf(this.txtCpfInteressado.plainTextProperty().getValue());
			this.preencherInteressado();
		} catch(Exception e) {
			//TODO Tratar Exceções de CPF mau preenchido e de CPF não encontrado
		}
	}

	@FXML
	private void fecharJanela() {
		Stage janela = (Stage) this.raiz.getScene().getWindow();
		if (janela != null)
			janela.close();
	}

	@FXML
	private void cadastrar() {
		if (processoOriginal == null) {	// Criando novo processo
			Processo novoProcesso = new Processo();
			/**
				this.processo,
				this.rbOficio.isSelected(),
				this.txtNumProcesso.getText(),
				this.txtNomeInteressado.getText(),
				this.txtCpfInteressado.plainTextProperty().getValue(),
				this.txtContatoInteressado.getText(),
				this.cbOrgao.getSelectionModel().getSelectedIndex(),
				this.cbAssunto.getSelectionModel().getSelectedIndex(),
				this.cbSituacao.getSelectionModel().getSelectedIndex(),
				this.txtObservacao.getText());
			 */
			fachada.salvar(novoProcesso);
			
			
		} else {				// Atualizando existente	
			Processo processoEditado = new Processo();
			/**
				this.processo,
				this.rbOficio.isSelected(),
				this.txtNumProcesso.getText(),
				this.txtNomeInteressado.getText(),
				this.txtCpfInteressado.plainTextProperty().getValue(),
				this.txtContatoInteressado.getText(),
				this.cbOrgao.getSelectionModel().getSelectedIndex(),
				this.cbAssunto.getSelectionModel().getSelectedIndex(),
				this.cbSituacao.getSelectionModel().getSelectedIndex(),
				this.txtObservacao.getText());
			 */
			fachada.atualizar(processoEditado);
		}

		this.fecharJanela();
	}
}
