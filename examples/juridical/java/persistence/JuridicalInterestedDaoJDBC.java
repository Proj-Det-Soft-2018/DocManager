package persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import business.model.Interested;
import business.model.Search;
import juridical.model.JuridicalInterested;
import juridical.model.JuridicalInterestedSearch;
import persistence.exception.DatabaseException;
import purchase.persistence.ConnectionFactory;

public class JuridicalInterestedDaoJDBC implements InterestedDao {

	@Override
	public void save(Interested interested) throws DatabaseException {
		JuridicalInterested juridicalInterested = (JuridicalInterested)interested;
		String sql = "INSERT INTO interessados " +
				"(nome,cpf,contato)" +
				" VALUES (?,?,?)";

		Connection connection = null;
		PreparedStatement statement = null;
		try {
			connection = ConnectionFactory.getConnection();
			statement = connection.prepareStatement(sql);
			statement.setString(1,juridicalInterested.getName());
			statement.setString(2,juridicalInterested.getCpf());
			statement.setString(3,juridicalInterested.getContact());

			statement.executeUpdate();

		} catch (SQLException e) {
			throw new DatabaseException("Não foi possível salvar o interessado no Banco de Dados.", e);
		}
		finally {
			ConnectionFactory.closeConnection(connection, statement);
		}

	}

	@Override
	public void update(Interested interested) throws DatabaseException {
		JuridicalInterested juridicalInterested = (JuridicalInterested)interested;
		String sql = "UPDATE interessados " +
				"SET nome=?, cpf=?, contato=? " +
				"WHERE id=?";
		Connection connection = null;
		PreparedStatement statement = null;
		try {
			connection = ConnectionFactory.getConnection();
			statement = connection.prepareStatement(sql);

			statement.setString(1, juridicalInterested.getName());
			statement.setString(2, juridicalInterested.getCpf());
			statement.setString(3, juridicalInterested.getContact());
			statement.setLong(4, juridicalInterested.getId());

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
		JuridicalInterestedSearch search = (JuridicalInterestedSearch) searchData;
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
				interested = new JuridicalInterested(
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
