package business.model;

import java.time.LocalDateTime;

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

	void setNumber(String number);

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
	void setSubjectById(int subjectId);

	String getOriginEntityString();

	Organization getOriginEntity();

	/**
	 * @throws ValidationException 
	 */
	void setOriginEntityById(int originEntityId);

	String getSituationString();

	Situation getSituation();

	/**
	 * @throws ValidationException 
	 */
	void setSituationById(int situationId);

	String getObservation();

	//void setObservation(String observation);

	LocalDateTime getRegistrationDate();

	void setRegistrationDate(LocalDateTime registrationDate);

	LocalDateTime getDispatchDate();

	void setDispatchDate(LocalDateTime dispatchDate) throws ValidationException;

	String toXml();
	
	public void validate() throws ValidationException;

}