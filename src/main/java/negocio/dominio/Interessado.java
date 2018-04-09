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
	
	
	
	public Interessado(Long id, String nome, String cpf, String contato) {
		this.id = id;
		this.nome = nome;
		this.cpf = cpf;
		this.contato = contato;
	}
	
	public Interessado() {

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
		if(nome ==null || nome.isEmpty()) {
			throw new ValidationException("Você não preencheu o campo Nome!", "Nome", "O campo Nome não pode ser vazio.");
		}
		else if(!nome.matches("[a-zA-Z\\s]+")) {
			throw new ValidationException("Campo nome contem caracteres inválidos!", "Nome", "O campo Nome deve conter apenas letras.");
		}
		this.nome = nome;
	}


	public String getCpf() {
		return cpf;
	}


	public void setCpf(String cpf) {
		if(cpf == null) {
			throw new ValidationException("Você não preencheu o campo do CPF!", "CPF", "O campo cpf do interessado não pode ser vazio.");
		}
		else if(cpf.length() != 11) {
			throw new ValidationException("CPF INVÁLIDO!", "CPF", "Você não digitou corretamente o cpf do interessado.");
		}
		this.cpf = cpf;
	}
	
	public String getContato() {
		return contato;
	}
	
	public void setContato(String contato) {
		if(contato.length()>9){
			throw new ValidationException("O contato não foi digitado corretamente!", "Contato", "O contato deve conter o DDD com 2 dígitos seguido do número." + contato);
		}
		
		this.contato = contato;
	}

}