/**
 * 
 */
package juridical.model;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlTransient;

import business.exception.ValidationException;
import business.model.Interested;

/**
 * @author clah
 *
 */
public class Inventoried implements Interested {
	String dateRegex = "(^(((0[1-9]|1[0-9]|2[0-8])[\\/](0[1-9]|1[012]))"
							+ "|((29|30|31)[\\/](0[13578]|1[02]))|"
							+ "((29|30)[\\/](0[4,6,9]|11)))[\\/](19|[2-9][0-9])\\d\\d$)|"
							+ "(^29[\\/]02[\\/](19|[2-9][0-9])"
							+ "(00|04|08|12|16|20|24|28|32|36|40|44|48|52|"
							+ "56|60|64|68|72|76|80|84|88|92|96)$)"; 
	private Long id;
	private String name;
	private String cpf;
	private LocalDate dateOfDeath;
	
	public Inventoried(Long id, String nome, String cpf, String dateOfDeath) {
		this.id = id;
		this.name = nome;
		this.cpf = cpf;
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		this.dateOfDeath = LocalDate.parse(dateOfDeath, formatter);
	}
	
	public Inventoried(String nome, String cpf, String dataObito) {
		this.name = nome;
		this.cpf = cpf;
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		this.dateOfDeath = LocalDate.parse(dateOfDeath, formatter);
	}
	
	/* (non-Javadoc)
	 * @see business.model.Interested#getId()
	 */
	@Override
	public Long getId() {
		return this.id;
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
	@XmlElement (name="inventoried-name")
	public String getName() {
		return name;
	}


	/* (non-Javadoc)
	 * @see business.model.Interested#setName(java.lang.String)
	 */
	public void setName(String name){
		this.name = name;
	}
	
	/* (non-Javadoc)
	 * @see business.model.Interested#getFormatedCpf()
	 */
	@XmlElement(name="inventoried-cpf")
	public String getFormatedCpf() {
		return cpf.replaceAll("(\\d{3})(\\d{3})(\\d{3})(\\d{2})", "$1.$2.$3-$4");
	}
	
	/* (non-Javadoc)
	 * @see business.model.Interested#getCpf()
	 */
	@XmlTransient
	public String getCpf() {
		return cpf;
	}


	/* (non-Javadoc)
	 * @see business.model.Interested#setCpf(java.lang.String)
	 */
	public void setCpf(String cpf) {
		this.cpf = cpf;
	}
	
	
	@XmlElement(name="date-of-death")
	public String getFormatedDateOfDeath() {
		return dateOfDeath.toString();
	}
	
	
	@XmlTransient
	public LocalDate getDateOfDeath() {
		return dateOfDeath;
	}
	
	public void setDateOfDeath(LocalDate dateOfDeath) {
		this.dateOfDeath = dateOfDeath;
	}


	/* (non-Javadoc)
	 * @see business.model.Interested#validate()
	 */
	@Override
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
		
		if( this.dateOfDeath.isAfter(LocalDate.now())){
			failure = true;
			failureMsg.append("O data de óbito não pode ser amior que a data atual.\n\n");
		}
		
		if(failure) {
			failureMsg.delete(failureMsg.length() - 2, failureMsg.length());
			throw new ValidationException(failureMsg.toString());
		}

	}

}
