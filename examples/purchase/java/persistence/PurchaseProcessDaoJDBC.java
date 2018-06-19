package persistence;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import business.exception.ValidationException;
import business.model.Process;
import business.model.Search;
import persistence.exception.DatabaseException;
import purchase.model.PurchaseInterested;
import purchase.model.PurchaseProcess;
import purchase.model.PurchaseProcessSearch;
import purchase.model.PurchaseSituation;

public class PurchaseProcessDaoJDBC implements ProcessDao {

	public void save(Process process) throws DatabaseException, ValidationException {
		//Antes de salvar verificar os campos que nao podem ser nulos
		PurchaseProcess purchaseProcess = (PurchaseProcess)process;
		//TODO verificar duplicado
		//this.checkDuplicate(purchaseProcess.getNumber());


		String sql = "INSERT INTO processos"
				+ "(numero,id_fornecedor,"
				+ "assunto,situacao,orgao_origem,"
				+ "observacao,data_entrada)"
				+ " values (?,?,?,?,?,?,?,?)";

		Connection connection = null;
		PreparedStatement statement=null;
		try {
			connection = ConnectionFactory.getConnection();
			statement = connection.prepareStatement(sql);

			statement.setString(1, purchaseProcess.getNumber());
			statement.setLong(2, purchaseProcess.getInterested().getId());
			statement.setInt(3, purchaseProcess.getSubject().getId());
			statement.setInt(4, purchaseProcess.getSituation().getId());
			statement.setInt(5, purchaseProcess.getOriginEntity().getId());
			statement.setString(6, purchaseProcess.getObservation());

			//Definindo data de entrada no banco de dados
			LocalDateTime date = LocalDateTime.now();
			purchaseProcess.setRegistrationDate(date);

			Timestamp stamp = Timestamp.valueOf(date);
			Date registrationDate = new Date (stamp.getTime());

			statement.setDate(7,registrationDate);
			statement.executeUpdate();


		} catch (SQLException e) {
			throw new DatabaseException("Não foi possível salvar o processo no Banco de Dados.", e);
		}finally {
			ConnectionFactory.closeConnection(connection, statement);
		}

	}

	public void update(Process process) throws DatabaseException {
		PurchaseProcess purchaseProcess = (PurchaseProcess)process;

		String query = "UPDATE processos SET "
				+ "numero=?, id_fornecedor=?, assunto=?,"
				+ "situacao=?, orgao_origem=?, observacao=?,"
				+ " WHERE id=?";

		Connection connection = null;
		PreparedStatement statement=null;

		try {

			connection = ConnectionFactory.getConnection();
			statement = connection.prepareStatement(query);
			statement.setString(1, purchaseProcess.getNumber());
			statement.setLong(2, purchaseProcess.getInterested().getId());
			statement.setInt(3, purchaseProcess.getSubject().getId());
			statement.setInt(4, purchaseProcess.getSituation().getId());
			statement.setInt(5, purchaseProcess.getOriginEntity().getId());
			statement.setString(6, purchaseProcess.getObservation());

			//setando id do processo a ser modificado
			statement.setLong(7, purchaseProcess.getId());


			statement.executeUpdate();


		} catch (SQLException e) {
			throw new DatabaseException("Não foi possível atualizar o processo no Banco de Dados.", e);
		}finally {
			ConnectionFactory.closeConnection(connection, statement);
		}

	}

	public void delete(Process process) throws DatabaseException {
		Connection connection = null;
		PreparedStatement statement=null;
		
		try {
			connection = ConnectionFactory.getConnection();
			statement = connection.prepareStatement("DELETE FROM processos WHERE id=?");
	        statement.setLong(1, process.getId());
	        statement.executeUpdate();
			
			
		} catch (SQLException e) {
			throw new DatabaseException("Não foi possível deletar o processo do Banco de Dados.", e);
		}finally {
			ConnectionFactory.closeConnection(connection, statement);
		}

	}

	public List<Process> getAllProcessesByPriority() throws DatabaseException {
		//TODO analisar qual a situacao final do processo
		//TODO implementar logica de prioridades
		int situationId = PurchaseSituation.PAGOLIQUD.getId();
		String sql = "WHERE situacao != "+situationId+
		             " ORDER BY data_entrada ASC" +
		             " LIMIT 50";
		List<Process> intermediaryList = pullProcessList(sql);
		if (intermediaryList.size() < 50) {
		    sql = "WHERE situacao = "+situationId+
		          " ORDER BY data_entrada DESC"+
		          " LIMIT "+(50 - intermediaryList.size());
		    intermediaryList.addAll(pullProcessList(sql));
		}
		return intermediaryList;
	}
	
private List<Process> pullProcessList(String whereStament) throws DatabaseException {
		
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		String query = "SELECT * "
						+ "FROM processos p "
						+ "INNER JOIN fornecedores f "
						+ "ON p.id_fornecedor=f.id "
						+ whereStament;
		
		List<Process> processList = new ArrayList<>();
		
		try {
			connection = ConnectionFactory.getConnection();
			
			statement = connection.prepareStatement(query);
			
			resultSet = statement.executeQuery();
			
			while(resultSet.next()) {
				
				//criando objeto Interessado
				PurchaseInterested interested = new PurchaseInterested();
				interested.setCnpj(resultSet.getString("cnpj"));
				interested.setId(resultSet.getLong("id_fornecedor"));
				interested.setLiableName(resultSet.getString("nome"));
				interested.setLiableCpf(resultSet.getString("cpf"));
				interested.setContact(resultSet.getString("contato"));

				
				//criando o objeto Processo
				PurchaseProcess process = new PurchaseProcess();
				process.setId(resultSet.getLong("id"));
				process.setNumber(resultSet.getString("numero"));
				process.setObservation(resultSet.getString("observacao"));
				process.setInterested(interested);
				
				
				process.setSubjectById(resultSet.getInt("assunto"));
				process.setOriginEntityById(resultSet.getInt("orgao_origem"));
				process.setSituationById(resultSet.getInt("situacao"));

				
				//Convertendo data entrada de java.sql.Date para LocalDateTime
				Date jdbcRegistrationDate = resultSet.getDate("data_entrada");
				if(jdbcRegistrationDate != null) {
					Timestamp jdbcRegistrationStamp = new Timestamp(jdbcRegistrationDate.getTime());
					LocalDateTime registrationDate = jdbcRegistrationStamp.toLocalDateTime();
					process.setRegistrationDate(registrationDate);
				}
				
				processList.add(process);
			
			}
		} catch (SQLException e) {
			throw new DatabaseException("Não foi possível buscar o processo no Banco.", e);
		}finally {
			ConnectionFactory.closeConnection(connection, statement, resultSet);
		}
		
		return processList;
	}

	public List<Process> searchByNumber(String number) throws DatabaseException {
		String sql = "WHERE numero LIKE '"+number+"'";
		return this.pullProcessList(sql);
	}
	
	//TODO busca por todos
//*
	public List<Process> searchAll(Search searchData) throws DatabaseException {
		 PurchaseProcessSearch search = (PurchaseProcessSearch) searchData;
			StringBuilder sql = new StringBuilder("WHERE ");
			final String AND = " AND ";
			
			String number = search.getNumber();
			if (number != null && !number.equalsIgnoreCase("")) {
				sql.append("numero LIKE '"+number+"' AND ");
			}
			
			String name = search.getBusinessName();
			if (name != null && !name.equalsIgnoreCase("")) {
				sql.append("nome LIKE '%"+name+"%' AND ");
			}
			
			String cnpj = search.getCnpj();
			if (cnpj != null && !cnpj.equalsIgnoreCase("")) {
				sql.append("cpf= '"+cnpj+"' AND ");
			}
			
			int organizationId = search.getOrganizationId();
			if (organizationId != 0) {
				sql.append("orgao_origem="+organizationId+AND);
			}
			
			int subjectId = search.getSubjectId();
			if (subjectId != 0) {
				sql.append("assunto="+subjectId+AND);
			}
			
			int situationId = search.getSituationId();
			if (situationId != 0) {
				sql.append("situacao="+situationId);
			} else {
				sql.delete(sql.lastIndexOf(AND), sql.length());
			}
			
			return this.pullProcessList(sql.toString());
	}
//*/

	public Map<Integer, ArrayList<Integer>> getQuantityProcessPerMonthYearList() throws DatabaseException {
		// TODO Auto-generated method stub
		return null;
	}

	public Map<Integer, Integer> getQuantityProcessPerSituationList() throws DatabaseException {
		// TODO Auto-generated method stub
		return null;
	}

	public Map<Integer, ArrayList<Integer>> getQuantityProcessPerMonthFromLastYearList() throws DatabaseException {
		// TODO Auto-generated method stub
		return null;
	}

	public Map<Integer, Integer> getQuantityProcessPerOrganizationList() throws DatabaseException {
		// TODO Auto-generated method stub
		return null;
	}

	public Map<Integer, Integer> getQuantityProcessPerSubjectList() throws DatabaseException {
		// TODO Auto-generated method stub
		return null;
	}

}
