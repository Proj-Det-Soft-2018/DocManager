/**
 * 
 */
package persistencia;

import negocio.GenericoDao;
import negocio.dominio.Orgao;
import negocio.dominio.Situacao;

/**
 * @author clarissa
 * @since 25/03/2018
 */
public class FabricaDao {
	
	private FabricaDao() {
		
	}
	
	public static GenericoDao<Orgao> criarOrgaoDao() {
		return new OrgaoDao();
	}
	
	
}
