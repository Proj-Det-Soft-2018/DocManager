package apresentacao;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * @author hugotho
 * 
 */
public interface DocumentoVisao {
	
	public boolean ehOficio();
	
	public String getNumDocumento();
	
	public String getNomeInteressado();
	
	public String getCpfInteressado();
	
	public String getContatoInteressado();
	
	public int getOrgaoOrigemId();
	
	public int getAssuntoId();
	
	public int getSituacaoId();
	
	public String getSituacao();
	
	public String getObservacao();
	
	default String getTipo() {
		return this.ehOficio()? "Ofício" : "Processo";
	}
	
	default StringProperty tipoProperty() {
		return new SimpleStringProperty(this.getTipo());
	}
	
	default StringProperty numDocumentoProperty() {
		return new SimpleStringProperty(this.getNumDocumento());
	}
	
	default StringProperty nomeInteressadoProperty() {
		return new SimpleStringProperty(this.getNomeInteressado());
	}
	
	default StringProperty situacaoProperty() {
		return new SimpleStringProperty(this.getSituacao());
	}
}
