/**
 * 
 */
package persistencia;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import negocio.IDao;
import negocio.dominio.Processo;

/**
 * @author clah
 *
 */
public class HashProcessoDao implements IDao<Processo> {
	private static final HashMap<Integer, Processo> banco = new HashMap<Integer, Processo>();
	
	
	public void salvar(Processo bean) {
		banco.put(bean.hashCode(), bean);
		
	}

	public void atualizar(Processo bean) {
		// TODO Auto-generated method stub
		
	}

	public void deletar(Processo bean) {
		banco.remove(bean.hashCode());
	}

	public Processo getById(int id) {
		// TODO Auto-generated method stub
		return banco.get(id);
	}

	public boolean contem(Processo bean) {
		return banco.containsKey(bean.hashCode());
	}

	public List<Processo> getAll() {
		List<Processo> listaProcessos =  new ArrayList<Processo>(banco.values());
		mostrarProcessos(listaProcessos);
		return listaProcessos;
	}
	
	public void mostrarProcessos(List<Processo> listaProcessos) {
		for (Processo processo : listaProcessos) {
		    System.out.println(processo.getNumero());
		}
	}

}
