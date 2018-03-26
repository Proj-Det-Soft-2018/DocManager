/**
 * 
 */
package negocio.dominio;

import java.util.ArrayList;
import java.util.List;

/**
 * @author clah
 *
 */
public class Situacao {
	
	
	private static List<Situacao> db = new ArrayList<Situacao>();
	static{{
		db.add( new Situacao("analise","Análise"));
		db.add(new Situacao ("convocarligar","A convocar"));
		db.add(new Situacao("aguardandodocumento","Aguardando Documento"));
		db.add(new Situacao("agendado","Agendado"));
		db.add( new Situacao("concluido","Concluido"));
		
	}}
	
	private int id;
	private String codigo;	
	private String descricao;
	
	public Situacao() {
		
	}
	
	public Situacao(String codigo, String descricao) {
		this.codigo = codigo;
		this.descricao = descricao;		
	}
	
	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}



	/**
	 * @return the codigo
	 */
	public String getCodigo() {
		return codigo;
	}

	/**
	 * @param codigo the codigo to set
	 */
	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	
	public static Situacao getById(int id) {
		return db.get(id);
	}

	/**
	 * @return the db
	 */
	public static List<Situacao> getDb() {
		return db;
	}	
	
	public String[] todosNomes() {
		return db.toArray(new String[db.size()]);
	}
}
