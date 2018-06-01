package persistence;

public class DaoFactoryJDBC extends DaoFactory {
	
	public static ProcessDao getProcessDao(){
		return new HealthProcessDaoMySql();
	}
	
	public static InterestedDao getInterestedDao(){
		return new HealthInterestedDaoMySql();
	}

}
