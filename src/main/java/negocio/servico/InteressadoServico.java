package negocio.servico;

import java.util.List;

import org.apache.log4j.Logger;

import negocio.dominio.Interessado;
import persistencia.InteressadoDaoHash;
/**
 * 
 * @author Allan
 *
 */
public class InteressadoServico {
	GenericoDao<Interessado> interessadoDao = new InteressadoDaoHash();
	
	private static Logger logger = Logger.getLogger(InteressadoServico.class);
	
	public void criarInteressado(Interessado interessado) {
		try {
			interessado.validar();	
		}
		catch (RuntimeException e) {
			logger.error("NAO VALIDOU INTERESSADO");
		}
		this.salvarInteressado(interessado);
	}
	//metodo privado pois só é acesssado depois que o objeto é criado
	private void salvarInteressado(Interessado interessado) {
		interessadoDao.salvar(interessado);
	}
	
	public void atualizarInteressado(Interessado interessado) {
		try {
			interessado.validar();
		}
		catch (RuntimeException e) {
			logger.error("ATUALIZACAO NAO VALIDADA");
		}
		interessadoDao.atualizar(interessado);
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
