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
	public void salvar(Process novoProcesso) throws DatabaseException {
		
		String sql = "INSERT INTO processos"
					+ "(eh_oficio,numero,interessado_id,"
                	+ "assunto,situacao,orgao_origem,"
                	+ "observacao,data_entrada)"
                	+ " values (?,?,?,?,?,?,?,?)";
		
		Connection con = null;
		PreparedStatement stmt=null;
		try {
			con = ConnectionFactory.getConnection();
			stmt = con.prepareStatement(sql);
			
			stmt.setBoolean(1, novoProcesso.isOficio());
			stmt.setString(2, novoProcesso.getNumber());
			stmt.setLong(3, novoProcesso.getIntersted().getId());
			stmt.setInt(4, novoProcesso.getSubject().ordinal());
			stmt.setInt(5, novoProcesso.getSituation().ordinal());
			stmt.setInt(6, novoProcesso.getOriginEntity().ordinal());
			stmt.setString(7, novoProcesso.getObservation());
			
			//Definindo data de entrada no banco de dados
			LocalDateTime data = LocalDateTime.now();
			novoProcesso.setRegistrationDate(data);
			
			Timestamp stamp = Timestamp.valueOf(data);
			Date dataEntrada = new Date (stamp.getTime());
			
			stmt.setDate(8,dataEntrada);
			stmt.executeUpdate();
			
			
		} catch (SQLException e) {
			throw new DatabaseException("Não foi possível salvar o processo no Banco de Dados.");
		}finally {
			ConnectionFactory.fechaConnection(con, stmt);
		}
		
		
	}
	
	
	@Override
	public void atualizar(Process processoModificado) throws DatabaseException {
		
		String query = "UPDATE processos SET "
					+ "numero=?, interessado_id=?, assunto=?,"
					+ "situacao=?, orgao_origem=?, observacao=?,"
					+ "eh_oficio=?"
					+ " WHERE id=?";
		
		Connection con = null;
		PreparedStatement stmt=null;
		
		try {
			
			con = ConnectionFactory.getConnection();
			stmt = con.prepareStatement(query);
			stmt.setString(1, processoModificado.getNumber());
			stmt.setLong(2, processoModificado.getIntersted().getId());
			stmt.setInt(3, processoModificado.getSubject().ordinal());
			stmt.setInt(4, processoModificado.getSituation().ordinal());
			stmt.setInt(5, processoModificado.getOriginEntity().ordinal());
			stmt.setString(6, processoModificado.getObservation());
			stmt.setBoolean(7, processoModificado.isOficio());
			
			//setando id do processo a ser modificado
			stmt.setLong(8, processoModificado.getId());
			
			
			stmt.executeUpdate();
			
			
		} catch (SQLException e) {
			throw new DatabaseException("Não foi possível atualizar o processo no Banco de Dados.");
		}finally {
			ConnectionFactory.fechaConnection(con, stmt);
		}
		
	}

		
	@Override
	public void deletar(Process processo) throws DatabaseException {
		
		Connection con = null;
		PreparedStatement stmt=null;
		
		try {
			con = ConnectionFactory.getConnection();
			stmt = con.prepareStatement("DELETE FROM processos WHERE id=?");
	        stmt.setLong(1, processo.getId());
	        stmt.executeUpdate();
			
			
		} catch (SQLException e) {
			throw new DatabaseException("Não foi possível deletar o processo do Banco de Dados.");
		}finally {
			ConnectionFactory.fechaConnection(con, stmt);
		}
		
	}
	
	
	@Override
	public Process pegarPeloId(Long id) throws ValidationException, DatabaseException {
		String sql = "WHERE p.id="+id.toString();
		List<Process> lista = this.burcador(sql);
		if(lista.isEmpty() || lista ==null) {
			return null;
		}else {
			return lista.get(0);
		} 
		
	}

	@Override
	public boolean contem(Process processo) throws ValidationException, DatabaseException {		
		Process processoBuscado = this.pegarPeloId(processo.getId());
		
		return (processoBuscado!=null) ? true : false;
		
	}
	
	@Override
	public List<Process> pegarTodos() throws ValidationException, DatabaseException {
		String sql = "ORDER BY data_entrada DESC LIMIT 50";
		return this.burcador(sql);
		
	}

	
	private List<Process> burcador(String whereStament) throws ValidationException, DatabaseException {
		
		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		String query = "SELECT * "
						+ "FROM processos p "
						+ "INNER JOIN interessados i "
						+ "ON p.interessado_id=i.id "
						+ whereStament;
		
		try {
			con = ConnectionFactory.getConnection();
			
			stmt = con.prepareStatement(query);
			
			rs = stmt.executeQuery();
			
			List<Process> processos = new ArrayList<>();
			
			while(rs.next()) {
				
				//criando objeto Interessado
				Interested interessado = new Interested(
						rs.getLong("interessado_id"),
						rs.getString("nome"),
						rs.getString("cpf"),
						rs.getString("contato"));
				
				//criando o objeto Processo
				Process processo = new Process(
						rs.getLong("id"),
						rs.getBoolean("eh_oficio"),
						rs.getString("numero"),
						rs.getString("observacao"));
				processo.setInterested(interessado);
				
				//falta resolver unidade destino /orgao_saida, se vai ter ou não
				processo.setSubjectById(rs.getInt("assunto"));
				processo.setOriginEntityById(rs.getInt("orgao_origem"));
				processo.setSituationById(rs.getInt("situacao"));

				
				//Convertendo data entrada de java.sql.Date para LocalDateTime
				Date dataEntradaSql = rs.getDate("data_entrada");
				if(dataEntradaSql != null) {
					Timestamp stampEntradaSql = new Timestamp(dataEntradaSql.getTime());
					LocalDateTime dataEntrada = stampEntradaSql.toLocalDateTime();
					processo.setRegistrationDate(dataEntrada);
				}
				
				//Convertendo data Saida de java.sql.Date para LocalDateTime
				Date dataSaidaSql = rs.getDate("data_saida");
				if(dataSaidaSql != null) {
					Timestamp stampSaidaSql = new Timestamp(rs.getDate("data_saida").getTime());
					LocalDateTime dataSaida = stampSaidaSql.toLocalDateTime();
					processo.setDispatchDate(dataSaida);
				}
				
				processos.add(processo);
			
			}
			
			return processos;

		} catch (SQLException e) {
			throw new DatabaseException("Não foi possível buscar o processo no Banco.");
		}finally {
			ConnectionFactory.fechaConnection(con, stmt, rs);
		}
	}
	
	
	@Override
	public List<Process> buscarPorNumero(String numero) throws ValidationException, DatabaseException {
		String sql = "WHERE numero LIKE '"+numero+"'";
		return this.burcador(sql);
	}

	public List<Process> buscaComposta(String numero, String nome, String cpf, int orgaoId,
			int assuntoId, int situacaoId) throws ValidationException, DatabaseException {
		StringBuilder sql = new StringBuilder("WHERE ");
		final String AND = " AND ";
		
		if (numero != null && !numero.equalsIgnoreCase("")) {
			sql.append("numero LIKE '"+numero+"' AND ");
		}
		if (nome != null && !nome.equalsIgnoreCase("")) {
			sql.append("nome LIKE '%"+nome+"%' AND ");
		}
		if (cpf != null && !cpf.equalsIgnoreCase("")) {
			sql.append("cpf= '"+cpf+"' AND ");
		}
		if (orgaoId != 0) {
			sql.append("orgao_origem="+orgaoId+AND);
		}
		if (assuntoId != 0) {
			sql.append("assunto="+assuntoId+AND);
		}
		if (situacaoId != 0) {
			sql.append("situacao="+situacaoId);
		} else {
			sql.delete(sql.lastIndexOf(AND), sql.length());
		}
		return this.burcador(sql.toString());
	}
	
	//Methods to resolve statistic solutions

	@Override
	public Map<Integer, ArrayList<Integer>> getQuantityProcessPerMonthYearList() throws DatabaseException {
		
		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		String query = "SELECT COUNT(id), EXTRACT(year from data_entrada) as ano, EXTRACT(month from data_entrada) AS mes "
				+ "FROM processos GROUP BY ano, mes ORDER BY ano, mes";
		Map<Integer, ArrayList<Integer>> list = new HashMap<Integer, ArrayList<Integer>>();
		
		con = ConnectionFactory.getConnection();
		
		try {
			stmt = con.prepareStatement(query);
			rs = stmt.executeQuery();
			
			while(rs.next()) {
				ArrayList<Integer> line = new ArrayList<>();
                if (!list.containsKey(rs.getInt("ano")))
                {
                    line.add(rs.getInt("mes"));
                    line.add(rs.getInt("count(id)"));
                    list.put(rs.getInt("ano"), line);
                }else{
                    ArrayList<Integer> newLine = list.get(rs.getInt("ano"));
                    newLine.add(rs.getInt("mes"));
                    newLine.add(rs.getInt("count(id)"));
                }
			}
			
			return list;

		} catch (SQLException e) {
			throw new DatabaseException("Problema no SQL:"+e.getMessage());
		}
	}


	@Override
	public Map<Integer, Integer> getQuantityProcessPerSituation() throws DatabaseException {
		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		String query = "SELECT COUNT(id) AS qtde, situacao FROM processos GROUP BY situacao ORDER BY situacao";
		
		Map<Integer, Integer> list = new HashMap<>();
		
		con = ConnectionFactory.getConnection();
		
		try {
			stmt = con.prepareStatement(query);
			rs = stmt.executeQuery();
			
			while(rs.next()) {
				Integer situation, quantity;
				situation = rs.getInt("situacao");
				quantity = rs.getInt("qtde");
				
				list.put(situation, quantity);
			}
			
			return list;

		} catch (SQLException e) {
			throw new DatabaseException("Problema no SQL:"+e.getMessage());
		}
	}
	


}
