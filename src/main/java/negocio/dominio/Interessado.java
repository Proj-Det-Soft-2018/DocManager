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
	
	private String nome;
	private String cpf;
	private String contato1;
	
	public Interessado() {
		
	}

	/**
	 * @param nome
	 * @param cpf
	 * @param contato1
	 * @param contato2
	 */
	public Interessado(String nome, String cpf, String contato1) {
		super();
		this.nome = nome;
		this.cpf = cpf;
		this.contato1 = contato1;
	}

	public String getContato1() {
		return contato1;
	}

	public void setContato1(String contato1) {
		this.contato1 = contato1;
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

	public void validar() throws RuntimeException {
		throw new RuntimeException();
	}
	

}