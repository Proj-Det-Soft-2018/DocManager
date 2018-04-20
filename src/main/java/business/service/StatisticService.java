/**
 * 
 */
package business.service;

import java.util.ArrayList;
import java.util.Map;

import persistence.exception.DatabaseException;

/**
 * @author clah
 * @since 04.20.2018
 */
public interface StatisticService {
	
	public Map<Integer,ArrayList<Integer>> quantityProcessPerMonthYear() throws DatabaseException;
	
	
	public Map<Integer,Integer> quantityProcessPerSituation() throws DatabaseException;

}
