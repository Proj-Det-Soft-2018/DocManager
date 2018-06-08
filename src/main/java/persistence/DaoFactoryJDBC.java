package persistence;

public class DaoFactoryJDBC implements DaoFactory {
	
	@Override
	public ProcessDao getProcessDao(){
		return new HealthProcessDaoJDBC();
	}
	
	@Override
	public InterestedDao getInterestedDao(){
		return new HealthInterestedDaoJDBC();
	}

}
