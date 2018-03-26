/**
 * 
 */
package negocio.dominio;

import java.util.ArrayList;
import java.util.List;

import negocio.GenericoDao;
import persistencia.FabricaDao;

/**
 * @author clah
 *
 */
public class Orgao {
	
	private static List<Orgao> db = new ArrayList<Orgao>();
	static{
		db.add(new Orgao("UFRN"));
		db.add(new Orgao("DPF"));
		db.add(new Orgao("MTE"));
		db.add(new Orgao("DPRF"));
		db.add(new Orgao("FUNAI"));	
	}
	
	
	
	private int id;
	private String codigo;
	private String nome;
	private String sigla;
	private GenericoDao<Orgao> banco;
	
	public Orgao () {
		this.banco = FabricaDao.criarOrgaoDao();
	}
	
	public Orgao(String nome) {
		this.nome = nome;
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getSigla() {
		return sigla;
	}

	public void setSigla(String sigla) {
		this.sigla = sigla;
	}
	
	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	public void salvar() {
		banco.salvar(this);
		
	}
	
	public static Orgao getById(int id) {
		return db.get(id);
	}
	
	/**
	 * @return the db
	 */
	public static List<Orgao> getDb() {
		return db;
	}	
	
	public String[] todosNomes() {
		String[] nomes = new String[db.size()];
		int i = 0;
		for (Orgao orgao : db) {
			nomes[i++] = orgao.getNome();
		}
		return nomes;
	}
	
	public void remover() {
		banco.deletar(this);
	}

	
	
}
