package purchase.model;

import java.time.LocalDateTime;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSeeAlso;

import org.apache.log4j.Logger;

import business.exception.ValidationException;
import business.model.Interested;
import business.model.Organization;
import business.model.Process;
import business.model.Situation;
import business.model.Subject;

@XmlRootElement(name="process")
@XmlSeeAlso(PurchaseInterested.class)
public class PurchaseProcess implements Process {
	
	private static final Logger LOGGER = Logger.getLogger(PurchaseInterested.class);
	
	private Long id;
    private String number;
    private String description;
    private Interested interested;
    private Subject subject;
    private Organization originEntity;
    private Situation situation;
    private String observation;
    private LocalDateTime registrationDate; //Hora registro do processo no banco
	
	@Override
	public Long getId() {
		return id;
	}

	@Override
	public void setId(Long processId) {
		this.id = id;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Interested getInterested() {
		return interested;
	}

	public void setInterested(Interested interested) {
		this.interested = interested;
	}

	public Subject getSubject() {
		return subject;
	}

	public void setSubject(Subject subject) {
		this.subject = subject;
	}

	public Organization getOriginEntity() {
		return originEntity;
	}

	public void setOriginEntity(Organization originEntity) {
		this.originEntity = originEntity;
	}

	public Situation getSituation() {
		return situation;
	}

	public void setSituation(Situation situation) {
		this.situation = situation;
	}

	public String getObservation() {
		return observation;
	}

	public void setObservation(String observation) {
		this.observation = observation;
	}

	public LocalDateTime getRegistrationDate() {
		return registrationDate;
	}

	public void setRegistrationDate(LocalDateTime registrationDate) {
		this.registrationDate = registrationDate;
	}

	@Override
	public String toXml() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void validate() throws ValidationException {
		// TODO Auto-generated method stub

	}

}
