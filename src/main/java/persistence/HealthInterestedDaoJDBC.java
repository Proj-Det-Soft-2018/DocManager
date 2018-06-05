/**
 * 
 */
package persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import business.model.HealthInterested;
import business.model.HealthInterestedSearch;
import business.model.Interested;
import business.model.Search;
import persistence.exception.DatabaseException;

/**
 * @author clah
 * @since 30/03/2018
 */
public class HealthInterestedDaoJDBC implements InterestedDao{

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
			throw new DatabaseException("Não foi possível salvar o interessado no Banco de Dados.", e);
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
	        throw new DatabaseException("Não foi possível atualizar o interessado no Banco de Dados.", e);
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
	        throw new DatabaseException("Não foi possível deletar o processo do Banco de Dados.", e);
	    }finally {
	    	ConnectionFactory.closeConnection(connection, statement);
		}
		
	}
	
	@Override
	public Interested search(Search searchData) throws DatabaseException {
	    HealthInterestedSearch search = (HealthInterestedSearch) searchData;
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		
		try {
			connection = ConnectionFactory.getConnection();
			
			statement = connection.prepareStatement("SELECT * FROM interessados WHERE cpf=?");
			statement.setString(1, search.getCpf());
			
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
			throw new DatabaseException("Não foi possível recuperar o interessado pelo CPF", e);
		}finally {
			ConnectionFactory.closeConnection(connection, statement, resultSet);
		}
	}

}
