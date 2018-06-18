package purchase.model;

import java.util.ArrayList;
import java.util.List;

import business.model.Subject;

public enum PurchaseSubject implements Subject {
	;
	
	private String description;
	private String shortDescription;
	
	PurchaseSubject(String shortDescription, String description){
		this.shortDescription = shortDescription;
		this.description = description;
	}

	@Override
	public String getDescription() {
		return description;
	}
	
	@Override
	public String getShortDescription() {
		if (shortDescription == null) {
			return description;
		}
		return shortDescription;
	}
	
	@Override
	public int getId() {
	    return ordinal();
	}

	public static List<Subject> getAll() {
		List<Subject> subjectList = new ArrayList<>();
		for(PurchaseSubject subject : PurchaseSubject.values()) {
			subjectList.add(subject);
		}
		subjectList.remove(0);
		return subjectList;
	}
	
	public static PurchaseSubject getSubjectById(int id){
		return PurchaseSubject.values()[id];
	}
}
