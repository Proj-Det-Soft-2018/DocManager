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
import java.util.List;

import business.model.Interested;
import business.model.Process;

/**
 * @author clah
 * @since 01/04/2018
 */
public class ProcessoDaoMySql implements ProcessoDao{
	
	@Override
	public void salvar(Process novoProcesso) {
		
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
			
			stmt.setBoolean(1, novoProcesso.isTipoOficio());
			stmt.setString(2, novoProcesso.getNumero());
			stmt.setLong(3, novoProcesso.getInteressado().getId());
			stmt.setInt(4, novoProcesso.getAssunto().ordinal());
			stmt.setInt(5, novoProcesso.getSituacao().ordinal());
			stmt.setInt(6, novoProcesso.getUnidadeOrigem().ordinal());
			stmt.setString(7, novoProcesso.getObservacao());
			
			//Definindo data de entrada no banco de dados
			LocalDateTime data = LocalDateTime.now();
			novoProcesso.setDataEntrada(data);
			
			Timestamp stamp = Timestamp.valueOf(data);
			Date dataEntrada = new Date (stamp.getTime());
			
			stmt.setDate(8,dataEntrada);
			stmt.executeUpdate();
			
			
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}finally {
			ConnectionFactory.fechaConnection(con, stmt);
		}
		
		
	}
	
	
	@Override
	public void atualizar(Process processoModificado) {
		
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
			stmt.setString(1, processoModificado.getNumero());
			stmt.setLong(2, processoModificado.getInteressado().getId());
			stmt.setInt(3, processoModificado.getAssunto().ordinal());
			stmt.setInt(4, processoModificado.getSituacao().ordinal());
			stmt.setInt(5, processoModificado.getUnidadeOrigem().ordinal());
			stmt.setString(6, processoModificado.getObservacao());
			stmt.setBoolean(7, processoModificado.isTipoOficio());
			
			//setando id do processo a ser modificado
			stmt.setLong(8, processoModificado.getId());
			
			
			stmt.executeUpdate();
			
			
		} catch (SQLException e) {
			//TODO resolver
			throw new RuntimeException(e);
		}finally {
			ConnectionFactory.fechaConnection(con, stmt);
		}
		
	}

		
	@Override
	public void deletar(Process processo) {
		
		Connection con = null;
		PreparedStatement stmt=null;
		
		try {
			con = ConnectionFactory.getConnection();
			stmt = con.prepareStatement("DELETE FROM processos WHERE id=?");
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
	public Process pegarPeloId(Long id) {
		String sql = "WHERE p.id="+id.toString();
		List<Process> lista = this.burcador(sql);
		if(lista.isEmpty() || lista ==null) {
			return null;
		}else {
			return lista.get(0);
		} 
		
	}

	@Override
	public boolean contem(Process processo) {		
		Process processoBuscado = this.pegarPeloId(processo.getId());
		
		return (processoBuscado!=null) ? true : false;
		
	}
	
	@Override
	public List<Process> pegarTodos() {
		String sql = "ORDER BY data_entrada DESC LIMIT 50";
		return this.burcador(sql);
		
	}

	
	private List<Process> burcador(String whereStament) {
		
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
				processo.setInteressado(interessado);
				
				//falta resolver unidade destino /orgao_saida, se vai ter ou não
				processo.setAssuntoById(rs.getInt("assunto"));
				processo.setUnidadeOrigemById(rs.getInt("orgao_origem"));
				processo.setSituacaoById(rs.getInt("situacao"));

				
				//Convertendo data entrada de java.sql.Date para LocalDateTime
				Date dataEntradaSql = rs.getDate("data_entrada");
				if(dataEntradaSql != null) {
					Timestamp stampEntradaSql = new Timestamp(dataEntradaSql.getTime());
					LocalDateTime dataEntrada = stampEntradaSql.toLocalDateTime();
					processo.setDataEntrada(dataEntrada);
				}
				
				//Convertendo data Saida de java.sql.Date para LocalDateTime
				Date dataSaidaSql = rs.getDate("data_saida");
				if(dataSaidaSql != null) {
					Timestamp stampSaidaSql = new Timestamp(rs.getDate("data_saida").getTime());
					LocalDateTime dataSaida = stampSaidaSql.toLocalDateTime();
					processo.setDataSaida(dataSaida);
				}
				
				processos.add(processo);
			
			}
			
			return processos;

		} catch (SQLException e) {
			///TODO resolver
			throw new RuntimeException("Erro no pegarPeloId Processo: "+ e);
		}finally {
			ConnectionFactory.fechaConnection(con, stmt, rs);
		}
	}
	
	
	@Override
	public List<Process> buscarPorNumero(String numero) {
		String sql = "WHERE numero LIKE '"+numero+"'";
		return this.burcador(sql);
	}

	@Override
	public List<Process> buscarPorNomeInteressado(String nome) {
		String sql = "WHERE nome LIKE '%"+nome+"%'";
		return this.burcador(sql);
	}


	@Override
	public List<Process> buscarPorCpfInteressado(String cpf) {
		String sql = "WHERE cpf= '"+cpf+"'";
		return this.burcador(sql);
	}
	
	@Override
	public List<Process> buscarPorSituacao(int situacaoId) {
		String sql = "WHERE situacao="+situacaoId;
		return this.burcador(sql);
	}
	
	public List<Process> buscarPorOrgao(int orgaoId) {
		String sql = "WHERE orgao_origem="+orgaoId;
		return this.burcador(sql);
	}
	
	public List<Process> buscarPorAssunto(int assuntoId) {
		String sql = "WHERE assunto="+assuntoId;
		return this.burcador(sql);
	}

	public List<Process> buscaComposta(String numero, String nome, String cpf, int orgaoId,
			int assuntoId, int situacaoId) {
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

}
