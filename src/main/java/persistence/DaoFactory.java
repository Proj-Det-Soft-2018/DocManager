/**
 * 
 */
package persistence;

/**
 * @author clah
 *
 */
public interface DaoFactory {
	
	ProcessDao getProcessDao();
	
	InterestedDao getInterestedDao();
}
