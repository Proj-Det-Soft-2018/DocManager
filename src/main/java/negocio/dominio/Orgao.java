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
public class Orgao {
	private int id;
	private String codigo;
	private String nome;
	private String sigla;
	private GenericoDao<Orgao> banco;
	
	public Orgao () {
		this.banco = FabricaDao.criarOrgaoDao();
	}
	
	public Orgao(String nome, String sigla) {
		this.sigla = sigla;
		this.nome = nome;
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getSigla() {
		return sigla;
	}

	public void setSigla(String sigla) {
		this.sigla = sigla;
	}
	
	
	
	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}

	public void salvar() {
		banco.salvar(this);
		
	}
	
	public void remover() {
		banco.deletar(this);
	}

	
	
}
