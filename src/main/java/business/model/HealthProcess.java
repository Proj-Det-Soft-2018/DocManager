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
@XmlSeeAlso(HealthInterested.class)
public class HealthProcess implements Process {
	
	private static final Logger logger = Logger.getLogger(HealthProcess.class);
	
	private Long id;
	private boolean oficio;
	private String number;
	private Interested interested;
	private Subject subject;
	private Organization originEntity;
	private Situation situation;
	private String observation;
	private LocalDateTime registrationDate; //Hora registro do processo no banco
	private LocalDateTime dispatchDate; //Hora que altera e grava situação para concluido

	public HealthProcess() {

	}

	public HealthProcess(Long id, boolean tipoOficio, String number, String observation) {
		this.id = id;
		this.oficio = tipoOficio;
		this.number = number;
		this.observation = observation;
	}

	/* (non-Javadoc)
	 * @see business.model.Process#getId()
	 */
	@Override
	@XmlTransient
	public Long getId() {
		return id;
	}

	/* (non-Javadoc)
	 * @see business.model.Process#setId(java.lang.Long)
	 */
	@Override
	public void setId(Long processId) {
		this.id = processId;
	}

	/* (non-Javadoc)
	 * @see business.model.Process#isOficio()
	 */
	@Override
	@XmlTransient
	public boolean isOficio() {
		return oficio;
	}

	/* (non-Javadoc)
	 * @see business.model.Process#setTipoOficio(boolean)
	 */
	@Override
	public void setTipoOficio(boolean oficio) {
		this.oficio = oficio;
	}
	
	/* (non-Javadoc)
	 * @see business.model.Process#getType()
	 */
	@Override
	@XmlElement(name="type")
	public String getType () {
		return this.oficio? "Ofício" : "Processo";
	}

	/* (non-Javadoc)
	 * @see business.model.Process#getFormattedNumber()
	 */
	@Override
	@XmlElement(name="number")
	public String getFormattedNumber() {
		if(this.isOficio()) {
			return this.number.replaceAll("(\\d{4})(\\d{4})(\\w)", "$1/$2-$3");
		}
		else {
			return this.number.replaceAll("(\\d{5})(\\d{6})(\\d{4})(\\d{2})", "$1.$2/$3-$4");
		}
	}
	
	/* (non-Javadoc)
	 * @see business.model.Process#getNumber()
	 */
	@Override
	@XmlTransient
	public String getNumber() {
		return number;
	}

	/* (non-Javadoc)
	 * @see business.model.Process#setNumber(java.lang.String)
	 */
	@Override
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
	
	/* (non-Javadoc)
	 * @see business.model.Process#getIntersted()
	 */
	@Override
	@XmlElement(name="interested")
	public Interested getIntersted() {
		return interested;
	}

	/* (non-Javadoc)
	 * @see business.model.Process#setInterested(business.model.HealthInterested)
	 */
	@Override
	public void setInterested(Interested interested) {
		this.interested = interested;
	}

	
	/* (non-Javadoc)
	 * @see business.model.Process#getSubjectString()
	 */
	@Override
	@XmlElement(name="subject")
	public String getSubjectString() {
		return subject.getText();
	}
	
	/* (non-Javadoc)
	 * @see business.model.Process#getSubject()
	 */
	@Override
	public Subject getSubject() {
		return subject;
	}

	/* (non-Javadoc)
	 * @see business.model.Process#setSubjectById(int)
	 */
	@Override
	public void setSubjectById(int subjectId) throws ValidationException {
		if(subjectId == 0) {
			throw new ValidationException("Campo assunto é obrigatório.");
		}
		this.subject = Subject.getSubjectById(subjectId);
	}
	
	/* (non-Javadoc)
	 * @see business.model.Process#getOriginEntityString()
	 */
	@Override
	@XmlElement(name="origin-entity")
	public String getOriginEntityString(){
		return originEntity.getFullName();
	}
	
	/* (non-Javadoc)
	 * @see business.model.Process#getOriginEntity()
	 */
	@Override
	public Organization getOriginEntity() {
		return originEntity;
	}
	
	/* (non-Javadoc)
	 * @see business.model.Process#setOriginEntityById(int)
	 */
	@Override
	public void setOriginEntityById(int originEntityId) throws ValidationException {
		if(originEntityId == 0) {
			throw new ValidationException("O campo Orgão é obrigatório.");
		}
		this.originEntity = Organization.getOrganizationById(originEntityId);
	}
	
	/* (non-Javadoc)
	 * @see business.model.Process#getSituationString()
	 */
	@Override
	@XmlElement(name="situation")
	public String getSituationString() {
		return situation.getStatus();
	}
	
	/* (non-Javadoc)
	 * @see business.model.Process#getSituation()
	 */
	@Override
	public Situation getSituation() {
		return situation;
	}

	/* (non-Javadoc)
	 * @see business.model.Process#setSituationById(int)
	 */
	@Override
	public void setSituationById(int situationId) throws ValidationException {
		if(situationId == 0) {
			throw new ValidationException("O campo Situação é obrigatório.");
		}
		this.situation = Situation.getSituationById(situationId);
	}
	
	/* (non-Javadoc)
	 * @see business.model.Process#getObservation()
	 */
	@Override
	@XmlElement(name="observation")
	public String getObservation() {
		return observation;
	}

	/* (non-Javadoc)
	 * @see business.model.Process#setObservation(java.lang.String)
	 */
	@Override
	public void setObservation(String observation) {
		this.observation = observation;
	}
	
	/* (non-Javadoc)
	 * @see business.model.Process#getRegistrationDate()
	 */
	@Override
	@XmlElement(name="entry-date")
	public LocalDateTime getRegistrationDate() {
		return registrationDate;
	}

	/* (non-Javadoc)
	 * @see business.model.Process#setRegistrationDate(java.time.LocalDateTime)
	 */
	@Override
	public void setRegistrationDate(LocalDateTime registrationDate) {
		this.registrationDate = registrationDate;
	}
	
	/* (non-Javadoc)
	 * @see business.model.Process#getDispatchDate()
	 */
	@Override
	@XmlElement(name="out")
	public LocalDateTime getDispatchDate() {
		return dispatchDate;
	}

	/* (non-Javadoc)
	 * @see business.model.Process#setDispatchDate(java.time.LocalDateTime)
	 */
	@Override
	public void setDispatchDate(LocalDateTime dispatchDate) throws ValidationException {
		if(dispatchDate.isAfter(this.registrationDate)) {
			this.dispatchDate = dispatchDate;
		}
		else {
			throw new ValidationException("Verifique a Data e a Hora do seu computador.");
		}
	}
	
	/* (non-Javadoc)
	 * @see business.model.Process#toXml()
	 */
	@Override
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