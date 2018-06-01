package business.model;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlTransient;

import business.exception.ValidationException;

public interface Interested {

	/**
	 * @return the id
	 */
	Long getId();

	/**
	 * @param id the id to set
	 */
	void setId(Long id);

	String getName();

	void setName(String name) throws ValidationException;

	String getFormatedCpf();

	String getCpf();

	void setCpf(String cpf);

	String getFormatedContact();

	String getContact();

	void setContact(String contact) throws ValidationException;

}