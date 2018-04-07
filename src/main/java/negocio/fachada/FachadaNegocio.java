/**
 * 
 */
package negocio.fachada;

import java.util.List;

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
public class FachadaNegocio implements FachadaCaixasDeEscolha{
	
	private ProcessoServico processoServico;
	private InteressadoServico interessadoServico;
	
	// Singleton
	private static final FachadaNegocio instance = new FachadaNegocio();
	
	private FachadaNegocio() {
		processoServico = ProcessoServico.getInstance();
		interessadoServico = InteressadoServico.getInstance();
	}
	
	public static FachadaNegocio getInstance() {
		return instance;
	}

	@Override
	public List<Processo> buscarListaProcessos() {
		return this.processoServico.getAll();
	}
	
	@Override
	public void cadastrarObservador(Observador observadorDaLista) {
		processoServico.cadastrarObservador(observadorDaLista);
	}

	/**
	 * Requisição para adicionar novo processo na base de dados
	 */
	@Override
	public void salvar (Processo novoProcesso) {
		processoServico.criarProcesso(novoProcesso);
	}
	
	/**
	 * Requisição para atualizar um processo que já existe
	 */
	@Override
	public void atualizar (Processo processoModificado)	{
		processoServico.atualizarProcesso(processoModificado);
 	}
	
	
	@Override
	public void excluir (Processo processo) {
		
		processoServico.deletarProcesso(processo);
	}
	
	@Override
	public List<Processo> buscarProcessos(String numero, String nome, String cpf) {
		return processoServico.burcarProcessos(numero, nome, cpf);
	}
	
	@Override
	public Interessado buscarPorCpf (String cpf) {
		return interessadoServico.burcarPeloCpfInteressado(cpf);
	}
	
	@Override
	public void salvar (Interessado novoInteressado) {
		interessadoServico.criarInteressado(novoInteressado);
	}
	
	@Override
	public void atualizar (Interessado interessadoEditado)	{
		interessadoServico.atualizarInteressado(interessadoEditado);
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
