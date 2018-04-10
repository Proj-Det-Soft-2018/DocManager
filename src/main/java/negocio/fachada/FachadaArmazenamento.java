package negocio.fachada;

import java.util.List;

import negocio.dominio.Interessado;
import negocio.dominio.Processo;
import negocio.servico.Observador;

/**
 * @author hugotho
 * 
 */
public interface FachadaArmazenamento {
	
	public List<Processo> buscarListaProcessos();
	
	public void cadastrarObservador (Observador observador);
	
	public void descadastrarObservador(Observador observador);
	
	public void salvar (Processo novoProcesso);
	
	public void atualizar (Processo processoModificado);
	
	public void excluir (Processo processo, String admUser, String password);
	
	public List<Processo> buscarProcessos(String numero, String nome, String cpf, int situacao, int orgao, int assunto);
	
	public Interessado buscarPorCpf (String cpf);
	
	public void salvar (Interessado novoInteressado);
	
	public void atualizar (Interessado interessadoEditado);
}
