/**
 * 
 */
package br.com.docmanager.model;

import br.com.docmanager.model.domain.Interessado;
import br.com.docmanager.model.domain.Orgao;
import br.com.docmanager.model.domain.Processo;
import br.com.docmanager.model.domain.Situacao;

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

}
