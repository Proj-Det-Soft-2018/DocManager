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
public class HealthInterested implements Interested {
	private Long id;
	private String name;
	private String cpf;
	private String contact;
	
	
	
	public HealthInterested(Long id, String nome, String cpf, String contato) {
		this.id = id;
		this.name = nome;
		this.cpf = cpf;
		this.contact = contato;
	}
	
	public HealthInterested() {}

	/* (non-Javadoc)
	 * @see business.model.Interested#getId()
	 */
	@Override
	@XmlTransient
	public Long getId() {
		return id;
	}

	/* (non-Javadoc)
	 * @see business.model.Interested#setId(java.lang.Long)
	 */
	@Override
	public void setId(Long id) {
		this.id = id;
	}

	/* (non-Javadoc)
	 * @see business.model.Interested#getName()
	 */
	@Override
	@XmlElement
	public String getName() {
		return name;
	}


	/* (non-Javadoc)
	 * @see business.model.Interested#setName(java.lang.String)
	 */
	@Override
	public void setName(String name) throws ValidationException {
		if(name == null || name.isEmpty()) {
			throw new ValidationException("O campo Nome não pode ser vazio.");
		}
		else if(!name.matches("[a-zA-Z\\s]+")) {
			throw new ValidationException("O campo Nome deve conter apenas letras.");
		}
		this.name = name;
	}
	
	/* (non-Javadoc)
	 * @see business.model.Interested#getFormatedCpf()
	 */
	@Override
	@XmlElement(name="cpf")
	public String getFormatedCpf() {
		return cpf.replaceAll("(\\d{3})(\\d{3})(\\d{3})(\\d{2})", "$1.$2.$3-$4");
	}
	
	/* (non-Javadoc)
	 * @see business.model.Interested#getCpf()
	 */
	@Override
	@XmlTransient
	public String getCpf() {
		return cpf;
	}


	/* (non-Javadoc)
	 * @see business.model.Interested#setCpf(java.lang.String)
	 */
	@Override
	public void setCpf(String cpf) {
		this.cpf = cpf;
	}
	
	/* (non-Javadoc)
	 * @see business.model.Interested#getFormatedContact()
	 */
	@Override
	@XmlElement(name="contact")
	public String getFormatedContact() {
		return contact.replaceAll("(\\d{2})(\\d{5}|\\d{4})(\\d{4})", "($1) $2-$3");
	}
	
	/* (non-Javadoc)
	 * @see business.model.Interested#getContact()
	 */
	@Override
	@XmlTransient
	public String getContact() {
		return contact;
	}
	
	/* (non-Javadoc)
	 * @see business.model.Interested#setContact(java.lang.String)
	 */
	@Override
	public void setContact(String contact) throws ValidationException {
		if(contact == null || (!contact.isEmpty() && contact.length() < 10)){
			throw new ValidationException("O contato inserido está incompleto");
		}
		this.contact = contact;
	}
}