/**
 * 
 */
package br.com.docmanager.model.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import br.com.docmanager.model.domain.Processo;

/**
 * Classe representa persistencia de dados
 * @author lets
 *
 */
public final class HashProcessoDao implements IDao<Processo> {
	
	private static final HashMap<String, Processo> banco = new HashMap<String, Processo>();
	
	
	public Processo buscarPorChave(String numeroProcesso) {
		return banco.get(numeroProcesso);
	}
	
	public boolean existeAlgum(String key) {
		return banco.containsKey(key);
	}

	public void salvar(Processo bean) {
		banco.put(bean.getNumero(), bean);
		
		
	}

	public void atualizar(Processo bean) {
		// TODO Auto-generated method stub
		
	}

	public void deletar(Processo processo) {
		banco.remove(processo.getNumero());
		
	}

	public Processo getById(Processo processo) {
		return banco.get(processo.getNumero());
	}

	public List<Processo> getAll() {
		List<Processo> listaProcessos =  new ArrayList<Processo>(banco.values());
		for (Processo processo : listaProcessos) {
		    System.out.println(processo.getNumero());
		}
			
		return listaProcessos;
	}
	
}
