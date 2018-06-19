package persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import business.model.Interested;
import business.model.Search;
import persistence.exception.DatabaseException;
import purchase.model.PurchaseInterested;

public class PurchaseInterestedDaoJDBC implements InterestedDao{

	public void save(Interested interested) throws DatabaseException {
		PurchaseInterested purchaseInterested = (PurchaseInterested)interested;
		String sql = "INSERT INTO fornecedores " +
				"(cnpj,nome,cpf,contato)" +
				" VALUES (?,?,?,?)";

		Connection connection = null;
		PreparedStatement statement = null;
		try {
			connection = ConnectionFactory.getConnection();
			statement = connection.prepareStatement(sql);
			statement.setString(1, purchaseInterested.getCnpj());
			statement.setString(2,purchaseInterested.getLiableName());
			statement.setString(3,purchaseInterested.getLiableCpf());
			statement.setString(4,purchaseInterested.getContact());

			statement.executeUpdate();

		} catch (SQLException e) {
			throw new DatabaseException("Não foi possível salvar o fornecedor no Banco de Dados.", e);
		}
		finally {
			ConnectionFactory.closeConnection(connection, statement);
		}

	}

	public void update(Interested interested) throws DatabaseException {
		PurchaseInterested purchaseInterested = (PurchaseInterested)interested;
		String sql = "UPDATE fornecedores " +
				"SET cnpj=?, nome=?, cpj=? contato=? " +
				"WHERE id=?";
		Connection connection = null;
		PreparedStatement statement = null;
		try {
			connection = ConnectionFactory.getConnection();
			statement = connection.prepareStatement(sql);

			statement.setString(1, purchaseInterested.getCnpj());
			statement.setString(2, purchaseInterested.getLiableName());
			statement.setString(3, purchaseInterested.getLiableCpf());
			statement.setString(4, purchaseInterested.getContact());
			statement.setLong(5, purchaseInterested.getId());

			statement.executeUpdate();

		} catch (SQLException e) {
			throw new DatabaseException("Não foi possível atualizar o fornecedor no Banco de Dados.", e);
		}finally {
			ConnectionFactory.closeConnection(connection, statement);
		}

	}

	public void delete(Interested interested) throws DatabaseException {
		Connection connection = null;
		PreparedStatement statement = null;
		try {
			connection = ConnectionFactory.getConnection();
			statement = connection.prepareStatement("DELETE FROM fornecedores WHERE id=?");
			statement.setLong(1, interested.getId());
			statement.executeUpdate();

		} catch (SQLException e) {
			throw new DatabaseException("Não foi possível deletar o fornecedor do Banco de Dados.", e);
		}finally {
			ConnectionFactory.closeConnection(connection, statement);
		}
	}

	public Interested search(Search searchData) throws DatabaseException {
		PurchaseInterested search = (PurchaseInterested) searchData;
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;

		try {
			connection = ConnectionFactory.getConnection();

			statement = connection.prepareStatement("SELECT * FROM fornecedores WHERE cnpj=?");
			statement.setString(1, search.getCnpj());

			resultSet = statement.executeQuery();

			PurchaseInterested interested = null;

			if(resultSet.next()) {
				//criando o objeto Interessado

				interested = new PurchaseInterested();
				interested.setId(resultSet.getLong("id"));
				interested.setCnpj(resultSet.getString("cnpj"));
				interested.setBusinessName(resultSet.getString("nome"));
				interested.setLiableCpf(resultSet.getString("cpf"));
				interested.setContact(resultSet.getString("contato"));


			}

			return interested;

		} catch (SQLException e) {
			throw new DatabaseException("Não foi possível recuperar o fornecedor pelo CNPJ", e);
		}finally {
			ConnectionFactory.closeConnection(connection, statement, resultSet);
		}
	}

}
