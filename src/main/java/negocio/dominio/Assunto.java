/**
 * 
 */
package negocio.dominio;

import java.util.ArrayList;
import java.util.List;

/**
 * @author clah
 * @since 25/03/2018
 */
public class Assunto {
	
	private static List<Assunto> db = new ArrayList<Assunto>();
	static{{
		db.add( new Assunto("Isenção de IRPF sobre Aposentadoria"));
		db.add( new Assunto("Regime Domiciliar"));
		db.add( new Assunto("Avaliaçao para Fins de Pensão"));
		db.add( new Assunto("Remoção por Motivo de Saúde do Servidor"));

	}}
	
	private int id;
	private String nome;	
	
	public Assunto() {
		
	}
	
	public Assunto(String nome) {
		this.nome = nome;
	}
	
	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}
	
	
	/**
	 * @return the nome
	 */
	public String getNome() {
		return nome;
	}

	/**
	 * @param nome the nome to set
	 */
	public void setNome(String nome) {
		this.nome = nome;
	}

	public static Assunto getById(int id) {
		return db.get(id);
	}

	/**
	 * @return the db
	 */
	public static List<Assunto> getDb() {
		return db;
	}	
	
	public String[] todosNomes() {
		return db.toArray(new String[db.size()]);
		
	}

}
