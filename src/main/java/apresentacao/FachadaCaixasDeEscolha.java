package apresentacao;

import java.util.List;

/**
 * @author hugotho
 * 
 */
public interface FachadaCaixasDeEscolha extends FachadaArmazenamento {
	
	public List<String> getListaOrgaos();
	
	public List<String> getListaAssuntos();
	
	public List<String> getListaSituacoes();
}
