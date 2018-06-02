package presentation;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.Base64;
import java.util.ResourceBundle;

import org.apache.commons.io.FilenameUtils;
import org.apache.log4j.Logger;

import business.model.Process;
import business.service.ProcessService;
import javafx.concurrent.Worker;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;
import presentation.utils.StringConstants;

public class PdfViewerCtrl implements Initializable {
	
	private static final Logger LOGGER = Logger.getLogger(PdfViewerCtrl.class);
	
	private static final URL FXML_PATH = PdfViewerCtrl.class.getResource("/visions/pdf_viewer.fxml");
	

	private ProcessService processService;
	
	private byte[] pdfData;
	
	@FXML
	private Node root;
	
	@FXML
	private WebView pdfView;

	public static void showPdfView(Window ownerWindow, Process process, ProcessService processService) {
		try {
			FXMLLoader loader = new FXMLLoader(FXML_PATH);
			PdfViewerCtrl pdfViewerController = new PdfViewerCtrl(processService);
			pdfViewerController.setProcess(process);
			loader.setController(pdfViewerController);
			Pane newPane = loader.load();

			Stage pdfViewerScreen = new Stage();
			pdfViewerScreen.setTitle(StringConstants.TITLE_PDF_VIEWER_SCREEN.getText());
			pdfViewerScreen.initModality(Modality.WINDOW_MODAL);
			pdfViewerScreen.initOwner(ownerWindow);
			pdfViewerScreen.setScene(new Scene(newPane, 820, 660));
			
			pdfViewerScreen.show();
		} catch (IOException e) {
			//TODO Alert Erro de geração de tela
			LOGGER.error(e.getMessage(), e);
		}
	}
	
	private PdfViewerCtrl(ProcessService processService) {
		this.processService = processService;
	}
	
	private void setProcess(Process visualizedProcess) {
		pdfData = processService.getPdf(visualizedProcess);
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		configureEngine();
	}

	private void configureEngine() {

		WebEngine engine = pdfView.getEngine();

		// Setando a página para o visualizador de PDF
		String url = getClass().getResource("/pdfjs/web/viewer.html").toExternalForm();
		engine.setJavaScriptEnabled(true);
		engine.load(url);
		
		engine.getLoadWorker().stateProperty().addListener((obs, oldState, newState) -> {
			if (newState == Worker.State.SUCCEEDED) {
				loadPdfFile();
			}
		});
	}
	
	@FXML
	private void closeWindow() {
		Stage janela = (Stage) root.getScene().getWindow();
		if (janela != null)
			janela.close();
	}
	
	@FXML
	private void savePdfFile() {
		FileChooser fileChooser = new FileChooser();
		 
        //Set extension filter for text files
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("arquivos PDF (*.pdf)", "*.pdf");
        fileChooser.getExtensionFilters().add(extFilter);

        //Show save file dialog
        File file = fileChooser.showSaveDialog(root.getScene().getWindow());

        if (file != null) {
        	// Testa a extenção do arquivo de destino e troca, quando necessário
        	if (!FilenameUtils.getExtension(file.getName()).equalsIgnoreCase("pdf")) {
        	    file = new File(file.getParentFile(), FilenameUtils.getBaseName(file.getName())+".pdf");
        	}
        	savePdfToFile(file);
        	closeWindow();
        }
	}
	
	private void loadPdfFile() {
		String pdfBase64 = Base64.getEncoder().encodeToString(pdfData);
		pdfView.getEngine().executeScript("openFileFromBase64('" + pdfBase64 + "')");
	}
	
	private void savePdfToFile(File destiny) {
		try (FileOutputStream fos = new FileOutputStream(destiny)) {
			   fos.write(pdfData);
		} catch (IOException e) {
			LOGGER.error(e.getMessage(), e);
			
			// Gera um Alert em caso de falha
			Alert alert = new Alert(AlertType.ERROR, "Falha ao tentar salvar o arquivo!");
			alert.getDialogPane().getChildren().stream().filter(node -> node instanceof Label).forEach(
					node -> {
						((Label)node).setMinHeight(Region.USE_PREF_SIZE);
						((Label)node).setTextFill(Color.RED);
					});
			alert.setHeaderText(null);
			alert.setGraphic(null);
	        alert.initOwner(root.getScene().getWindow());
		}
	}
}
