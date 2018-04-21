/**
 * 
 */
package persistence;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import business.exception.ValidationException;
import business.model.Interested;
import business.model.Process;
import persistence.exception.DatabaseException;

/**
 * @author clah
 * @since 01/04/2018
 */
public class ProcessoDaoMySql implements ProcessoDao{
	
	@Override
	public void save(Process process) throws DatabaseException {
		
		String sql = "INSERT INTO processos"
					+ "(eh_oficio,numero,interessado_id,"
                	+ "assunto,situacao,orgao_origem,"
                	+ "observacao,data_entrada)"
                	+ " values (?,?,?,?,?,?,?,?)";
		
		Connection connection = null;
		PreparedStatement statement=null;
		try {
			connection = ConnectionFactory.getConnection();
			statement = connection.prepareStatement(sql);
			
			statement.setBoolean(1, process.isOficio());
			statement.setString(2, process.getNumber());
			statement.setLong(3, process.getIntersted().getId());
			statement.setInt(4, process.getSubject().ordinal());
			statement.setInt(5, process.getSituation().ordinal());
			statement.setInt(6, process.getOriginEntity().ordinal());
			statement.setString(7, process.getObservation());
			
			//Definindo data de entrada no banco de dados
			LocalDateTime date = LocalDateTime.now();
			process.setRegistrationDate(date);
			
			Timestamp stamp = Timestamp.valueOf(date);
			Date registrationDate = new Date (stamp.getTime());
			
			statement.setDate(8,registrationDate);
			statement.executeUpdate();
			
			
		} catch (SQLException e) {
			throw new DatabaseException("Não foi possível salvar o processo no Banco de Dados.");
		}finally {
			ConnectionFactory.closeConnection(connection, statement);
		}
		
		
	}
	
	
	@Override
	public void update(Process process) throws DatabaseException {
		
		String query = "UPDATE processos SET "
					+ "numero=?, interessado_id=?, assunto=?,"
					+ "situacao=?, orgao_origem=?, observacao=?,"
					+ "eh_oficio=?"
					+ " WHERE id=?";
		
		Connection connection = null;
		PreparedStatement statement=null;
		
		try {
			
			connection = ConnectionFactory.getConnection();
			statement = connection.prepareStatement(query);
			statement.setString(1, process.getNumber());
			statement.setLong(2, process.getIntersted().getId());
			statement.setInt(3, process.getSubject().ordinal());
			statement.setInt(4, process.getSituation().ordinal());
			statement.setInt(5, process.getOriginEntity().ordinal());
			statement.setString(6, process.getObservation());
			statement.setBoolean(7, process.isOficio());
			
			//setando id do processo a ser modificado
			statement.setLong(8, process.getId());
			
			
			statement.executeUpdate();
			
			
		} catch (SQLException e) {
			throw new DatabaseException("Não foi possível atualizar o processo no Banco de Dados.");
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
			throw new DatabaseException("Não foi possível deletar o processo do Banco de Dados.");
		}finally {
			ConnectionFactory.closeConnection(connection, statement);
		}
		
	}
	
	
	@Override
	public Process getById(Long id) throws ValidationException, DatabaseException {
		String sql = "WHERE p.id="+id.toString();
		List<Process> processList = this.searcher(sql);
		if(processList.isEmpty() || processList ==null) {
			return null;
		}else {
			//TODO verificar o getById
			return processList.get(0);
		} 
		
	}

	@Override
	public boolean contains(Process process) throws ValidationException, DatabaseException {		
		Process foundProcess = this.getById(process.getId());
		
		return (foundProcess!=null) ? true : false;
		
	}
	
	@Override
	public List<Process> getAll() throws ValidationException, DatabaseException {
		String sql = "ORDER BY data_entrada DESC LIMIT 50";
		return this.searcher(sql);
		
	}

	
	private List<Process> searcher(String whereStament) throws ValidationException, DatabaseException {
		
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		String query = "SELECT * "
						+ "FROM processos p "
						+ "INNER JOIN interessados i "
						+ "ON p.interessado_id=i.id "
						+ whereStament;
		
		try {
			connection = ConnectionFactory.getConnection();
			
			statement = connection.prepareStatement(query);
			
			resultSet = statement.executeQuery();
			
			List<Process> processList = new ArrayList<>();
			
			while(resultSet.next()) {
				
				//criando objeto Interessado
				Interested interested = new Interested(
						resultSet.getLong("interessado_id"),
						resultSet.getString("nome"),
						resultSet.getString("cpf"),
						resultSet.getString("contato"));
				
				//criando o objeto Processo
				Process process = new Process(
						resultSet.getLong("id"),
						resultSet.getBoolean("eh_oficio"),
						resultSet.getString("numero"),
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
				
				processList.add(process);
			
			}
			
			return processList;

		} catch (SQLException e) {
			throw new DatabaseException("Não foi possível buscar o processo no Banco.");
		}finally {
			ConnectionFactory.closeConnection(connection, statement, resultSet);
		}
	}
	
	
	@Override
	public List<Process> searchByNumber(String number) throws ValidationException, DatabaseException {
		String sql = "WHERE numero LIKE '"+number+"'";
		return this.searcher(sql);
	}

	public List<Process> multipleSearch(String number, String name, String cpf, int organizationId,
			int subjectId, int situationId) throws ValidationException, DatabaseException {
		StringBuilder sql = new StringBuilder("WHERE ");
		final String AND = " AND ";
		
		if (number != null && !number.equalsIgnoreCase("")) {
			sql.append("numero LIKE '"+number+"' AND ");
		}
		if (name != null && !name.equalsIgnoreCase("")) {
			sql.append("nome LIKE '%"+name+"%' AND ");
		}
		if (cpf != null && !cpf.equalsIgnoreCase("")) {
			sql.append("cpf= '"+cpf+"' AND ");
		}
		if (organizationId != 0) {
			sql.append("orgao_origem="+organizationId+AND);
		}
		if (subjectId != 0) {
			sql.append("assunto="+subjectId+AND);
		}
		if (situationId != 0) {
			sql.append("situacao="+situationId);
		} else {
			sql.delete(sql.lastIndexOf(AND), sql.length());
		}
		return this.searcher(sql.toString());
	}
	
	//Methods to resolve statistic solutions

	@Override
	public Map<Integer, ArrayList<Integer>> getQuantityProcessPerMonthYearList() throws DatabaseException {
		String query = "SELECT COUNT(id), EXTRACT(year from data_entrada) as ano, EXTRACT(month from data_entrada) AS mes "
						+ "FROM processos "
						+ "GROUP BY ano, mes ORDER BY ano, mes";
		
		return this.builderMapIntArrayInt(query);
	}
	
	@Override
	public Map<Integer, ArrayList<Integer>> getQuantityProcessPerMonthFromLastYearList() throws DatabaseException {
		String query = "SELECT COUNT(id), EXTRACT(year from data_entrada) as ano, EXTRACT(month from data_entrada) AS mes "
						+ "FROM (SELECT * FROM processos WHERE data_entrada BETWEEN CURDATE() - INTERVAL 1 YEAR AND CURDATE() ) AS processosUltimoAno "
						+ "GROUP BY ano, mes ORDER BY ano, mes";
		return this.builderMapIntArrayInt(query);
	}


	private Map<Integer, ArrayList<Integer>> builderMapIntArrayInt(String query) throws DatabaseException {
		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		Map<Integer, ArrayList<Integer>> list = new HashMap<>();
		
		connection = ConnectionFactory.getConnection();
		
		try {
			statement = connection.prepareStatement(query);
			resultSet = statement.executeQuery();
			
			while(resultSet.next()) {
				ArrayList<Integer> row = new ArrayList<>();
                if (!list.containsKey(resultSet.getInt("ano")))
                {
                    row.add(resultSet.getInt("mes"));
                    row.add(resultSet.getInt("count(id)"));
                    list.put(resultSet.getInt("ano"), row);
                }else{
                    ArrayList<Integer> newRow = list.get(resultSet.getInt("ano"));
                    newRow.add(resultSet.getInt("mes"));
                    newRow.add(resultSet.getInt("count(id)"));
                }
			}
			
			return list;

		} catch (SQLException e) {
			throw new DatabaseException("Problema no SQL:"+e.getMessage());
		}
		finally {
			ConnectionFactory.fechaConnection(con, stmt, rs);
		}
	}
	
	@Override
	public Map<Integer, Integer> getQuantityProcessPerSituationList() throws DatabaseException {
		String category = "situacao";
		return this.builderMapIntInt(category);
		
	}
	
	@Override
	public Map<Integer, Integer> getQuantityProcessPerOrganizationList() throws DatabaseException {
		String category = "orgao_origem";
		return this.builderMapIntInt(category);
	}
	
	@Override
	public Map<Integer, Integer> getQuantityProcessPerSubjectList() throws DatabaseException {
		String category = "assunto";
		return this.builderMapIntInt(category);
	}
	
	private Map<Integer, Integer> builderMapIntInt(String categoryColumn) throws DatabaseException{
		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		String query = "SELECT COUNT(id) AS qtde, " + categoryColumn +" FROM processos "
				+ "GROUP BY "+ categoryColumn +" ORDER BY "+ categoryColumn;
		
		Map<Integer, Integer> list = new HashMap<>();
		
		connection = ConnectionFactory.getConnection();
		
		try {
			statement = connection.prepareStatement(query);
			resultSet = statement.executeQuery();
			
			while(rs.next()) {
				Integer situation;
				Integer quantity;
				situation = rs.getInt(categoryColumn);
				quantity = rs.getInt("qtde");
				
				list.put(situation, quantity);
			}
			
			return list;

		} catch (SQLException e) {
			throw new DatabaseException("Problema no SQL:"+e.getMessage());
		}finally {
			ConnectionFactory.fechaConnection(con, stmt, rs);
		}

	}



	

	


}
