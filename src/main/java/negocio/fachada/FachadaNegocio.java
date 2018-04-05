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
		processoServico = new ProcessoServico();
		interessadoServico = new InteressadoServico();
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
		//TODO mandar para o serviço de processos
		processoServico.criarProcesso(novoProcesso);
	}
	
	/**
	 * Requisição para atualizar um processo que já existe
	 */
	@Override
	public void atualizar (Processo processoModificado)	{
		//TODO mandar para o serviço de processos
	}
	
	public Interessado buscarPorCpf (String cpf) {
		//TODO mandar para o serviço de interessados
		return null;
	}
	
	public void salvar (Interessado novoInteressado) {
		//TODO mandar para o serviço de interessados
	}
	
	public void atualizar (Interessado interessadoEditado)	{
		//TODO mandar para o serviço de interessados
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
