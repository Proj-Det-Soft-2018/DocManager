/**
 * 
 */
package negocio.facade;

import java.util.ArrayList;
import java.util.List;

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
	private List<Documento> listaDocumentos;
	public FachadaGerenciadorProcesso() {
		processo = new Processo();
		interessado = new Interessado();
		situacao = new Situacao();
		orgao = new Orgao();
	}	

	public List<Documento> getListaDocumentos() {
		 listaDocumentos = new ArrayList<Processo>();
		return processo.getBanco().getAll();
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
		
		
		this.situacao = Situacao.getDb().get(situacaoId);
		
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
			documentoAlvo.
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
		return situacao.todosNomes();
	}

}
