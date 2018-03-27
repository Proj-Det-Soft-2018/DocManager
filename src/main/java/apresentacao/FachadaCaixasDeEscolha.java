package apresentacao;

import java.util.List;

public interface FachadaCaixasDeEscolha extends FachadaArmazenamento {
	
	public List<String> getListaOrgaos();
	
	public List<String> getListaTipoDocumento();
	
	public List<String> getListaSituacao();
}
