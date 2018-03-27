/**
 * 
 */
package negocio.facade;

import java.util.List;

import apresentacao.DocumentoVisao;
import apresentacao.FachadaCaixasDeEscolha;
import negocio.dominio.Assunto;
import negocio.dominio.Interessado;
import negocio.dominio.Orgao;
import negocio.dominio.Processo;
import negocio.dominio.Situacao;

/**
 * @author clah
 * 
 */
public class FachadaGerenciadorProcesso implements FachadaCaixasDeEscolha{
	
	private Processo processo;
	private Interessado interessado;
	
	
	public FachadaGerenciadorProcesso() {
		processo = new Processo();
		interessado = new Interessado();
	}	

	public List<? extends DocumentoVisao> getListaDocumentos() {
		return processo.getBanco().getAll();
	}
	
	/**
	 * Requisição para adicionar novo processo a base de dados
	 */
	@Override
	public void criarDocumento(boolean ehOficio, String numDocumento, String nomeInteressado, String cpfInteressado,
			String contatoInteressado, int orgaoOrigemId, int assuntoDocumentoId, int situacaoId, String observacao) {
		
		this.interessado.setNome(nomeInteressado);
		this.interessado.setCpf(cpfInteressado);
		this.interessado.setContato1(contatoInteressado);
		this.interessado.setContato2(null);
		
		this.interessado.criar();
		
		this.processo.setInteressado(this.interessado);
		this.processo.setNumero(numDocumento);
		this.processo.setSituacaoAtual(Situacao.values()[situacaoId-1]);
		this.processo.setUnidadeOrigem(Orgao.values()[orgaoOrigemId-1]);
		this.processo.setAssunto(Assunto.values()[assuntoDocumentoId-1]);
		this.processo.setObservacao(observacao);
		
		this.processo.criar();
	}
	
	/**
	 * Requisição para atualizar um processo que já existe
	 */
	@Override
	public void atualizarDocumento(DocumentoVisao documentoAlvo, boolean ehOficio, String numDocumento,
			String nomeInteressado, String cpfInteressado, String contatoInteressado, int orgaoOrigemId,
			int tipoDocumentoId, int situacaoId, String observacao) {	
	}
	
	public static String verProcessoSelecionado(String numProcesso) {
		//TODO deve-se implementar como os dados do processo vai ser recebido no parametro
		//TODO Deve-se ver o retorno após consulta no banco de dados
		Processo processo = new Processo();
		return processo.selecionarPorId(numProcesso).toString();
	}

	public List<String> getListaOrgaos() {
		return Orgao.getOrgaos();
	}

	public List<String> getListaAssuntos() {
		return Assunto.getAssuntos();
	}

	public List<String> getListaSituacoes() {
		return Situacao.getSituacoes();
	}
}
