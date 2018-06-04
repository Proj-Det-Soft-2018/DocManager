package business.model;

import business.exception.ValidationException;

public interface Search {
    
    void validate() throws ValidationException;
}
