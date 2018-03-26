package apresentacao;

import java.util.List;

public interface FachadaArmazenamento {
	
	public List<Documento> getListaDocumentos();
	
	public void criarDocumento (boolean ehOficio, String numDocumento, String nomeInteressado,
			String cpfInteressado, String contatoInteressado, int orgaoOrigemId, int tipoDocumentoId,
			int situacaoId, String observacao);
	
	public void atualizarDocumento (Documento documentoAlvo, boolean ehOficio, String numDocumento, String nomeInteressado,
			String cpfInteressado, String contatoInteressado, int orgaoOrigemId, int tipoDocumentoId,
			int situacaoId, String observacao);
}
