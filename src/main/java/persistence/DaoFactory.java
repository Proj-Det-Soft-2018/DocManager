package persistence;

public class DaoFactory {
	
	public static ProcessoDao getProcessDao(){
		return new ProcessoDaoMySql();
	}
	
	public static InteressadoDao getInterestedDao(){
		return new InteressadoDaoMySql();
	}

}
