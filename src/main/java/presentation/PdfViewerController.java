package presentation;

import java.net.URL;
import java.util.Base64;
import java.util.ResourceBundle;

import business.service.ConcreteProcessService;
import business.service.ProcessService;
import business.model.Process;

import javafx.concurrent.Worker;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

public class PdfViewerController implements Initializable {

	private ProcessService processService;
	
	private byte[] pdfData;
	private boolean dataReady;
	
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

	public void setVisualizedProcess(Process visualizedProcess) {
		
		pdfData = processService.getPdf(visualizedProcess);
		dataReady = true;
	}
	
	private void loadPdfFile() {
		String pdfBase64 = Base64.getEncoder().encodeToString(pdfData);
		pdfView.getEngine().executeScript("openFileFromBase64('" + pdfBase64 + "')");
	}
}
