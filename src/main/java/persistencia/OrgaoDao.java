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
	ArrayList<Orgao> bancoOrgao = new ArrayList<Orgao>();
	private String[] todosNomes;
	
	public void salvar(Orgao bean) {
		bancoOrgao.add(bean.getId(),bean);
	}

	public void atualizar(Orgao bean) {
		bancoOrgao.add(bean);
	}

	public void deletar(Orgao bean) {
		bancoOrgao.add(bean);
	}

	public Orgao getById(int id) {
		return bancoOrgao.get(id);
	}

	public boolean contem(Orgao bean) {
		return bancoOrgao.contains(bean);
	}

	public List<Orgao> getAll() {
		
		return bancoOrgao;
	}
	
	public String[] listarNomes() {
		todosNomes = null;
		
		for (int i=0; i<bancoOrgao.size();i++) {
			todosNomes[i] = bancoOrgao.get(i).getNome();
		}
		
		return todosNomes;
		
	}

}
