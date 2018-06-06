/**
 * 
 */
package persistence;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import business.exception.ValidationException;
import business.model.Process;
import business.model.Search;
import persistence.exception.DatabaseException;

/**
 * @author clah
 *
 */
public interface ProcessDao{
	
	public void save(Process newProcess) throws DatabaseException, ValidationException;
	public void update(Process modifiedProcess) throws DatabaseException;
	public void delete(Process process) throws DatabaseException;
	//public Process getById(Long id) throws DatabaseException;
	//public boolean contains(Process process) throws DatabaseException;
	//public List<Process> getAll() throws DatabaseException;
	public List<Process> getAllProcessesByPriority() throws DatabaseException; 
	public List<Process> searchByNumber(String number) throws DatabaseException;
	public List<Process> searchAll(Search searchData) throws DatabaseException;
	
	//Statistics lists
	public Map<Integer, ArrayList<Integer>> getQuantityProcessPerMonthYearList() throws DatabaseException;
	public Map<Integer, Integer> getQuantityProcessPerSituationList() throws DatabaseException;
	public Map<Integer, ArrayList<Integer>> getQuantityProcessPerMonthFromLastYearList() throws DatabaseException;
	public Map<Integer, Integer> getQuantityProcessPerOrganizationList() throws DatabaseException;
	public Map<Integer, Integer> getQuantityProcessPerSubjectList() throws DatabaseException;
	
}
