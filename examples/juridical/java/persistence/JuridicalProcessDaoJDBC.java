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
import business.model.Interested;
import business.model.Process;
import business.model.Search;
import juridical.model.JuridicalInterested;
import juridical.model.JuridicalProcess;
import juridical.model.JuridicalProcessSearch;
import juridical.model.JuridicalSituation;
import persistence.exception.DatabaseException;

public class JuridicalProcessDaoJDBC implements ProcessDao {

	@Override
	public void save(Process process) throws DatabaseException, ValidationException {
		JuridicalProcess juridicalProcess = (JuridicalProcess)process;

		String sql = "INSERT INTO processos"
				+ "(numero,id_interessado,"
				+ "assunto,situacao,orgao_origem,"
				+ "id_advogado, id_inventariado, observacao, data_entrada)"
				+ " values (?,?,?,?,?,?,?,?,?)";

		Connection connection = null;
		PreparedStatement statement=null;
		try {
			connection = ConnectionFactory.getConnection();
			statement = connection.prepareStatement(sql);

			statement.setString(1, juridicalProcess.getNumber());
			statement.setLong(2, juridicalProcess.getInventorian().getId());
			statement.setLong(3, juridicalProcess.getJudge().getId());
			statement.setInt(4, juridicalProcess.getSituation().getId());
			statement.setInt(5, juridicalProcess.getCourt().getId());
			statement.setLong(6, juridicalProcess.getLawyer().getId());
			statement.setLong(7, juridicalProcess.getInventoried().getId());
			statement.setString(8, juridicalProcess.getObservation());

			//Definindo data de entrada no banco de dados
			LocalDateTime date = LocalDateTime.now();
			juridicalProcess.setRegistrationDate(date);

			Timestamp stamp = Timestamp.valueOf(date);
			Date registrationDate = new Date (stamp.getTime());

			statement.setDate(9,registrationDate);
			statement.executeUpdate();


		} catch (SQLException e) {
			throw new DatabaseException("Não foi possível salvar o processo no Banco de Dados.", e);
		}finally {
			ConnectionFactory.closeConnection(connection, statement);
		}

	}

	@Override
	public void update(Process process) throws DatabaseException {
		JuridicalProcess juridicalProcess = (JuridicalProcess)process;

		String query = "UPDATE processos SET "
				+ "numero=?, id_interessado=?, assunto=?,"
				+ "situacao=?, orgao_origem=?, id_advogado=?,"
				+ "id_inventariado=?, observacao=?,"
				+ " WHERE id=?";

		Connection connection = null;
		PreparedStatement statement=null;

		try {

			connection = ConnectionFactory.getConnection();
			statement = connection.prepareStatement(query);
			statement.setString(1, juridicalProcess.getNumber());
			statement.setLong(2, juridicalProcess.getInventorian().getId());
			statement.setInt(3, juridicalProcess.getJudge().getId());
			statement.setInt(4, juridicalProcess.getSituation().getId());
			statement.setInt(5, juridicalProcess.getCourt().getId());
			statement.setLong(6, juridicalProcess.getLawyer().getId());
			statement.setLong(7, juridicalProcess.getInventoried().getId());
			statement.setString(8, juridicalProcess.getObservation());

			//setando id do processo a ser modificado
			statement.setLong(9, juridicalProcess.getId());


			statement.executeUpdate();


		} catch (SQLException e) {
			throw new DatabaseException("Não foi possível atualizar o processo no Banco de Dados.", e);
		}finally {
			ConnectionFactory.closeConnection(connection, statement);
		}

	}

	@Override
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

	@Override
	public List<Process> getAllProcessesByPriority() throws DatabaseException {
		//TODO ordem de busca
		int situationId = JuridicalSituation.NULL.getId();
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

	@Override
	public List<Process> searchByNumber(String number) throws DatabaseException {
		String sql = "WHERE numero LIKE '"+number+"'";
		return this.pullProcessList(sql);
	}

	@Override
	public List<Process> searchAll(Search searchData) throws DatabaseException {

		JuridicalProcessSearch search = (JuridicalProcessSearch) searchData;
		StringBuilder sql = new StringBuilder("WHERE ");
		final String AND = " AND ";
		//TODO implementar juridicalprocesssearch
		
		String number = search.getNumber();
		if (number != null && !number.equalsIgnoreCase("")) {
			sql.append("numero LIKE '"+number+"' AND ");
		}

		String name = search.getInventorian();
		if (name != null && !name.equalsIgnoreCase("")) {
			sql.append("nome LIKE '%"+name+"%' AND ");
		}

		String cpf = search.getCpf();
		if (cpf != null && !cpf.equalsIgnoreCase("")) {
			sql.append("cpf= '"+cpf+"' AND ");
		}

		int organizationId = search.getCourtId();
		if (organizationId != 0) {
			sql.append("orgao_origem="+organizationId+AND);
		}

		int subjectId = search.getJudgeId();
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

	private List<Process> pullProcessList(String whereStament) throws DatabaseException {

		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		String query = "SELECT * "
				+ "FROM processos p "
				+ "INNER JOIN interessados i "
				+ "ON p.interessado_id=i.id "
				+ whereStament;

		List<Process> processList = new ArrayList<>();

		try {
			connection = ConnectionFactory.getConnection();

			statement = connection.prepareStatement(query);

			resultSet = statement.executeQuery();

			while(resultSet.next()) {

				//criando objeto Interessado
				Interested interested = new JuridicalInterested(
						resultSet.getLong("interessado_id"),
						resultSet.getString("nome"),
						resultSet.getString("cpf"),
						resultSet.getString("contato"));

				//criando o objeto Processo
				JuridicalProcess process = new JuridicalProcess();
				//TODO criar os setbyid
				/*
				resultSet.getString("numero"),
				resultSet.getLong("id_interessado"),
				resultSet.getInt("orgao_origem"),
				resultSet.getInt("assunto"),
				resultSet.getInt("situacao"),
				resultSet.getInt("id_advogado"),
				resultSet.getInt("id_inventariado"),
				resultSet.getString("observacao"));
				process.setInterested(interested);

				//falta resolver unidade destino /orgao_saida, se vai ter ou não
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

				//Convertendo data Saida de java.sql.Date para LocalDateTime
				Date jdbcDispatchDate = resultSet.getDate("data_saida");
				if(jdbcDispatchDate != null) {
					Timestamp jdbcDispatchStamp = new Timestamp(resultSet.getDate("data_saida").getTime());
					LocalDateTime dispatchDate = jdbcDispatchStamp.toLocalDateTime();
					process.setDispatchDate(dispatchDate);
				}
				 */
				processList.add(process);

			}
		} catch (SQLException e) {
			throw new DatabaseException("Não foi possível buscar o processo no Banco.", e);
			//} catch (ValidationException e) {
			//throw new DatabaseException("Banco corrompido!", e);
		}finally {
			ConnectionFactory.closeConnection(connection, statement, resultSet);
		}

		return processList;
	}

	@Override
	public Map<Integer, ArrayList<Integer>> getQuantityProcessPerMonthYearList() throws DatabaseException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<Integer, Integer> getQuantityProcessPerSituationList() throws DatabaseException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<Integer, ArrayList<Integer>> getQuantityProcessPerMonthFromLastYearList() throws DatabaseException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<Integer, Integer> getQuantityProcessPerOrganizationList() throws DatabaseException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<Integer, Integer> getQuantityProcessPerSubjectList() throws DatabaseException {
		// TODO Auto-generated method stub
		return null;
	}

}
