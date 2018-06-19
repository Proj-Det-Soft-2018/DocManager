package juridical.model;

import javax.xml.bind.annotation.XmlElement;

import business.exception.ValidationException;

public class Lawyer {
	private Long id;
	private String name;
	private String oab;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Lawyer(String name, String oab) {
		super();
		this.name = name;
		this.oab = oab;
	}

	/**
	 * @return the name
	 */
	@XmlElement(name="lawyer-name")
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the oab
	 */
	@XmlElement(name="oab")
	public String getOab() {
		return oab;
	}

	/**
	 * @param oab the oab to set
	 */
	public void setOab(String oab) {
		this.oab = oab;
	}
	

	public void validate() throws ValidationException {
		StringBuilder failureMsg = new StringBuilder();
		boolean failure = false;
		
		if(this.name == null || this.name.isEmpty()) {
			failure = true;
			failureMsg.append("O campo Nome não pode ser vazio.\n\n");
		}
		else if(!this.name.matches("[a-zA-Z\\s]+")) {
			failure = true;
			failureMsg.append("O campo Nome deve conter apenas letras.\n\n");
		}
		
		if(this.oab == null || (!this.oab.isEmpty() && this.oab.length() < 6)){
			failure = true;
			failureMsg.append("O numero OAB inserido está incompleto.\n\n");
		}
		
		if(failure) {
			failureMsg.delete(failureMsg.length() - 2, failureMsg.length());
			throw new ValidationException(failureMsg.toString());
		}

	}
	
}
