import static org.junit.Assert.*;

import javax.print.attribute.HashAttributeSet;

import org.junit.Test;

import junit.framework.TestCase;
import junit.framework.TestSuite;
import negocio.GenericoDao;
import negocio.dominio.Interessado;
import negocio.dominio.Orgao;
import negocio.dominio.Processo;
import negocio.dominio.Situacao;
import negocio.facade.FachadaGerenciadorProcesso;
import negocio.servico.ProcessoServico;
import persistencia.HashProcessoDao;

/**
 * 
 */

/**
 * @author clah
 * @since 23/03/2018
 *
 */
public class GerenciadorFacadeTeste extends TestCase{

    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public GerenciadorFacadeTeste( String testName )
    {
        super( testName );
    }



    /**
     * Testa se o processo foi inserido no BD/HashMap
     */
    public void testcadastrarSucesso()
    {
    	String numeroProcesso = "123";
    	String assuntoProcesso = "isenção";
    	String unidadeOrigem = "UFRN";
    	String situacao = "Analise";
    	String nomeInteressado = "Clara";
    	String cpfInteressado = "05544422233";
    	String contato1 = "99992222";
    	String contato2 = "88889999";
    	
    	
    	FachadaGerenciadorProcesso objfacade = new FachadaGerenciadorProcesso();
    	objfacade.criarDocumento(false, numeroProcesso, nomeInteressado, cpfInteressado, contato1, 0, 0, 0, null);
    	
    	ProcessoServico dao = new ProcessoServico();
    
        
}

}
