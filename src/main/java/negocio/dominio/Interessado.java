/**
 * 
 */
package negocio.dominio;

/**
 * Classe representa o interessado do processo, pessoa vinculada ao processo como
 * parte interessada.
 * 
 * @author clah
 *
 */
public class Interessado {
	private Long id;
	private String nome;
	private String cpf;
	private String contato;
	
	
	//JavaBeans
	public Interessado() {
		
	}


	/**
	 * @param nome
	 * @param cpf
	 * @param contato1
	 * @param contato2
	 */
	public Interessado(String nome, String cpf, String contato) {
		super();
		this.nome = nome;
		this.cpf = cpf;
		this.contato = contato;
	}
	
	
	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}


	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}


	public String getNome() {
		return nome;
	}


	public void setNome(String nome) {
		this.nome = nome;
	}


	public String getCpf() {
		return cpf;
	}


	public void setCpf(String cpf) {
		this.cpf = cpf;
	}
	
	public String getContato() {
		return contato;
	}

	/**
	 * @param contato the contato to set
	 */
	public void setContato(String contato) {
		this.contato = contato;
	}

	public void validar() {
		// TODO Auto-generated method stub
		
	}
	
	
	

}