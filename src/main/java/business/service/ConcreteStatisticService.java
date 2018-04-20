/**
 * 
 */
package business.service;

import java.util.ArrayList;
import java.util.Map;

import persistence.DaoFactory;
import persistence.ProcessoDao;
import persistence.exception.DatabaseException;

/**
 * @author clah
 * @since 04.20.2018
 */
public class ConcreteStatisticService implements StatisticService {
	
	private ProcessoDao processoDao;

	// Singleton
	private static final ConcreteStatisticService instance = new ConcreteStatisticService();

	private ConcreteStatisticService() {
		processoDao = DaoFactory.getProcessDao();
	}

	public static ConcreteStatisticService getInstance() {
		return instance;
	}
	
	@Override
	public Map<Integer, ArrayList<Integer>> quantityProcessPerMonthYear() throws DatabaseException {
		return processoDao.getQuantityProcessPerMonthYearList();
	}

	@Override
	public Map<Integer, Integer> quantityProcessPerSituation() throws DatabaseException {
		// TODO Auto-generated method stub
		return null;
	}

}
