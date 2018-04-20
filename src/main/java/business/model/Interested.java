/**
 * 
 */
package business.model;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import business.exception.ValidationException;

/**
 * Classe representa o interessado do processo, pessoa vinculada ao processo como
 * parte interessada.
 * 
 * @author clah
 *
 */
@XmlRootElement(name="interested")
public class Interested {
	private Long id;
	private String name;
	private String cpf;
	private String contact;
	
	
	
	public Interested(Long id, String nome, String cpf, String contato) {
		this.id = id;
		this.name = nome;
		this.cpf = cpf;
		this.contact = contato;
	}
	
	public Interested() {}

	/**
	 * @return the id
	 */
	@XmlTransient
	public Long getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	@XmlElement
	public String getName() {
		return name;
	}


	public void setNome(String nome) throws ValidationException {
		if(nome == null || nome.isEmpty()) {
			throw new ValidationException("O campo Nome não pode ser vazio.");
		}
		else if(!nome.matches("[a-zA-Z\\s]+")) {
			throw new ValidationException("O campo Nome deve conter apenas letras.");
		}
		this.name = nome;
	}
	
	@XmlElement(name="cpf")
	public String getFormatedCpf() {
		return cpf.replaceAll("(\\d{3})(\\d{3})(\\d{3})(\\d{2})", "$1.$2.$3-$4");
	}
	
	@XmlTransient
	public String getCpf() {
		return cpf;
	}


	public void setCpf(String cpf) {
		this.cpf = cpf;
	}
	
	@XmlElement(name="contact")
	public String getFormatedContato() {
		return contact.replaceAll("(\\d{2})(\\d{5}|\\d{4})(\\d{4})", "($1) $2-$3");
	}
	
	@XmlTransient
	public String getContato() {
		return contact;
	}
	
	public void setContato(String contato) throws ValidationException {
		if(contato == null || (!contato.isEmpty() && contato.length() < 10)){
			throw new ValidationException("O contato inserido está incompleto");
		}
		this.contact = contato;
	}
}