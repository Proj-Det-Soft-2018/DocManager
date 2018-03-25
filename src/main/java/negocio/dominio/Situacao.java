/**
 * 
 */
package negocio.dominio;

import negocio.GenericoDao;
import persistencia.FabricaDao;

/**
 * @author clah
 *
 */
public class Situacao {
	private int Id;
	private String descricao;
	GenericoDao<Situacao> banco;
	
	public Situacao() {
		banco = FabricaDao.createSitucaoDao();
		
	}
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
	/**
	 * @return the id
	 */
	public int getId() {
		return Id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(int id) {
		Id = id;
	}
	
	public void salvar() {
		banco.salvar(this);
		
	}
	
	public void remover() {
		banco.deletar(this);
	}
	
	

}
