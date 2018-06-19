package purchase.model;

import java.util.ArrayList;
import java.util.List;

import business.model.Organization;

public enum PurchaseOrganization implements Organization {
	;

private String fullName;
	
	PurchaseOrganization(String fullName) {
		this.fullName = fullName;
	}
	
	@Override
	public String getFullName() {
		return fullName;
	}
	
	@Override
	public String getInitials(){
	    return name();
	}
	
	@Override
	public int getId() {
	    return ordinal();
	}

	public static List<Organization> getAll() {
		List<Organization> organizationList = new ArrayList<>();
		for(PurchaseOrganization organization : PurchaseOrganization.values()) {
			organizationList.add(organization);
		}
		organizationList.remove(0);
		return organizationList;
	}
	
	public static PurchaseOrganization getOrganizationById(int id){
		return PurchaseOrganization.values()[id];
	}
}
