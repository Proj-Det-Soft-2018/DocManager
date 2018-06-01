package persistence;

public class DaoFactoryJDBC extends DaoFactory {
	
	@Override
	public ProcessDao getProcessDao(){
		return new HealthProcessDaoJDBC();
	}
	
	@Override
	public InterestedDao getInterestedDao(){
		return new HealthInterestedDaoJDBC();
	}

}
