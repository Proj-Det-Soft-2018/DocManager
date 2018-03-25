/**
 * 
 */
package persistencia;

import java.util.ArrayList;
import java.util.List;

import negocio.GenericoDao;
import negocio.dominio.Situacao;

/**
 * @author clah
 *
 */
public class SituacaoDao implements GenericoDao<Situacao> {
	ArrayList<Situacao> bancoSituacao = new ArrayList<Situacao>();
	private String[] todosNomes;
	
	public void salvar(Situacao bean) {
		bancoSituacao.add(bean.getId(),bean);
		
	}

	public void atualizar(Situacao bean) {
		bancoSituacao.set(bean.getId(), bean);
	}

	public void deletar(Situacao bean) {
		bancoSituacao.remove(bean);
		
	}

	public Situacao getById(int id) {
		return bancoSituacao.get(id);
	}

	public boolean contem(Situacao bean) {
		// TODO Auto-generated method stub
		return false;
	}

	public List<Situacao> getAll() {
		return bancoSituacao;
	}
	
	public String[] listarNomes() {
		todosNomes = null;
		if(bancoSituacao.isEmpty()) {
			return null;
		}
		
		for (int i=0; i<bancoSituacao.size();i++) {
			todosNomes[i] = bancoSituacao.get(i).getDescricao();
		}
		
		return todosNomes;
		
	}
	
	

}
