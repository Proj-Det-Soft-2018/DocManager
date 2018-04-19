package presentation;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Base64;
import java.util.ResourceBundle;

import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;

import business.service.ConcreteProcessService;
import business.service.ProcessService;
import business.model.Process;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

public class PdfViewerController implements Initializable {

	private static final Logger logger = Logger.getLogger(PdfViewerController.class);

	private ProcessService processService;
	private Process visualizedProcess;

	@FXML
	private WebView pdfView;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		processService = ConcreteProcessService.getInstance();
	}

	public void engineConfigurations() {

		WebEngine engine = pdfView.getEngine();

		// Setando a página para o visualizador de PDF
		String url = getClass().getResource("/pdfjs/web/viewer.html").toExternalForm();
		engine.setJavaScriptEnabled(true);
		engine.load(url);
	}

	public void setVisualizedProcess(Process visualizedProcess) {
		
		this.visualizedProcess = visualizedProcess;

		InputStream pdfStream = null;
		try {
			pdfStream = processService.getPdf(visualizedProcess);
			byte[] pdfData = IOUtils.toByteArray(pdfStream);
			String pdfBase64 = Base64.getEncoder().encodeToString(pdfData);
			pdfView.getEngine().executeScript("openFileFromBase64('" + pdfBase64 + "')");

		} catch (Exception e) {
			logger.error(e);
		}finally {
			if (pdfStream != null) {
				try {
					pdfStream.close();
				} catch (IOException e) {
					logger.error(e);
				}
			}
		}
	}
}
