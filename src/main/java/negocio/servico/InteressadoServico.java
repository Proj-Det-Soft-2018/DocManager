package negocio.servico;

import java.util.List;

import org.apache.log4j.Logger;

import negocio.GenericoDao;
import negocio.dominio.Interessado;
import persistencia.InteressadoDao;
/**
 * 
 * @author Allan
 *
 */
public class InteressadoServico {
	
	private static Logger logger = Logger.getLogger(InteressadoServico.class);
	private static GenericoDao<Interessado> interessadoDao = new InteressadoDao();
	
	public void criarInteressado(Interessado interessado) {
		try {
			interessado.validar();	
		}
		catch (RuntimeException e) {
			logger.error("NAO VALIDOU INTERESSADO");
		}
		finally {
			this.salvarInteressado(interessado);
		}
	}
	
	public void salvarInteressado(Interessado interessado) {
		interessadoDao.salvar(interessado);
	}
	
	public void atualizarInteressado(Interessado interessado) {
		try {
			interessado.validar();
		}
		catch (RuntimeException e) {
			logger.error("ATUALIZACAO NAO VALIDADA");
		}
		finally {
			interessadoDao.atualizar(interessado);
		}
		
	}
	
	public void deletarInteressado(Interessado interessado) {
		interessadoDao.deletar(interessado);
	}
	
	public Interessado encontrarPorId(String cpf) {
		return interessadoDao.getById(cpf);
	}
	
	public boolean contem(Interessado interessado) {
		return interessadoDao.contem(interessado);
	}
	
	public List<Interessado> getAll(){
		return interessadoDao.getAll();
	}
}
