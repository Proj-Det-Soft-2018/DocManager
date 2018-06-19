package purchase.model;

import java.util.ArrayList;
import java.util.List;

import business.model.Situation;

public enum PurchaseSituation implements Situation {
	;

	private String description;

	  private int[] linkedNodesIndexes;

	  PurchaseSituation(String description, int[] neighborNodes){
	    this.description = description;
	    this.linkedNodesIndexes = neighborNodes;
	  }

	  public static List<Situation> getAll() {
	    List<Situation> situationList = new ArrayList<>();
	    for(PurchaseSituation situation : PurchaseSituation.values()) {
	      situationList.add(situation);
	    }
	    situationList.remove(0);
	    return situationList;
	  }

	  @Override
	  public String getDescription() {
	    return description;
	  }

	  @Override
	  public int getId() {
	    return ordinal();
	  }

	  @Override
	  public List<Situation> getlinkedNodes() {
	    List<Situation> linkedNodes = new ArrayList<>();
	    for(int i: linkedNodesIndexes) {
	      linkedNodes.add(getSituationById(i));
	    }
	    return linkedNodes;
	  }

	  public static PurchaseSituation getSituationById(int id){
	    return PurchaseSituation.values()[id];
	  }
}
