/**
 * 
 */
package persistencia;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import negocio.GenericoDao;
import negocio.dominio.Interessado;

/**
 * @author clah
 * @since 30/03/2018
 */
public class InteressadoDaoMySql implements GenericoDao<Interessado> {

	@Override
	public void salvar(Interessado bean) {
		String sql = "insert into interessados " +
                "(nome,cpf,contato)" +
                " values (?,?,?)";
		
		Connection con = null;
		PreparedStatement stmt = null;
		try {
			con = ConnectionFactory.getConnection();
			stmt = con.prepareStatement(sql);
			System.out.println(bean.getNome());
			stmt.setString(1,bean.getNome());
	        stmt.setString(2,bean.getCpf());
	        stmt.setString(3,bean.getContato());
	        
			stmt.executeUpdate();
			
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		finally {
			ConnectionFactory.fechaConnection(con, stmt);
		}
	}

	@Override
	public void atualizar(Interessado bean) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deletar(Interessado bean) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Interessado getById(String id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean contem(Interessado bean) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<Interessado> getAll() {
		// TODO Auto-generated method stub
		return null;
	}

}
