/**
 * 
 */
package persistence;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import business.model.HealthProcess;
import business.model.Process;
import persistence.exception.DatabaseException;

/**
 * @author clah
 *
 */
public interface ProcessDao{
	
	public void save(Process newProcess) throws DatabaseException;
	public void update(Process modifiedProcess) throws DatabaseException;
	public void delete(Process process) throws DatabaseException;
	public Process getById(Long id) throws DatabaseException;
	public boolean contains(Process process) throws DatabaseException;
	public List<HealthProcess> getAll() throws DatabaseException;
	public List<HealthProcess> searchByNumber(String number) throws DatabaseException;
	public List<HealthProcess> multipleSearch(String number, String interestedName, String interestedCpf, int organizationId,
			int subjectId, int situationId) throws DatabaseException;
	//Statistics lists
	public Map<Integer, ArrayList<Integer>> getQuantityProcessPerMonthYearList() throws DatabaseException;
	public Map<Integer, Integer> getQuantityProcessPerSituationList() throws DatabaseException;
	public Map<Integer, ArrayList<Integer>> getQuantityProcessPerMonthFromLastYearList() throws DatabaseException;
	public Map<Integer, Integer> getQuantityProcessPerOrganizationList() throws DatabaseException;
	public Map<Integer, Integer> getQuantityProcessPerSubjectList() throws DatabaseException;
	
}
