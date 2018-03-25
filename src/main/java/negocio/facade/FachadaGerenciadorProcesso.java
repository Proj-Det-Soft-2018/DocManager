/**
 * 
 */
package negocio.facade;

import apresentacao.Documento;
import apresentacao.FachadaCaixasDeEscolha;
import javafx.collections.ObservableList;
import negocio.dominio.Interessado;
import negocio.dominio.Orgao;
import negocio.dominio.Processo;
import negocio.dominio.Situacao;

/**
 * @author clah
 * 
 */
public class FachadaGerenciadorProcesso implements apresentacao.FachadaArmazenamento, FachadaCaixasDeEscolha{
	private Processo processo;
	private Interessado interessado;
	private Situacao situacao;
	private Orgao orgao;
	
	public FachadaGerenciadorProcesso() {
		processo = new Processo();
		interessado = new Interessado();
		situacao = new Situacao(null);
		orgao = new Orgao();
	}	

	public ObservableList<Documento> getListaDocumentos() {
		return null;
	}
	
	/**
	 * Requisição para adicionar novo processo a base de dados
	 */
	public void criarDocumento(boolean ehOficio, String numDocumento, String nomeInteressado, String cpfInteressado,
			String contatoInteressado, int orgaoOrigemId, int assuntoDocumentoId, int situacaoId, String observacao) {
		
		this.interessado.setNome(nomeInteressado);
		this.interessado.setCpf(cpfInteressado);
		this.interessado.setContato1(contatoInteressado);
		this.interessado.setContato2(null);
		
		this.interessado.criar();
		
		this.situacao.setDescricao(null);//TODO Estrutura para guardar e metodo set por ID
		
		this.orgao.setNome(null); //TODO Estrutura para guardar e metodo set por ID
		
		this.processo.setAssunto(null); //TODO Estrutura para guardar e metodo set por ID
		
		
		this.processo.setInteressado(this.interessado);
		this.processo.setNumero(numDocumento);
		this.processo.setSituacaoAtual(this.situacao);
		this.processo.setUnidadeOrigem(this.orgao);
		this.processo.setObservacao(observacao);
		
		this.processo.criar();
		
	}
	
	/**
	 * Requisição para atualizar um processo que já existe
	 */
	public void atualizarDocumento(Documento documentoAlvo, boolean ehOficio, String numDocumento,
			String nomeInteressado, String cpfInteressado, String contatoInteressado, int orgaoOrigemId,
			int tipoDocumentoId, int situacaoId, String observacao) {
		// TODO Auto-generated method stub
		
	}
	
	public static String verProcessoSelecionado(int idProcesso) {
		//TODO deve-se implementar como os dados do processo vai ser recebido no parametro
		//TODO Deve-se ver o retorno após consulta no banco de dados
		Processo processo = new Processo();
		return processo.selecionarPorId(idProcesso).toString();
	}

	public String[] getListaOrgaos() {
		// TODO Auto-generated method stub
		return null;
	}

	public String[] getListaTipoDocumento() {
		// TODO Auto-generated method stub
		return null;
	}

	public String[] getListaSituacao() {
		// TODO Auto-generated method stub
		return null;
	}

}
