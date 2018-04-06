package negocio.servico;

import java.util.List;

import org.apache.log4j.Logger;

import negocio.dominio.Interessado;
import persistencia.InteressadoDaoMySql;
/**
 * 
 * @author Allan
 *
 */
public class InteressadoServico {
	private InteressadoDao interessadoDao;
	
	private static Logger logger = Logger.getLogger(InteressadoServico.class);
	
	
	// Singleton
	private static final InteressadoServico instance = new InteressadoServico();
	
	private InteressadoServico() {
		interessadoDao = new InteressadoDaoMySql();
	}
	
	public static InteressadoServico getInstance() {
		return instance;
	}
	
	
	public void criarInteressado(Interessado interessado) {
		
		//Verifica se interessado ja esta no banco atraves do cpf
		Interessado interessado_bd = this.encontrarPorCpf(interessado.getCpf());
		
		if(interessado_bd == null) {
			try {
				interessado.validar();
				this.salvarInteressado(interessado);
			}
			catch (RuntimeException e) {
				logger.error(e.getMessage(), e);
				//TODO elaborar
			}
		}
		
		
		
	}
	//metodo privado pois só é acesssado depois que o objeto é criado
	private void salvarInteressado(Interessado interessado) {
		interessadoDao.salvar(interessado);
	}
	
	public void atualizarInteressado(Interessado interessado) {
		try {
			interessado.validar();
			interessadoDao.atualizar(interessado);
		}
		catch (RuntimeException e) {
			logger.error(e.getMessage(), e);
			//TODO elaborar
		}
	}
	
	public void deletarInteressado(Interessado interessado) {
		interessadoDao.deletar(interessado);
	}
	
	public Interessado encontrarPorId(Long id) {
		return interessadoDao.pegarPeloId(id);
	}
	
	public Interessado encontrarPorCpf(String cpf) {
		return interessadoDao.pegarPeloCpf(cpf);
	}
	
	public boolean contem(Interessado interessado) {
		return interessadoDao.contem(interessado);
	}
	
	public List<Interessado> getAll(){
		return interessadoDao.pegarTodos();
	}
}
