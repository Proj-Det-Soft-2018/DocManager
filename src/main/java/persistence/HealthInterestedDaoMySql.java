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

import business.model.HealthInterested;
import business.model.Interested;
import persistence.exception.DatabaseException;

/**
 * @author clah
 * @since 30/03/2018
 */
public class HealthInterestedDaoMySql implements InterestedDao{

	@Override
	public void save(Interested novoInteressado) throws DatabaseException {
		String sql = "INSERT INTO interessados " +
                		"(nome,cpf,contato)" +
                		" VALUES (?,?,?)";
		
		Connection connection = null;
		PreparedStatement statement = null;
		try {
			connection = ConnectionFactory.getConnection();
			statement = connection.prepareStatement(sql);
			statement.setString(1,novoInteressado.getName());
	        statement.setString(2,novoInteressado.getCpf());
	        statement.setString(3,novoInteressado.getContact());
	        
			statement.executeUpdate();
			
		} catch (SQLException e) {
			throw new DatabaseException("Não foi possível salvar o interessado no Banco de Dados.");
		}
		finally {
			ConnectionFactory.closeConnection(connection, statement);
		}
	}
	
	
	@Override
	public void update(Interested modifiedInterested) throws DatabaseException {
		String sql = "UPDATE interessados " +
					 "SET nome=?, cpf=?, contato=? " +
					 "WHERE id=?";
		Connection connection = null;
		PreparedStatement statement = null;
	    try {
	    	connection = ConnectionFactory.getConnection();
			statement = connection.prepareStatement(sql);
			
			statement.setString(1, modifiedInterested.getName());
	        statement.setString(2, modifiedInterested.getCpf());
	        statement.setString(3, modifiedInterested.getContact());
	        statement.setLong(4, modifiedInterested.getId());
	        
	        statement.executeUpdate();
	        
	    } catch (SQLException e) {
	        throw new DatabaseException("Não foi possível atualizar o interessado no Banco de Dados.");
	    }finally {
			ConnectionFactory.closeConnection(connection, statement);
		}
	
	}
	

	@Override
	public void delete(Interested interested) throws DatabaseException {
		Connection connection = null;
		PreparedStatement statement = null;
		try {
			connection = ConnectionFactory.getConnection();
	        statement = connection.prepareStatement("DELETE FROM interessados WHERE id=?");
	        statement.setLong(1, interested.getId());
	        statement.executeUpdate();
	        
	    } catch (SQLException e) {
	        throw new DatabaseException("Não foi possível deletar o processo do Banco de Dados.");
	    }finally {
	    	ConnectionFactory.closeConnection(connection, statement);
		}
		
	}
	
	
	@Override
	public Interested getById(Long id) throws DatabaseException {
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;

		try {
			connection = ConnectionFactory.getConnection();
			
			statement = connection.prepareStatement("SELECT * FROM interessados WHERE id=?");
			statement.setLong(1, id);
			
			resultSet = statement.executeQuery();
			
			Interested interessado = null;
			
			if(resultSet.next()) {
				//criando o objeto Interessado
				interessado = new HealthInterested(
						resultSet.getLong("id"),
						resultSet.getString("nome"),
						resultSet.getString("cpf"),
						resultSet.getString("contato"));
			}
			
			return interessado;
			
		} catch (SQLException e) {
			throw new DatabaseException("Não foi possível recuperar o interessado por ID");
		}finally {
			ConnectionFactory.closeConnection(connection, statement, resultSet);
		}
	}
	
	@Override
	public Interested getByCpf(String cpf) throws DatabaseException {
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		
		try {
			connection = ConnectionFactory.getConnection();
			
			statement = connection.prepareStatement("SELECT * FROM interessados WHERE cpf=?");
			statement.setString(1, cpf);
			
			resultSet = statement.executeQuery();
			
			Interested interested = null;
			
			if(resultSet.next()) {
				//criando o objeto Interessado
				interested = new HealthInterested(
						resultSet.getLong("id"),
						resultSet.getString("nome"),
						resultSet.getString("cpf"),
						resultSet.getString("contato"));
				
			}
			
			return interested;
			
		} catch (SQLException e) {
			throw new DatabaseException("Não foi possível recuperar o interessado pelo CPF");
		}finally {
			ConnectionFactory.closeConnection(connection, statement, resultSet);
		}
	}

	@Override
	public boolean contains(Interested interested) throws DatabaseException {
		Interested foundInterested = this.getById(interested.getId());
		
		return (foundInterested!=null) ? true: false;
		
	}

	@Override
	public List<HealthInterested> getAll() throws DatabaseException {
		
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		
		try {
			connection = ConnectionFactory.getConnection();
			statement = connection.prepareStatement("SELECT * FROM interessados");
			resultSet = statement.executeQuery();
			List<HealthInterested> interestedList = new ArrayList<HealthInterested>();
			
			while(resultSet.next()) {
				//criando o objeto Interessado
				HealthInterested interested = new HealthInterested(
						resultSet.getLong("id"),
						resultSet.getString("nome"),
						resultSet.getString("cpf"),
						resultSet.getString("contato"));
				interestedList.add(interested);
			}
			return interestedList;
		} catch (SQLException e) {
			throw new DatabaseException("Não foi possível recuperar todos os interessados.");
		}finally {
			ConnectionFactory.closeConnection(connection, statement, resultSet);
		}
	
	}

}
