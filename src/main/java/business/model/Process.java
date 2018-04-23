package business.model;

import java.io.IOException;
import java.io.StringWriter;
import java.time.LocalDateTime;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlTransient;

import org.apache.log4j.Logger;

import business.exception.ValidationException;

/**
 * @author lets
 *
 */
@XmlRootElement
@XmlSeeAlso(Interested.class)
public class Process {
	
	private static final Logger logger = Logger.getLogger(Process.class);
	
	private Long id;
	private boolean oficio;
	private String number;
	private Interested interested;
	private Subject subject;
	private Organization originEntity;
	private Situation situation;
	private String observation;
	private Organization destinationEntity; //para onde o processo é dirigido quando concluido
	private LocalDateTime registrationDate; //Hora registro do processo no banco
	private LocalDateTime dispatchDate; //Hora que altera e grava situação para concluido

	public Process() {

	}

	public Process(Long id, boolean tipoOficio, String number, String observation) {
		this.id = id;
		this.oficio = tipoOficio;
		this.number = number;
		this.observation = observation;
	}

	/**
	 * @return the id
	 */
	@XmlTransient
	public Long getId() {
		return id;
	}

	public void setId(Long processId) {
		this.id = processId;
	}

	/**
	 * @return the tipoOficio
	 */
	@XmlTransient
	public boolean isOficio() {
		return oficio;
	}

	/**
	 * @param oficio the tipoOficio to set
	 */
	public void setTipoOficio(boolean oficio) {
		this.oficio = oficio;
	}
	
	@XmlElement(name="type")
	public String getType () {
		return this.oficio? "Ofício" : "Processo";
	}

	@XmlElement(name="number")
	public String getFormattedNumber() {
		if(this.isOficio()) {
			return this.number.replaceAll("(\\d{4})(\\d{4})(\\w)", "$1/$2-$3");
		}
		else {
			return this.number.replaceAll("(\\d{5})(\\d{6})(\\d{4})(\\d{2})", "$1.$2/$3-$4");
		}
	}
	
	@XmlTransient
	public String getNumber() {
		return number;
	}

	public void setNumber(String number) throws ValidationException {
		if(this.oficio == true) {
			if(number.length() < 8) {
				throw new ValidationException("O número digitado é inválido.");
			}
			else {
				if(!number.substring(0, 7).matches("[0-9]+")) {
					throw new ValidationException("O número digitado é inválido.");
				}
			}
		}
		else {
			if(!(number.length() == 17) || !(number.matches("[0-9]+"))) {
				throw new ValidationException("O número digitado é inválido.");
			}
		}
		this.number = number;
	}
	
	@XmlElement(name="interested")
	public Interested getIntersted() {
		return interested;
	}

	public void setInterested(Interested interested) {
		this.interested = interested;
	}

	
	@XmlElement(name="subject")
	public String getSubjectString() {
		return subject.getText();
	}
	
	/**
	 * @return assunto
	 */
	public Subject getSubject() {
		return subject;
	}

	/**
	 * @throws ValidationException 
	 */
	public void setSubjectById(int subjectId) throws ValidationException {
		if(subjectId == 0) {
			throw new ValidationException("Campo assunto é obrigatório.");
		}
		this.subject = Subject.getSubjectById(subjectId);
	}
	
	@XmlElement(name="origin-entity")
	public String getOriginEntityString(){
		return originEntity.getFullName();
	}
	
	public Organization getOriginEntity() {
		return originEntity;
	}
	
	/**
	 * @throws ValidationException 
	 */
	public void setOriginEntityById(int originEntityId) throws ValidationException {
		if(originEntityId == 0) {
			throw new ValidationException("O campo Orgão é obrigatório.");
		}
		this.originEntity = Organization.getOrganizationById(originEntityId);
	}
	
	@XmlElement(name="situation")
	public String getSituationString() {
		return situation.getStatus();
	}
	
	public Situation getSituation() {
		return situation;
	}

	/**
	 * @throws ValidationException 
	 */
	public void setSituationById(int situationId) throws ValidationException {
		if(situationId == 0) {
			throw new ValidationException("O campo Situação é obrigatório.");
		}
		this.situation = Situation.getSituationById(situationId);
	}
	
	@XmlElement(name="observation")
	public String getObservation() {
		return observation;
	}

	public void setObservation(String observation) {
		this.observation = observation;
	}
	
	@XmlElement(name="entry-date")
	public LocalDateTime getRegistrationDate() {
		return registrationDate;
	}

	public void setRegistrationDate(LocalDateTime registrationDate) {
		this.registrationDate = registrationDate;
	}
	
	public Organization getDestinationEntity() {
		return destinationEntity;
	}

	public void setDestinationEntity(Organization destinationEntity) {
		this.destinationEntity = destinationEntity;
	}
	
	@XmlElement(name="out")
	public LocalDateTime getDispatchDate() {
		return dispatchDate;
	}

	public void setDispatchDate(LocalDateTime dispatchDate) throws ValidationException {
		if(dispatchDate.isAfter(this.registrationDate)) {
			this.dispatchDate = dispatchDate;
		}
		else {
			throw new ValidationException("Verifique a Data e a Hora do seu computador.");
		}
	}
	
	public String toXml() {
		
		StringWriter stringWriter = new StringWriter();
		String xml = null;
		
		try {
			// Conversão do Objeto para um XML
			JAXBContext jaxbContext = JAXBContext.newInstance(this.getClass());
			Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
			jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, false);
			jaxbMarshaller.marshal(this, stringWriter);
			
			xml = stringWriter.toString();
		} catch (JAXBException e) {
			// TODO Nova Exceção?
			logger.error(e.getMessage(), e);
		} finally {
			// Fecha o reader e o writer
			try {
				stringWriter.close();
			} catch (IOException e) {
				// Não conseguiu fechar o writer
				logger.fatal(e.getMessage(), e);
			}
		}
		
		return xml;
	}
}
