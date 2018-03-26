/**
 * 
 */
package negocio.facade;

import java.util.List;

import apresentacao.Documento;
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
	private Situacao situacao;
	private Orgao orgao;
	private Assunto assunto;
	
	
	public FachadaGerenciadorProcesso() {
		processo = new Processo();
		interessado = new Interessado();
		situacao = new Situacao();
		assunto = new Assunto();
		orgao = new Orgao();
	}	

	public List<? extends Documento> getListaDocumentos() {
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
		
		this.situacao = Situacao.getDb().get(situacaoId);
		
		this.orgao = Orgao.getById(orgaoOrigemId);
		
		this.assunto = Assunto.getById(assuntoDocumentoId);
		
		
		this.processo.setInteressado(this.interessado);
		this.processo.setNumero(numDocumento);
		this.processo.setSituacaoAtual(this.situacao);
		this.processo.setUnidadeOrigem(this.orgao);
		this.processo.setAssunto(this.assunto);
		this.processo.setObservacao(observacao);
		
		this.processo.criar();
		
	}
	
	/**
	 * Requisição para atualizar um processo que já existe
	 */
	@Override
	public void atualizarDocumento(Documento documentoAlvo, boolean ehOficio, String numDocumento,
			String nomeInteressado, String cpfInteressado, String contatoInteressado, int orgaoOrigemId,
			int tipoDocumentoId, int situacaoId, String observacao) {
			
	}
	
	public static String verProcessoSelecionado(int idProcesso) {
		//TODO deve-se implementar como os dados do processo vai ser recebido no parametro
		//TODO Deve-se ver o retorno após consulta no banco de dados
		Processo processo = new Processo();
		return processo.selecionarPorId(idProcesso).toString();
	}

	public String[] getListaOrgaos() {
		return orgao.todosNomes();
	}

	public String[] getListaAssuntos() {
		return assunto.todosNomes();
	}

	public String[] getListaSituacao() {
		return situacao.todosNomes();
	}




	
}
