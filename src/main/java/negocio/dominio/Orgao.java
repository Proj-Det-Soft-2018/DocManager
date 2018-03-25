/**
 * 
 */
package negocio.dominio;

/**
 * @author clah
 *
 */
public class Orgao {
	private int Id;
	private String codigo;
	private String nome;
	private String sigla;
	
	public Orgao () {
		
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
	
}
