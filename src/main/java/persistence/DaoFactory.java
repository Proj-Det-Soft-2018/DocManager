/**
 * 
 */
package persistence;

/*
 * Interface para uma fábrica de Daos
 */
public interface DaoFactory {
	
	ProcessDao getProcessDao();
	
	InterestedDao getInterestedDao();
}
