package persistencia;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import negocio.IDao;
import negocio.dominio.Interessado;
/**
 * 
 * @author Allan
 *
 */
public class InteressadoDao implements IDao<Interessado> {
	
	private static final HashMap<Integer, Interessado> banco = new HashMap<Integer, Interessado>();

	public void salvar(Interessado bean) {
		banco.put(bean.hashCode(), bean);
		
	}

	public void atualizar(Interessado bean) {
		// TODO Auto-generated method stub
	}

	public void deletar(Interessado bean) {
		banco.remove(bean.hashCode());
		
	}

	public Interessado getById(int id) {
		return banco.get(id);
	}

	public boolean contem(Interessado bean) {
		return banco.containsKey(bean.hashCode());
	}

	public List<Interessado> getAll() {
		return new ArrayList<Interessado>(banco.values());
	}

}
