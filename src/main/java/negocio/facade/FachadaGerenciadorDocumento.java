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
import negocio.servico.InteressadoServico;
import negocio.servico.Observador;
import negocio.servico.ProcessoServico;

/**
 * @author clah
 * 
 */
public class FachadaGerenciadorDocumento implements FachadaCaixasDeEscolha{
	
	private ProcessoServico processoServico;
	private InteressadoServico interessadoServico;
	
	// Singleton
	private static final FachadaGerenciadorDocumento instance = new FachadaGerenciadorDocumento();
	
	private FachadaGerenciadorDocumento() {
		processoServico = new ProcessoServico();
		interessadoServico = new InteressadoServico();
	}
	
	public static FachadaGerenciadorDocumento getInstance() {
		return instance;
	}

	@Override
	public List<? extends DocumentoVisao> getListaDocumentos() {
		return this.processoServico.getAll();
	}
	
	@Override
	public void cadastrarObservador(Observador observadorDaLista) {
		processoServico.cadastrarObservador(observadorDaLista);
	}

	/**
	 * Requisição para adicionar novo processo a base de dados
	 */
	@Override
	public void criarDocumento(
			boolean ehOficio,
			String numDocumento,
			String nomeInteressado,
			String cpfInteressado,
			String contatoInteressado,
			int orgaoOrigemId,
			int assuntoDocumentoId,
			int situacaoId,
			String observacao)
	{
		Interessado interessado = new Interessado(nomeInteressado, cpfInteressado, contatoInteressado);
		
		interessadoServico.criarInteressado(interessado);
		try {
			Processo processo = new Processo(ehOficio, numDocumento, interessado, Assunto.getAssuntoPorId(assuntoDocumentoId), Orgao.getOrgaoPorId(orgaoOrigemId), Situacao.getSituacaoPorId(situacaoId));
			processoServico.criarProcesso(processo);
		}
		catch (Exception e) {
			// TODO: handle exception
		}
		
		
	}
	
	/**
	 * Requisição para atualizar um processo que já existe
	 */
	@Override
	public void atualizarDocumento(
			DocumentoVisao documentoAlvo,
			boolean ehOficio,
			String numDocumento, 
			String nomeInteressado,
			String cpfInteressado,
			String contatoInteressado,
			int orgaoOrigemId,
			int tipoDocumentoId,
			int situacaoId,
			String observacao)
	{
				
		Interessado interessado = new Interessado(nomeInteressado, cpfInteressado, cpfInteressado);
		interessadoServico.atualizarInteressado(interessado);
		try {
			Processo processo = new Processo(ehOficio, numDocumento, interessado, Assunto.getAssuntoPorId(tipoDocumentoId), Orgao.getOrgaoPorId(orgaoOrigemId), Situacao.getSituacaoPorId(situacaoId));
			processoServico.atualizarProcesso(processo);
		}
		catch (RuntimeException e) {
			// TODO
		}
		catch (Exception e) {
			// TODO
		}
		
	}
	
	public static String verProcessoSelecionado(String numProcesso) {
		//TODO deve-se implementar como os dados do processo vai ser recebido no parametro
		//TODO Deve-se ver o retorno após consulta no banco de dados
		Processo processo = new Processo();
		
		return processo.selecionarPorId(numProcesso).toString();
	}
	
	@Override
	public List<String> getListaOrgaos() {
		return Orgao.getOrgaos();
	}
	
	@Override
	public List<String> getListaAssuntos() {
		return Assunto.getAssuntos();
	}
	
	@Override
	public List<String> getListaSituacoes() {
		return Situacao.getSituacoes();
	}
}
