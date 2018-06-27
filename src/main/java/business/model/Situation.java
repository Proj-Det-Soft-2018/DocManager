package business.model;

import java.util.List;
/**
 * Representação de uma Situação em um Processo
 */
public interface Situation {	
	
    String getDescription();
    int getId();
    /**
     * Consultar as situações possíveis de acordo com a situação atual
     * @return Uma lista de inteiros referente as possíveis situações
     */
    List<Situation> getlinkedNodes();
}
