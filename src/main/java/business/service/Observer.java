package business.service;

import persistence.DatabaseException;

public interface Observer {

	public void update() throws ValidationException, DatabaseException;
}
