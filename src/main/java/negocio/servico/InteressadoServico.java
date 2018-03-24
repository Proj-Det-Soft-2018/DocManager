package negocio.servico;

import java.util.List;

import negocio.IDao;
import negocio.dominio.Interessado;
import persistencia.InteressadoDao;

public class InteressadoServico {
	IDao<Interessado> interessadoDao = new InteressadoDao();
	
	public void salvarInteressado(Interessado interessado) {
		interessadoDao.salvar(interessado);
	}
	
	public void atualizarInteressado(Interessado interessado) {
		interessadoDao.atualizar(interessado);
	}
	
	public void deletarInteressado(Interessado interessado) {
		interessadoDao.deletar(interessado);
	}
	
	public Interessado encontrarPorId(int id) {
		return interessadoDao.getById(id);
	}
	
	public boolean contem(Interessado interessado) {
		return interessadoDao.contem(interessado);
	}
	
	public List<Interessado> getAll(){
		return interessadoDao.getAll();
	}
}
