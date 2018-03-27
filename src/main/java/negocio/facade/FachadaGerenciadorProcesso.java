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
import negocio.servico.InteressadoServico;
import negocio.servico.ProcessoServico;

/**
 * @author clah
 * 
 */
public class FachadaGerenciadorProcesso implements FachadaCaixasDeEscolha{
	
	private ProcessoServico processoServico;
	private InteressadoServico interessadoServico;
	
	
	public FachadaGerenciadorProcesso() {
		processoServico = new ProcessoServico();
		interessadoServico = new InteressadoServico();
	}	

	public List<? extends Documento> getListaDocumentos() {
		return this.processoServico.getAll();
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
		Processo processo = new Processo(ehOficio, numDocumento, interessado, Assunto.values()[assuntoDocumentoId-1], Orgao.values()[orgaoOrigemId-1], Situacao.values()[situacaoId-1]);
		processoServico.criarProcesso(processo);
	}
	
	/**
	 * Requisição para atualizar um processo que já existe
	 */
	@Override
	public void atualizarDocumento(
			Documento documentoAlvo,
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
		Processo processo = new Processo(ehOficio, numDocumento, interessado, Assunto.values()[tipoDocumentoId-1], Orgao.values()[orgaoOrigemId-1], Situacao.values()[situacaoId-1]);
		processoServico.atualizarProcesso(processo);
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

	public List<String> getListaTipoDocumento() {
		return Assunto.getAssuntos();
	}

	public List<String> getListaSituacao() {
		return Situacao.getSituacoes();
	}
}
