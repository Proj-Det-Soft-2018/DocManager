/**
 * 
 */
package negocio.dominio;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import negocio.GenericoDao;
import persistencia.FabricaDao;

/**
 * @author clah
 *
 */
public class Situacao {
	
	
	private static List<Situacao> db = new ArrayList<Situacao>();
	static{{
		db.add( new Situacao(1,"analise","Análise"));
		db.add(new Situacao (2,"convocarligar","A convocar"));
		db.add(new Situacao(3,"aguardandodocumento","Aguardando Documento"));
		db.add(new Situacao(4,"agendado","Agendado"));
		db.add( new Situacao(5,"concluido","Concluido"));
		
	}}
	
	private int id;
	private String codigo;	
	private String descricao;
	
	public Situacao() {
		
	}
	
	public Situacao(int id, String codigo, String descricao) {
		this.id = id;
		this.codigo = codigo;
		this.descricao = descricao;		
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

	/**
	 * @return the db
	 */
	public static List<Situacao> getDb() {
		return db;
	}	
	
	public String[] todosNomes() {
		String[] todosNomesSituacao = null;
		int i = 0;
		for (Situacao situacao : Situacao.getDb().values()) {
			todosNomesSituacao[i] = situacao.getDescricao();
			i++;
		}
		
		return todosNomesSituacao;
		
	}
}
