package purchase.model;

import javax.xml.bind.annotation.XmlRootElement;

import business.exception.ValidationException;
import business.model.Interested;

@XmlRootElement(name="interested")
public class PurchaseInterested implements Interested {
	private Long id;
	private String cnpj;
	private String businessName;
	private String liableCpf;
	private String liableName;
	private String contact;

	@Override
	public Long getId() {
		return id;
	}

	@Override
	public void setId(Long id) {
		this.id = id;
	}
	
	public String getCnpj() {
		return cnpj;
	}

	public void setCnpj(String cnpj) {
		this.cnpj = cnpj;
	}

	public String getBusinessName() {
		return businessName;
	}

	public void setBusinessName(String businessName) {
		this.businessName = businessName;
	}

	public String getLiableCpf() {
		return liableCpf;
	}

	public void setLiableCpf(String liableCpf) {
		this.liableCpf = liableCpf;
	}

	public String getLiableName() {
		return liableName;
	}

	public void setLiableName(String liableName) {
		this.liableName = liableName;
	}

	public String getContact() {
		return contact;
	}

	public void setContact(String contact) {
		this.contact = contact;
	}

	@Override
	public void validate() throws ValidationException {
		// TODO Auto-generated method stub
	}
}
