package presentation;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.Base64;
import java.util.ResourceBundle;

import org.apache.commons.io.FilenameUtils;
import org.apache.log4j.Logger;

import business.service.ConcreteProcessService;
import business.service.ProcessService;
import business.model.Process;

import javafx.concurrent.Worker;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class PdfViewerController implements Initializable {
	
	private final static Logger LOGGER = Logger.getLogger(PdfViewerController.class);

	private ProcessService processService;
	
	private byte[] pdfData;
	private boolean dataReady;
	
	@FXML
	private VBox root;
	
	@FXML
	private WebView pdfView;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		processService = ConcreteProcessService.getInstance();
		dataReady = false;
	}

	public void engineConfigurations() {

		WebEngine engine = pdfView.getEngine();

		// Setando a página para o visualizador de PDF
		String url = getClass().getResource("/pdfjs/web/viewer.html").toExternalForm();
		engine.setJavaScriptEnabled(true);
		engine.load(url);
		
		engine.getLoadWorker().stateProperty().addListener((obs, oldState, newState) -> {
			if (newState == Worker.State.SUCCEEDED && dataReady) {
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

	public void setVisualizedProcess(Process visualizedProcess) {
		
		pdfData = processService.getPdf(visualizedProcess);
		dataReady = true;
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
