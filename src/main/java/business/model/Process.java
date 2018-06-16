package business.model;

import business.exception.ValidationException;

public interface Process {

	/**
	 * @return the id
	 */
	Long getId();

	void setId(Long processId);

	String toXml();
	
	public void validate() throws ValidationException;

}