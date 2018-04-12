/**
 * 
 */
package persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import business.model.Interested;

/**
 * @author clah
 * @since 30/03/2018
 */
public class InteressadoDaoMySql implements InteressadoDao{

	@Override
	public void salvar(Interested novoInteressado) {
		String sql = "INSERT INTO interessados " +
                		"(nome,cpf,contato)" +
                		" VALUES (?,?,?)";
		
		Connection con = null;
		PreparedStatement stmt = null;
		try {
			con = ConnectionFactory.getConnection();
			stmt = con.prepareStatement(sql);
			stmt.setString(1,novoInteressado.getNome());
	        stmt.setString(2,novoInteressado.getCpf());
	        stmt.setString(3,novoInteressado.getContato());
	        
			stmt.executeUpdate();
			
		} catch (SQLException e) {
			//TODO resolver
			throw new RuntimeException(e);
		}
		finally {
			ConnectionFactory.fechaConnection(con, stmt);
		}
	}
	
	
	@Override
	public void atualizar(Interested interessadoModificado) {
		String sql = "UPDATE interessados " +
					 "SET nome=?, cpf=?, contato=? " +
					 "WHERE id=?";
		Connection con = null;
		PreparedStatement stmt = null;
		
	    try {
	    	con = ConnectionFactory.getConnection();
			stmt = con.prepareStatement(sql);
			
			stmt.setString(1, interessadoModificado.getNome());
	        stmt.setString(2, interessadoModificado.getCpf());
	        stmt.setString(3, interessadoModificado.getContato());
	        stmt.setLong(4, interessadoModificado.getId());
	        
	        stmt.executeUpdate();
	        
	    } catch (SQLException e) {
	    	//TODO resolver
	        throw new RuntimeException(e);
	    }finally {
			ConnectionFactory.fechaConnection(con, stmt);
		}
	
	}
	

	@Override
	public void deletar(Interested processo) {
		Connection con = null;
		PreparedStatement stmt = null;
		try {
			con = ConnectionFactory.getConnection();
	        stmt = con.prepareStatement("DELETE FROM interessados WHERE id=?");
	        stmt.setLong(1, processo.getId());
	        stmt.executeUpdate();
	        
	    } catch (SQLException e) {
	    	//TODO resolver
	        throw new RuntimeException(e);
	    }finally {
	    	ConnectionFactory.fechaConnection(con, stmt);
		}
		
	}
	
	
	@Override
	public Interested pegarPeloId(Long id) {
		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;

		try {
			con = ConnectionFactory.getConnection();
			
			stmt = con.prepareStatement("SELECT * FROM interessados WHERE id=?");
			stmt.setLong(1, id);
			
			rs = stmt.executeQuery();
			
			Interested interessado = null;
			
			if(rs.next()) {
				//criando o objeto Interessado
				interessado = new Interested(
						rs.getLong("id"),
						rs.getString("nome"),
						rs.getString("cpf"),
						rs.getString("contato"));
			}
			
			return interessado;
			
		} catch (SQLException e) {
			//TODO resolver
			throw new RuntimeException("Erro no pegarPeloId Interessado: "+ e);
		}finally {
			ConnectionFactory.fechaConnection(con, stmt, rs);
		}
	}
	
	@Override
	public Interested pegarPeloCpf(String cpf) {
		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		try {
			con = ConnectionFactory.getConnection();
			
			stmt = con.prepareStatement("SELECT * FROM interessados WHERE cpf=?");
			stmt.setString(1, cpf);
			
			rs = stmt.executeQuery();
			
			Interested interessado = null;
			
			if(rs.next()) {
				//criando o objeto Interessado
				interessado = new Interested(
						rs.getLong("id"),
						rs.getString("nome"),
						rs.getString("cpf"),
						rs.getString("contato"));
				
			}
			
			return interessado;
			
		} catch (SQLException e) {
			//TODO resolver
			throw new RuntimeException("Erro no pegarPeloCpf Interessado: "+ e);
		}finally {
			ConnectionFactory.fechaConnection(con, stmt, rs);
		}
	}

	@Override
	public boolean contem(Interested interessado) {
		Interested interessadoBuscado = this.pegarPeloId(interessado.getId());
		
		return (interessadoBuscado!=null) ? true: false;
		
	}

	@Override
	public List<Interested> pegarTodos() {
		
		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		try {
			con = ConnectionFactory.getConnection();
			stmt = con.prepareStatement("SELECT * FROM interessados");
			rs = stmt.executeQuery();
			List<Interested> interessados = new ArrayList<Interested>();
			
			while(rs.next()) {
				//criando o objeto Interessado
				Interested interessado = new Interested(
						rs.getLong("id"),
						rs.getString("nome"),
						rs.getString("cpf"),
						rs.getString("contato"));
				interessados.add(interessado);
			}
			return interessados;
		} catch (SQLException e) {
			//TODO resolver
			throw new RuntimeException("Erro no pegarTodos Interessado: "+ e);
		}finally {
			ConnectionFactory.fechaConnection(con, stmt, rs);
		}
	
	}


	@Override
	public List<Interested> burcarPeloNome(String nome) {
		// TODO Auto-generated method stub
		return null;
	}

}
