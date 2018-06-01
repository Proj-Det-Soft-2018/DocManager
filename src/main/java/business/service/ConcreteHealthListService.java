package business.service;

import java.util.List;

import business.model.Situation;

public class ConcreteHealthListService extends ListServiceAbstract {
		
	private static ConcreteHealthListService instance = new ConcreteHealthListService();
	
	public static ConcreteHealthListService getInstance() {
		return instance;
	}

	@Override
	protected List<String> reorganizeByPriotity(Situation current) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected Situation getCurrentProcessSituation() {
		// TODO Auto-generated method stub
		return null;
	}

}
