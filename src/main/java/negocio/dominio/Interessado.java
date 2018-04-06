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
	
	public void setContato(String contato) {
		this.contato = contato;
	}

	public void validar() throws RuntimeException {
		/*
		if(nome == null || cpf == null){
			throw new RuntimeException();
		}else if(cpf.length() != 14) {
			throw new RuntimeException();
		}
		//*/
	}
	
	public void validarCpfNulo() {
		if(this.cpf == null || this.cpf.isEmpty()) {
			//throw new CpfInvalidoException("O campo cpf do interessado não pode ser vazio");
		}
	}
	
	public void validarCpfForma() {
		//verifica se a string contem somente numeros
		if(!this.cpf.matches("[0-9]")) {
			//throw new CpfInvalidoException("O campo cpf do interessado deve conter somente numeros");
		}
	}
	
	public void validarCpfTamanho() {
		if(this.cpf.length() != 11) {
			//throw new CpfInvalidoException("O campo cpf do interessado deve conter apenas numeros");
		}
	}
	
	public void validarNomeForma() {
		//verifica se so tem letras e espaços
		if(!this.nome.matches("[a-zA-Z\\s]+")) {
			//throw new NomeInvalidoException("O campo nome do interessado deve conter apenas letars e espaços");
		}	
	}
	
	public void validarNomeNulo() {
		if(this.nome ==null || this.nome.isEmpty()) {
//			throw new NomeInvalidoException("O campo nome do interessado não pode ser vazio");
		}
	}
	
	public void validarContatoForma() {
		if(!this.contato.matches(".((10)|([1-9][1-9]).)\\s9?[6-9][0-9]{3}-[0-9]{4}") ||
        !this.contato.matches(".((10)|([1-9][1-9]).)\\s[2-5][0-9]{3}-[0-9]{4}") ){
			//throw new ContatoInvalidoException("O campo contato do interessado deve conter apenas letras números");
		}
	}

}