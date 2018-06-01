/**
 * 
 */
package persistence;

/**
 * @author clah
 *
 */
public abstract class DaoFactory {
	
	public abstract ProcessDao getProcessDao();
	
	public abstract InterestedDao getInterestedDao();


}
