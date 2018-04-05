/**
 * 
 */
package persistencia;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import negocio.dominio.Assunto;
import negocio.dominio.Interessado;
import negocio.dominio.Orgao;
import negocio.dominio.Processo;
import negocio.dominio.Situacao;
import negocio.servico.GenericoDao;

/**
 * @author clah
 * @since 01/04/2018
 */
public class ProcessoDaoMySql implements GenericoDao<Processo>{

	@Override
	public void salvar(Processo bean) {
		
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
			
			stmt.setBoolean(1, bean.isTipoOficio());
			stmt.setString(2, bean.getNumero());
			stmt.setLong(3, bean.getInteressado().getId());
			stmt.setInt(4, bean.getAssunto().ordinal());
			stmt.setInt(5, bean.getSituacao().ordinal());
			stmt.setInt(6, bean.getUnidadeOrigem().ordinal());
			stmt.setString(7, bean.getObservacao());
			
			//Definindo data de entrada no banco de dados
			
			bean.setDataEntrada(LocalDateTime.now());
			Timestamp stamp = Timestamp.valueOf(bean.getDataEntrada());
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
	public void atualizar(Processo bean) {
		
		String sql = "UPDATE processos SET "
					+ "numero=?, interessado_id=?, assunto=?,"
					+ "situacao=?, orgao_origem=?, observacao=?,"
					+ " data_saida=?"
					+ " WHERE id=?";
		
		Connection con = null;
		PreparedStatement stmt=null;
		
		try {
			con = ConnectionFactory.getConnection();
			stmt = con.prepareStatement(sql);
			stmt.setString(1, bean.getNumero());
			stmt.setLong(2, bean.getInteressado().getId());
			stmt.setInt(3, bean.getAssunto().ordinal());
			stmt.setInt(4, bean.getSituacao().ordinal());
			stmt.setInt(5, bean.getUnidadeOrigem().ordinal());
			stmt.setString(6, bean.getObservacao());
			
			//LocalDateTime to java.sql.Date
			if(bean.getDataSaida()!=null) {
				Timestamp stamp = Timestamp.valueOf(bean.getDataSaida());
				Date dataSaida = new Date (stamp.getTime());
				stmt.setDate(7,dataSaida);
			}
			
			stmt.setDate(7, null);
				
			//setando id do processo a ser modificado
			stmt.setLong(8, bean.getId());
			
			
			stmt.executeUpdate();
			
			
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}finally {
			ConnectionFactory.fechaConnection(con, stmt);
		}
		
	}

	@Override
	public void deletar(Processo bean) {
		
		Connection con = null;
		PreparedStatement stmt=null;
		
		try {
			con = ConnectionFactory.getConnection();
			stmt = con.prepareStatement("DELETE FROM processos WHERE id=?");
	        stmt.setLong(1, bean.getId());
	        stmt.executeUpdate();
			
			
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}finally {
			ConnectionFactory.fechaConnection(con, stmt);
		}
		
	}
	
	//TODO verificar essa exceção generica que os metodos getbyid das classes Orgao, Situacao e Assunto lança
	@Override
	public Processo getById(String id) {
		
		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		try {
			con = ConnectionFactory.getConnection();
			
			stmt = con.prepareStatement("SELECT * FROM processos p INNER JOIN interessados i ON p.interessado_id=i.id WHERE p.id=?");
			stmt.setLong(1, Long.parseLong(id));
			
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
				
					
			}else {
				System.out.println("Nenhum processo encontrado com esse id");
			}
			
			return processo;
			

		} catch (SQLException e) {
			throw new RuntimeException("Erro no getById Processo: "+ e);
		}finally {
			ConnectionFactory.fechaConnection(con, stmt, rs);
		}
	}

	@Override
	public boolean contem(Processo bean) {
		String id = bean.getId().toString();
		Processo processo = this.getById(id);
		if(processo!=null) {
			return true;
		}else {
			return false;
		}
	}

	@Override
	public List<Processo> getAll() {
		
		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		try {
			con = ConnectionFactory.getConnection();
			
			stmt = con.prepareStatement("SELECT * "
										+"FROM processos p "
										+ "INNER JOIN interessados i "
										+ "ON p.interessado_id=i.id ");					
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
				
				processos.add(processo);
			
			}
			
			return processos;
			

		} catch (SQLException e) {
			throw new RuntimeException("Erro no getAll Processo: "+ e);
		}finally {
			ConnectionFactory.fechaConnection(con, stmt, rs);
		}
	}

}
