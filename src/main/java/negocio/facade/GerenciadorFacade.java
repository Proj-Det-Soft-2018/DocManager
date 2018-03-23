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
public class GerenciadorFacade {
	
	public static void cadastrarNovoProcesso(String numeroProcesso, String assuntoProcesso, Orgao unidadeOrigemProcesso,
										String situacaoProcesso, String nomeInteressado, String cpfInteressado, String contato1Interessado, String contato2Interessado) {
		
		Interessado interessado = new Interessado(nomeInteressado, cpfInteressado, contato1Interessado, contato2Interessado);
		Situacao situacaoAtualProcesso = new Situacao(situacaoProcesso);
		Processo processo = new Processo(numeroProcesso, interessado, assuntoProcesso, unidadeOrigemProcesso,situacaoAtualProcesso);
		
		processo.criar(processo);
	
	}
	
	public static void verProcessoSelcionado() {
		//TODO deve-se implementar como os dados do processo vai ser recebido no parametro
		//TODO Deve-se ver o retorno após consulta no banco de dados
	}
	/**
	 * Requisição para atualizar um processo que já existe
	 */
	public static void atualizarProcesso() {
		//TODO a view deve enviar as informações para que o processo seja atualizado
		
		
	}

}
