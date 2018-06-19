package purchase.model;

import business.exception.ValidationException;
import business.model.Search;

public class PurchaseInterestedSearch implements Search {

	String cnpj;

    @Override
    public void validate() throws ValidationException {
        if(cnpj == null || cnpj.length() != 14) {
            throw new ValidationException("O CNPJ buscado está incompleto!");
        }
    }

    public String getCpf() {
        return cnpj;
    }

    public void setCpf(String cnpj) {
        this.cnpj = cnpj;
    }
}
