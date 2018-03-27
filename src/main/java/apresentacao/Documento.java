package apresentacao;

public interface Documento {
	
	public boolean ehOficio();
	
	public String getNumDocumento();
	
	public String getNomeInteressado();
	
	public String getCpfInteressado();
	
	public String getContatoInteressado();
	
	public int getOrgaoOrigemId();
	
	public int getAssuntoId();
	
	public int getSituacaoId();
	
	public String getObservacao();
	
	default String getTipo() {
		return this.ehOficio()? "Ofício" : "Processo";
	}
}
