package business.model;

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
	
	public void validate() throws ValidationException;

}