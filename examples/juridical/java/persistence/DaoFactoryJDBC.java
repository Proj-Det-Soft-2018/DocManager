package health.persistence;

import persistence.DaoFactory;
import persistence.InterestedDao;
import persistence.ProcessDao;

public class JuridicalDaoFactoryJDBC implements DaoFactory {
	
	@Override
	public ProcessDao getProcessDao(){
		return new JuridicalProcessDaoJDBC();
	}
	
	@Override
	public InterestedDao getInterestedDao(){
		return new JuridicalInterestedDaoJDBC();
	}

}
