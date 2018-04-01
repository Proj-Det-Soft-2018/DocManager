/**
 * 
 */
package persistencia;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
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
		String sql = "update interessados set nome=?, cpf=?, contato=?," +
	            "where id=?";
		Connection con = null;
		PreparedStatement stmt = null;
		
	    try {
	    	con = ConnectionFactory.getConnection();
			stmt = con.prepareStatement(sql);
			
			stmt.setString(1, bean.getNome());
	        stmt.setString(2, bean.getCpf());
	        stmt.setString(3, bean.getContato());
	        stmt.setLong(4, bean.getId());
	        
	        stmt.executeUpdate();
	        
	    } catch (SQLException e) {
	        throw new RuntimeException(e);
	    }finally {
			ConnectionFactory.fechaConnection(con, stmt);
		}
	
		
	}

	@Override
	public void deletar(Interessado bean) {
		Connection con = null;
		PreparedStatement stmt = null;
		try {
			con = ConnectionFactory.getConnection();
	        stmt = con.prepareStatement("delete " +
	                "from interessados where id=?");
	        stmt.setLong(1, bean.getId());
	        stmt.executeUpdate();
	        
	    } catch (SQLException e) {
	        throw new RuntimeException(e);
	    }finally {
	    	ConnectionFactory.fechaConnection(con, stmt);
		}
		
	}

	@Override
	public Interessado getById(String id) {
		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		try {
			con = ConnectionFactory.getConnection();
			
			stmt = con.prepareStatement("select * from interessados where id=?");
			stmt.setLong(1, Long.parseLong(id));
			
			rs = stmt.executeQuery();
			
			Interessado interessado = new Interessado();
			
			if(rs.next()) {
				//criando o objeto Interessado
				
				interessado.setId(rs.getLong("id"));
				interessado.setNome(rs.getString("nome"));
				interessado.setCpf(rs.getString("cpf"));
				interessado.setContato(rs.getString("contato"));
				
			}
			
			return interessado;
			
		} catch (SQLException e) {
			throw new RuntimeException("Erro no getAll Interessado: "+ e);
		}finally {
			ConnectionFactory.fechaConnection(con, stmt, rs);
		}
	}

	@Override
	public boolean contem(Interessado bean) {
		String id = bean.getId().toString();
		Interessado interessado = this.getById(id);
		if(interessado!=null) {
			return true;
		}else {
			return false;
		}
		
	}

	@Override
	public List<Interessado> getAll() {
		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		try {
			con = ConnectionFactory.getConnection();
			stmt = con.prepareStatement("select * from interessados");
			rs = stmt.executeQuery();
			List<Interessado> interessados = new ArrayList<Interessado>();
			
			while(rs.next()) {
				//criando o objeto Interessado
				Interessado interessado = new Interessado();
				interessado.setId(rs.getLong("id"));
				interessado.setNome(rs.getString("nome"));
				interessado.setCpf(rs.getString("cpf"));
				interessado.setContato(rs.getString("contato"));
				
				interessados.add(interessado);
			}
			
			return interessados;
		} catch (SQLException e) {
			throw new RuntimeException("Erro no getAll Interessado: "+ e);
		}finally {
			ConnectionFactory.fechaConnection(con, stmt, rs);
		}
	
	}

}
