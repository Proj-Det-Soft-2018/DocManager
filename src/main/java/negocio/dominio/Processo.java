/**
 * 
 */
package negocio.dominio;

import java.time.LocalDateTime;

import apresentacao.Documento;
import negocio.servico.ProcessoServico;

/**
 * @author lets
 *
 */
public class Processo implements Documento{
	private static int contador = 0;
	private int processoId;
	private boolean tipoOficio;
	private String numero;
	private Interessado interessado;
	private Assunto assunto;
	private Orgao unidadeOrigem;
	private LocalDateTime dataEntrada; //Hora registro do processo no banco
	
	private Situacao situacaoAtual;
	
	private String observacao;
	
	private Orgao unidadeDestino; //para onde o processo é dirigido quando concluido	
	private LocalDateTime dataSaida; //Hora que altera e grava situação para concluido
	
	private ProcessoServico banco;
	
	/**
	 * @param numero
	 * @param interessado
	 * @param assunto
	 * @param unidadeOrigem
	 * @param situacaoAtual
	 */
	public Processo(
		boolean tipoOficio,
		String numero,
		Interessado interessado,
		Assunto assunto,
		Orgao unidadeOrigem,
		Situacao situacaoAtual)
	{
		super();
		this.processoId = this.gerarProcessoId();
		this.tipoOficio = tipoOficio;
		this.numero = numero;
		this.interessado = interessado;
		this.assunto = assunto;
		this.unidadeOrigem = unidadeOrigem;
		this.situacaoAtual = situacaoAtual;
		this.banco = new ProcessoServico();
	}
	
	public Processo() {
		
	}

	/**
	 * @return the id
	 */
	public int getProcessoId() {
		return processoId;
	}
	
	private int gerarProcessoId() {
		return contador++;
	}

	/**
	 * @return the tipoOficio
	 */
	public boolean isTipoOficio() {
		return tipoOficio;
	}

	/**
	 * @param tipoOficio the tipoOficio to set
	 */
	public void setTipoOficio(boolean tipoOficio) {
		this.tipoOficio = tipoOficio;
	}

	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	public Interessado getInteressado() {
		return interessado;
	}

	public void setInteressado(Interessado interessado) {
		this.interessado = interessado;
	}

	/**
	 * @return the assunto
	 */
	public Assunto getAssunto() {
		return assunto;
	}

	/**
	 * @param assunto the assunto to set
	 */
	public void setAssunto(Assunto assunto) {
		this.assunto = assunto;
	}

	public Orgao getUnidadeOrigem() {
		return unidadeOrigem;
	}

	public void setUnidadeOrigem(Orgao unidadeOrigem) {
		this.unidadeOrigem = unidadeOrigem;
	}

	public LocalDateTime getDataEntrada() {
		return dataEntrada;
	}

	public void setDataEntrada(LocalDateTime dataEntrada) {
		this.dataEntrada = dataEntrada;
	}

	public Situacao getSituacaoAtual() {
		return situacaoAtual;
	}

	public void setSituacaoAtual(Situacao situacaoAtual) {
		this.situacaoAtual = situacaoAtual;
	}

	public String getObservacao() {
		return observacao;
	}

	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}

	public Orgao getUnidadeDestino() {
		return unidadeDestino;
	}

	public void setUnidadeDestino(Orgao unidadeDestino) {
		this.unidadeDestino = unidadeDestino;
	}

	public LocalDateTime getDataSaida() {
		return dataSaida;
	}

	public void setDataSaida(LocalDateTime dataSaida) {
		this.dataSaida = dataSaida;
	}
	
	
	/**
	 * @return the banco
	 */
	public ProcessoServico getBanco() {
		return banco;
	}

	/**
	 * @param banco the banco to set
	 */
	public void setBanco(ProcessoServico banco) {
		this.banco = banco;
	}
	
	public Processo selecionarPorId(String numProcesso) {
		return banco.encontrarPorId(numProcesso);
			
	}

	public void validar() throws RuntimeException{
		if(this.numero == null){
			throw new RuntimeException();
		}
	}

	public boolean ehOficio() {
		return this.isTipoOficio();
	}

	public String getNomeInteressado() {
		
		return this.getInteressado().getNome();
	}
	
	public String getNumDocumento() {
		return this.getNumero();
	}
	
	public String getCpfInteressado() {
		return this.getInteressado().getCpf();
	}

	public String getContatoInteressado() {
		return this.getInteressado().getContato1();
	}

	public int getOrgaoOrigemId() {
		// TODO Auto-generated method stub
		return 0;
	}

	public int getAssuntoId() {
		// TODO Auto-generated method stub
		return 0;
	}

	public int getSituacaoId() {
		return this.situacaoAtual.ordinal();
	}
	
	


	
}
