package apresentacao;

import java.util.List;

/**
 * @author hugotho
 * 
 */
public interface FachadaArmazenamento {
	
	public List<? extends DocumentoVisao> getListaDocumentos();
	
	public void criarDocumento (boolean ehOficio, String numDocumento, String nomeInteressado,
			String cpfInteressado, String contatoInteressado, int orgaoOrigemId, int tipoDocumentoId,
			int situacaoId, String observacao);
	
	public void atualizarDocumento (DocumentoVisao documentoAlvo, boolean ehOficio, String numDocumento, String nomeInteressado,
			String cpfInteressado, String contatoInteressado, int orgaoOrigemId, int tipoDocumentoId,
			int situacaoId, String observacao);
}
