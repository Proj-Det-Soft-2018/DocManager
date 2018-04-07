/**
 * 
 */
package persistencia;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import negocio.dominio.Interessado;
import negocio.dominio.Processo;
import negocio.servico.ProcessoDao;

/**
 * @author clah
 * @since 01/04/2018
 */
public class ProcessoDaoMySql implements ProcessoDao{
	
	@Override
	public void salvar(Processo novoProcesso) {
		
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
			
			novoProcesso.setDataEntrada(LocalDateTime.now());
			Timestamp stamp = Timestamp.valueOf(novoProcesso.getDataEntrada());
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
	public void atualizar(Processo processoModificado) {
		
		String sql = "UPDATE processos SET "
					+ "numero=?, interessado_id=?, assunto=?,"
					+ "situacao=?, orgao_origem=?, observacao=?,"
					+ " data_saida=?, eh_oficio=?"
					+ " WHERE id=?";
		
		Connection con = null;
		PreparedStatement stmt=null;
		
		try {
			
			con = ConnectionFactory.getConnection();
			stmt = con.prepareStatement(sql);
			stmt.setString(1, processoModificado.getNumero());
			stmt.setLong(2, processoModificado.getInteressado().getId());
			stmt.setInt(3, processoModificado.getAssunto().ordinal());
			stmt.setInt(4, processoModificado.getSituacao().ordinal());
			stmt.setInt(5, processoModificado.getUnidadeOrigem().ordinal());
			stmt.setString(6, processoModificado.getObservacao());
			stmt.setBoolean(7, processoModificado.isTipoOficio());
			
			
			//LocalDateTime to java.sql.Date
			LocalDateTime dataSaida = processoModificado.getDataSaida();
			
			if(dataSaida!=null) {
				Timestamp stamp = Timestamp.valueOf(dataSaida);
				Date dataSaidaSql = new Date (stamp.getTime());
				stmt.setDate(8,dataSaidaSql);
			}
			
			stmt.setDate(8, null);
				
			//setando id do processo a ser modificado
			stmt.setLong(9, processoModificado.getId());
			
			
			stmt.executeUpdate();
			
			
		} catch (SQLException e) {
			//TODO resolver
			throw new RuntimeException(e);
		}finally {
			ConnectionFactory.fechaConnection(con, stmt);
		}
		
	}

		
	@Override
	public void deletar(Processo processo) {
		
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
	
	
	//TODO verificar essa exceção generica que os metodos getbyid das classes Orgao, Situacao e Assunto lança
	@Override
	public Processo pegarPeloId(Long id) {
		
		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		try {
			con = ConnectionFactory.getConnection();
			
			stmt = con.prepareStatement("SELECT * FROM processos p INNER JOIN interessados i ON p.interessado_id=i.id WHERE p.id=?");
			stmt.setLong(1, id);
			
			rs = stmt.executeQuery();
			
			Processo processo = new Processo();
			Interessado interessado = new Interessado();
			
			if(rs.next()) {
				//criando o objeto Interessado
				
				processo.setId(rs.getLong("id"));
				processo.setTipoOficio(rs.getBoolean("eh_oficio"));
				processo.setNumero(rs.getString("numero"));
				processo.setObservacao(rs.getString("observacao"));
				
				//falta resolver unidade destino /orgao_saida, se vai ter ou não
				
				try {
				processo.setAssuntoById(rs.getInt("assunto"));
				processo.setUnidadeOrigemById(rs.getInt("orgao_origem"));
				processo.setSituacaoById(rs.getInt("situacao"));
				}catch(Exception e) {
					//TODO O que fazer aqui?
				}
				interessado.setId(rs.getLong("interessado_id"));
				interessado.setNome(rs.getString("nome"));
				interessado.setCpf(rs.getString("cpf"));
				interessado.setContato(rs.getString("contato"));
				processo.setInteressado(interessado);
				
				//Convertendo java.sql.Date to LocalDateTime
				Date dataE = rs.getDate("data_entrada");
				if(dataE != null) {
					Timestamp stampE = new Timestamp(dataE.getTime());
					LocalDateTime dataEntrada = stampE.toLocalDateTime();
					processo.setDataEntrada(dataEntrada);
				}else {
					processo.setDataEntrada(null);
				}
					
				
				//Convertendo java.sql.Date to LocalDateTime
				Date dataS = rs.getDate("data_saida");
				if(dataS != null) {
					Timestamp stampS = new Timestamp(rs.getDate("data_saida").getTime());
					LocalDateTime dataSaida = stampS.toLocalDateTime();
					processo.setDataSaida(dataSaida);
				}else {
					processo.setDataSaida(null);
				}
				
				return processo;
				
			}else {
				return null;
			}
			
			
			

		} catch (SQLException e) {
			///TODO resolver
			throw new RuntimeException("Erro no pegarPeloId Processo: "+ e);
		}finally {
			ConnectionFactory.fechaConnection(con, stmt, rs);
		}
	}

	@Override
	public boolean contem(Processo processo) {		
		Processo processoBuscado = this.pegarPeloId(processo.getId());
		
		return (processoBuscado!=null) ? true : false;
		
	}
	
	@Override
	public List<Processo> pegarTodos() {
		
		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		try {
			con = ConnectionFactory.getConnection();
			
			stmt = con.prepareStatement("SELECT * "
										+"FROM processos p "
										+ "INNER JOIN interessados i "
										+ "ON p.interessado_id=i.id "
										+ "ORDER BY data_entrada DESC"
										+ "LIMIT 50");					
			rs = stmt.executeQuery();
			
			
			List<Processo> processos = new ArrayList<Processo>();
			
			while(rs.next()) {
				
				//criando o objeto Interessado
				Processo processo = new Processo();
				processo.setId(rs.getLong("id"));
				processo.setTipoOficio(rs.getBoolean("eh_oficio"));
				processo.setNumero(rs.getString("numero"));
				processo.setObservacao(rs.getString("observacao"));
				
				//falta resolver unidade destino /orgao_saida, se vai ter ou não
				try {
					processo.setAssuntoById(rs.getInt("assunto"));
					processo.setUnidadeOrigemById(rs.getInt("orgao_origem"));
					processo.setSituacaoById(rs.getInt("situacao"));
				}
				catch (RuntimeException e) {
					// TODO: handle exception
				}
				
				//criando objeto interessado
				Interessado interessado = new Interessado();
				
				interessado.setId(rs.getLong("interessado_id"));
				interessado.setNome(rs.getString("nome"));
				interessado.setCpf(rs.getString("cpf"));
				interessado.setContato(rs.getString("contato"));
				processo.setInteressado(interessado);
				
				//Convertendo data entrada de java.sql.Date para LocalDateTime
				Date dataE = rs.getDate("data_entrada");
				if(dataE != null) {
					Timestamp stampE = new Timestamp(dataE.getTime());
					LocalDateTime dataEntrada = stampE.toLocalDateTime();
					processo.setDataEntrada(dataEntrada);
				}else {
					processo.setDataEntrada(null);
				}
					
				
				//Convertendo data Saida de java.sql.Date para LocalDateTime
				Date dataS = rs.getDate("data_saida");
				if(dataS != null) {
					Timestamp stampS = new Timestamp(rs.getDate("data_saida").getTime());
					LocalDateTime dataSaida = stampS.toLocalDateTime();
					processo.setDataSaida(dataSaida);
				}else {
					processo.setDataSaida(null);
				}
				
				processos.add(processo);
			
			}
			
			return processos;
			

		} catch (SQLException e) {
			//TODO resolver
			throw new RuntimeException("Erro no pegarTodos Processo: "+ e);
		}finally {
			ConnectionFactory.fechaConnection(con, stmt, rs);
		}
	}

	
	public List<Processo> burcador(String whereStament) {
		
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
			
			List<Processo> processos = new ArrayList<>();
			
			while(rs.next()) {
				
				//criando objeto Interessado
				Interessado interessado = new Interessado();	
				interessado.setId(rs.getLong("interessado_id"));
				interessado.setNome(rs.getString("nome"));
				interessado.setCpf(rs.getString("cpf"));
				interessado.setContato(rs.getString("contato"));
				
				//criando o objeto Processo
				Processo processo = new Processo();
				processo.setInteressado(interessado);
				processo.setId(rs.getLong("id"));
				processo.setTipoOficio(rs.getBoolean("eh_oficio"));
				processo.setNumero(rs.getString("numero"));
				processo.setObservacao(rs.getString("observacao"));
				
				//falta resolver unidade destino /orgao_saida, se vai ter ou não
				processo.setAssuntoById(rs.getInt("assunto"));
				processo.setUnidadeOrigemById(rs.getInt("orgao_origem"));
				processo.setSituacaoById(rs.getInt("situacao"));

				
				//Convertendo data entrada de java.sql.Date para LocalDateTime
				Date dataE = rs.getDate("data_entrada");
				if(dataE != null) {
					Timestamp stampE = new Timestamp(dataE.getTime());
					LocalDateTime dataEntrada = stampE.toLocalDateTime();
					processo.setDataEntrada(dataEntrada);
				}else {
					processo.setDataEntrada(null);
				}
					
				
				//Convertendo data Saida de java.sql.Date para LocalDateTime
				Date dataS = rs.getDate("data_saida");
				if(dataS != null) {
					Timestamp stampS = new Timestamp(rs.getDate("data_saida").getTime());
					LocalDateTime dataSaida = stampS.toLocalDateTime();
					processo.setDataSaida(dataSaida);
				}else {
					processo.setDataSaida(null);
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
	public List<Processo> buscarPorNumero(String numero) {
		String sql = "WHERE numero LIKE "+numero;
		return this.burcador(sql);
	}

	@Override
	public List<Processo> buscarPorNomeInteressado(String nome) {
		String sql = "WHERE nome LIKE '%"+nome+"%'";
		return this.burcador(sql);
	}


	@Override
	public List<Processo> buscarPorCpfInteressado(String cpf) {
		String sql = "WHERE cpf="+cpf;
		return this.burcador(sql);
	}
	
	@Override
	public List<Processo> buscarPorSituacao(int situacaoId) {
		String sql = "WHERE situacao="+situacaoId;
		return this.burcador(sql);
	}

	

}
