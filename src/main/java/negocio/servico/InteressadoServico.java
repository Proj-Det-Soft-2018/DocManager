package negocio.servico;

import java.util.List;

import org.apache.log4j.Logger;

import negocio.dominio.Interessado;
import persistencia.InteressadoDao;
import persistencia.InteressadoDaoMySql;
/**
 * 
 * @author Allan
 *
 */
public class InteressadoServico extends Observavel {
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
		Interessado interessado_bd = this.burcarPeloCpf(interessado.getCpf());
		
		if(interessado_bd == null) {
			try {
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
		notificarTodos();
	}
	
	public void atualizarInteressado(Interessado interessado) {
		try {
			interessadoDao.atualizar(interessado);
			notificarTodos();
		}
		catch (RuntimeException e) {
			logger.error(e.getMessage(), e);
			//TODO elaborar
		}
	}
	
	public void deletarInteressado(Interessado interessado) {
		interessadoDao.deletar(interessado);
		notificarTodos();
	}
	
	public Interessado encontrarPorId(Long id) {
		return interessadoDao.pegarPeloId(id);
	}
	
	public boolean contem(Interessado interessado) {
		return interessadoDao.contem(interessado);
	}
	
	public List<Interessado> pegarTodosInteressados(){
		return interessadoDao.pegarTodos();
	}
	
	public Interessado burcarPeloCpf(String cpf){
		if(cpf == null || cpf.length() != 11) {
			throw new ValidationException("CPF INVÁLIDO!", "Busca-CPF", "O CPF buscado está incompleto!");
		}
		return interessadoDao.pegarPeloCpf(cpf);
	} 
}
