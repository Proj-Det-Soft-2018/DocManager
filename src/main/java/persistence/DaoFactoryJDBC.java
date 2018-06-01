package persistence;

public class DaoFactoryJDBC extends DaoFactory {
	
	public static ProcessDao getProcessDao(){
		return new HealthProcessDaoJDBC();
	}
	
	public static InterestedDao getInterestedDao(){
		return new HealthInterestedDaoJDBC();
	}

}
