package persistence;

public class DaoFactoryJDBC extends DaoFactory {
	
	public static ProcessoDao getProcessDao(){
		return new ProcessoDaoMySql();
	}
	
	public static InteressadoDao getInterestedDao(){
		return new InteressadoDaoMySql();
	}

}
