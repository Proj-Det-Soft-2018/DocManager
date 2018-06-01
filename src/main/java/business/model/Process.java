package business.model;

import java.time.LocalDateTime;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlTransient;

import business.exception.ValidationException;

public interface Process {

	/**
	 * @return the id
	 */
	Long getId();

	void setId(Long processId);

	/**
	 * @return the tipoOficio
	 */
	boolean isOficio();

	/**
	 * @param oficio the tipoOficio to set
	 */
	void setTipoOficio(boolean oficio);

	String getType();

	String getFormattedNumber();

	String getNumber();

	void setNumber(String number) throws ValidationException;

	Interested getIntersted();

	void setInterested(Interested interested);

	String getSubjectString();

	/**
	 * @return assunto
	 */
	Subject getSubject();

	/**
	 * @throws ValidationException 
	 */
	void setSubjectById(int subjectId) throws ValidationException;

	String getOriginEntityString();

	Organization getOriginEntity();

	/**
	 * @throws ValidationException 
	 */
	void setOriginEntityById(int originEntityId) throws ValidationException;

	String getSituationString();

	Situation getSituation();

	/**
	 * @throws ValidationException 
	 */
	void setSituationById(int situationId) throws ValidationException;

	String getObservation();

	void setObservation(String observation);

	LocalDateTime getRegistrationDate();

	void setRegistrationDate(LocalDateTime registrationDate);

	LocalDateTime getDispatchDate();

	void setDispatchDate(LocalDateTime dispatchDate) throws ValidationException;

	String toXml();

}