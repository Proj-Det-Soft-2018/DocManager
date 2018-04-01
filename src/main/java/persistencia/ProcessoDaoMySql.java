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
import java.util.List;

import javax.management.RuntimeErrorException;

import negocio.GenericoDao;
import negocio.dominio.Assunto;
import negocio.dominio.Interessado;
import negocio.dominio.Orgao;
import negocio.dominio.Processo;
import negocio.dominio.Situacao;

/**
 * @author clah
 * @since 01/04/2018
 */
public class ProcessoDaoMySql implements GenericoDao<Processo>{

	@Override
	public void salvar(Processo bean) {
		String sql = "INSERT INTO processos" +
                	"(eh_oficio,numero,interessado_id,assunto,situacao,orgao_origem,observacao,data_entrada)" +
                	" values (?,?,?,?,?,?,?,?)";
		
		Connection con = null;
		PreparedStatement stmt=null;
		try {
			con = ConnectionFactory.getConnection();
			stmt = con.prepareStatement(sql);
			
			stmt.setBoolean(1, bean.isTipoOficio());
			stmt.setString(2, bean.getNumero());
			stmt.setLong(3, bean.getInteressado().getId());
			stmt.setInt(4, bean.getAssuntoId());
			stmt.setInt(5, bean.getSituacaoId());
			stmt.setInt(6, bean.getOrgaoOrigemId());
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
		String sql = "UPDATE processos SET "+
					"numero=?, interessado_id=?, assunto=? , situacao=?, orgao_origem=?,observacao=?,data_saida=?"+
					" WHERE id=?";
		Connection con = null;
		PreparedStatement stmt=null;
		try {
			con = ConnectionFactory.getConnection();
			stmt = con.prepareStatement(sql);
			stmt.setString(1, bean.getNumero());
			stmt.setLong(2, bean.getInteressado().getId());
			stmt.setInt(3, bean.getAssuntoId());
			stmt.setInt(4, bean.getSituacaoId());
			stmt.setInt(5, bean.getOrgaoOrigemId());
			stmt.setString(6, bean.getObservacao());
			
			//LocalDateTime to java.sql.Date
			Timestamp stamp = Timestamp.valueOf(bean.getDataSaida());
			Date dataSaida = new Date (stamp.getTime());
			stmt.setDate(7,dataSaida);
			
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
				processo.setAssunto(Assunto.getAssuntoPorId(rs.getInt("assunto")));
				processo.setUnidadeOrigem(Orgao.getOrgaoPorId(rs.getInt("orgao_origem")));
				processo.setSituacaoAtual(Situacao.getSituacaoPorId(rs.getInt("situacao")));
				}catch(Exception e) {
					//O que fazer aqui?
				}
				interessado.setId(rs.getLong("interessado_id"));
				interessado.setNome(rs.getString("nome"));
				interessado.setCpf(rs.getString("cpf"));
				interessado.setContato(rs.getString("contato"));
				processo.setInteressado(interessado);
				
				//Convertendo java.sql.Date to LocalDateTime
				Timestamp stampE = new Timestamp(rs.getDate("data_entrada").getTime());
				LocalDateTime dataEntrada = stampE.toLocalDateTime();
				processo.setDataEntrada(dataEntrada);
				
				//Convertendo java.sql.Date to LocalDateTime
				Timestamp stampS = new Timestamp(rs.getDate("data_saida").getTime());
				LocalDateTime dataSaida = stampS.toLocalDateTime();
				processo.setDataSaida(dataSaida);				
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
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<Processo> getAll() {
		// TODO Auto-generated method stub
		return null;
	}

}
