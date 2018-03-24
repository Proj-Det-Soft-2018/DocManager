/**
 * 
 */
package negocio.facade;

import negocio.dominio.Interessado;
import negocio.dominio.Orgao;
import negocio.dominio.Processo;
import negocio.dominio.Situacao;

/**
 * @author clah
 * 
 */
public class FachadaArmazenamento {
	private Processo processo;
	private Interessado interessado;
	private Situacao situacao;
	private Orgao orgao;
	
	public FachadaArmazenamento() {
		processo = new Processo();
		interessado = new Interessado();
		situacao = new Situacao(null);
		orgao = new Orgao(null);
	}
	
	public void cadastrarNovoProcesso(
			String numeroProcesso,
			String assuntoProcesso,
			String unidadeOrigemProcesso,
			String observacao,
			String situacaoProcesso,
			String nomeInteressado,
			String cpfInteressado,
			String contato1Interessado,
			String contato2Interessado)
	{
		
		this.interessado.setNome(nomeInteressado);
		this.interessado.setCpf(cpfInteressado);
		this.interessado.setContato1(contato1Interessado);
		this.interessado.setContato2(contato2Interessado);
		
		this.situacao.setDescricao(situacaoProcesso);
		
		this.orgao.setNome(unidadeOrigemProcesso);
		
		this.processo.setAssunto(assuntoProcesso);
		this.processo.setInteressado(this.interessado);
		this.processo.setNumero(numeroProcesso);
		this.processo.setSituacaoAtual(this.situacao);
		this.processo.setUnidadeOrigem(this.orgao);
		this.processo.setObservacao(observacao);
		
		this.processo.criar();
	
	}
	
	public static String verProcessoSelecionado(int idProcesso) {
		//TODO deve-se implementar como os dados do processo vai ser recebido no parametro
		//TODO Deve-se ver o retorno após consulta no banco de dados
		Processo processo = new Processo();
		return processo.selecionarPorId(idProcesso).toString();
	}
	/**
	 * Requisição para atualizar um processo que já existe
	 */
	public static void atualizarProcesso() {
		//TODO a view deve enviar as informações para que o processo seja atualizado
		
		
	}

}
