package business.service;

import business.exception.ValidationException;
import persistence.exception.DatabaseException;

public interface Observer {

	public void update() throws ValidationException, DatabaseException;
}
