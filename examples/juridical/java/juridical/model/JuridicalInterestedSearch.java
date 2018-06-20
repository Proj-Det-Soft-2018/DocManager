package juridical.model;

import business.exception.ValidationException;
import business.model.Search;

public class JuridicalInterestedSearch implements Search {

	String cpf;
	String email;
	
    @Override
    public void validate() throws ValidationException {
    	boolean invalidCpf = (cpf == null || cpf.isEmpty() || cpf.length() != 11);
    	boolean invalidEmail = (email == null || email.isEmpty());
    	//TODO fazer validação do email?
    	if(invalidCpf) {
            throw new ValidationException("O CPF buscado está incompleto!");
        }
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }
    
    public String getEmail() {
		return email;
	}
    
    public void setEmail(String email) {
		this.email = email;
	}

}
