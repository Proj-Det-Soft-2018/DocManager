package apresentacao;

public interface Documento {
	
	public boolean ehOficio();
	
	public String getNumDocumento();
	
	public String getNomeInteressado();
	
	public String getCpfInteressado();
	
	public String getContatoInteressado();
	
	public int getOrgaoOrigemId();
	
	public int getTipoDocumentoId();
	
	public int getSituacaoId();
	
	public String getObservacao();
}
