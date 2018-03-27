/**
 * 
 */
package persistencia;

import java.util.ArrayList;
import java.util.List;

import negocio.GenericoDao;
import negocio.dominio.Orgao;

/**
 * @author clah
 *
 */
public class OrgaoDao implements GenericoDao<Orgao> {
	private static final ArrayList<Orgao> bancoOrgao = new ArrayList<Orgao>();
	
	private List<String> todosNomes;
	
	
	public void salvar(Orgao bean) {
		bancoOrgao.add(bean);
	}

	public void atualizar(Orgao bean) {
		int indice = bancoOrgao.indexOf(bean);
		if(indice != -1) {
			bancoOrgao.add(indice, bean);
		}
	}

	public void deletar(Orgao bean) {
		bancoOrgao.remove(bean);
	}

	public Orgao getById(String id) {
		return null;
	}

	public boolean contem(Orgao bean) {
		return bancoOrgao.contains(bean);
	}

	public List<Orgao> getAll() {
		
		return bancoOrgao;
	}

}
