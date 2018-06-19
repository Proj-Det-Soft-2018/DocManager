package presentation;

import java.net.URL;

import org.apache.log4j.Logger;

import business.model.Interested;
import business.service.InterestedService;
import health.presentation.HealthInterestedEditCtrl;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import juridical.model.JuridicalInterested;
import presentation.utils.widget.MaskedContactTextField;

public class JuridicalInterestedEditCtrl extends InterestedEditCtrl {
	private static final Logger LOGGER = Logger.getLogger(JuridicalInterestedEditCtrl.class);
	private static final URL FXML_PATH = HealthInterestedEditCtrl.class.getResource("/visions/juridical_interested_edit_screen.fxml");
	
	@FXML
	private Label lblAlert;
	
	@FXML
	private Label lblTxtCpf;
	
	@FXML
	private TextField txtName;
	
	@FXML
	private MaskedContactTextField txtContact;
	
	protected JuridicalInterestedEditCtrl(InterestedService interestedService) {
		super(interestedService, LOGGER);
	}

	@Override
	protected void populeForm() {
		lblTxtCpf.setText(((JuridicalInterested)interested).getFormatedCpf());
		
		if (interested.getId() != null) {
			JuridicalInterested juridicalInterested = (JuridicalInterested) interested;
			super.root.getChildren().remove(lblAlert);
			
			txtName.setText(juridicalInterested.getName());
			txtContact.setContactPlainText(juridicalInterested.getContact());
		}

	}

	@Override
	protected Interested mountInterested() {
		return new JuridicalInterested(txtName.getText(), ((JuridicalInterested)super.interested).getCpf(), txtContact.plainTextProperty().getValue());
	}

	@Override
	protected Scene dimensionScene(Parent rootParent) {
		if (interested.getId() == null) {
			return new Scene(rootParent, 400, 260);
		} else {
			return new Scene(rootParent, 400, 230);
		}
	}

	@Override
	public URL getFxmlPath() {
		return FXML_PATH;
	}

}
