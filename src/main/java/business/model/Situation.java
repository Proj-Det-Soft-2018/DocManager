package business.model;

import java.util.List;

public interface Situation {	
	
    String getDescription();
    int getId();
    List<Situation> getlinkedNodes();
}
