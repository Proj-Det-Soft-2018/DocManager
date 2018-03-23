/**
 * 
 */
package br.com.docmanager.model.domain;

/**
 * @author clah
 *
 */
public class Situacao {
	
	private String descricao;

	/**
	 * @param descricao
	 */
	public Situacao(String descricao) {
		super();
		this.descricao = descricao;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	
	

}
