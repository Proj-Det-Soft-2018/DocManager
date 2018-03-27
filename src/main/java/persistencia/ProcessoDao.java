	/**
 * 
 */
package persistencia;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import apresentacao.Documento;
import negocio.GenericoDao;
import negocio.dominio.Processo;

/**
 * @author clah
 *
 */
public class ProcessoDao implements GenericoDao<Processo> {
	
	private static final HashMap<String, Processo> banco = new HashMap<String, Processo>();
	
	
	public void salvar(Processo bean) {
		banco.put(bean.getNumero(), bean);
		
	}

	public void atualizar(Processo bean) {
		banco.replace(bean.getNumero(), bean);
	}

	public void deletar(Processo bean) {
		banco.remove(bean.getNumero());
	}

	public Processo getById(String id) {
		return banco.get(id);
	}

	public boolean contem(Processo bean) {
		return banco.containsKey(bean.getNumero());
	}

	public List<Processo> getAll() {
		List<Processo> listaProcessos =  new ArrayList<Processo>(banco.values());
		mostrarProcessos(listaProcessos);
		return listaProcessos;
	}
	
	public List<? extends Documento> pegarDocumentos(){
		return (List<Processo>)(banco.values());
		
	}
	
	public void mostrarProcessos(List<Processo> listaProcessos) {
		for (Processo processo : listaProcessos) {
		    System.out.println(processo.getNumero());
		}
	}
	

}
