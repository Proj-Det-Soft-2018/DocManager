package apresentacao;

import javafx.collections.ObservableList;

public interface FachadaArmazenamento {
	
	public ObservableList<Documento> getListaDocumentos();
	
	public void criarDocumento (boolean ehOficio, String numDocumento, String nomeInteressado,
			String cpfInteressado, String contatoInteressado, int orgaoOrigemId, int tipoDocumentoId,
			int situacaoId, String observacao);
	
	public void atualizarDocumento (Documento documentoAlvo, boolean ehOficio, String numDocumento, String nomeInteressado,
			String cpfInteressado, String contatoInteressado, int orgaoOrigemId, int tipoDocumentoId,
			int situacaoId, String observacao);
}
