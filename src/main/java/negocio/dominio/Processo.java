/**
 * 
 */
package negocio.dominio;

import java.time.LocalDateTime;

import negocio.servico.ProcessoServico;

/**
 * @author lets
 *
 */
public class Processo implements Documento{
	
	private boolean tipoOficio;
	private String numero;
	private Interessado interessado;
	private String nomeInteressado;
	private String assunto;
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
			String numero,
			Interessado interessado,
			String assunto,
			Orgao unidadeOrigem,
			Situacao situacaoAtual)
	{
	public Processo(boolean tipoOficio, String numero, Interessado interessado, String assunto, Orgao unidadeOrigem, Situacao situacaoAtual) {
		super();
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
	
	public String getNomeInteressado() {
		return nomeInteressado;
	}

	public void setNomeInteressado(String nomeInteressado) {
		this.nomeInteressado = nomeInteressado;
	}

	public Interessado getInteressado() {
		return interessado;
	}

	public void setInteressado(Interessado interessado) {
		this.interessado = interessado;
	}

	public String getAssunto() {
		return assunto;
	}

	public void setAssunto(String assunto) {
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

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((numero == null) ? 0 : numero.hashCode());
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Processo other = (Processo) obj;
		if (numero == null) {
			if (other.numero != null)
				return false;
		} else if (!numero.equals(other.numero))
			return false;
		return true;
	}
	

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Processo [numero=" + numero + ", interessado=" + interessado + ", nomeInteressado=" + nomeInteressado
				+ ", assunto=" + assunto + ", unidadeOrigem=" + unidadeOrigem + ", dataEntrada=" + dataEntrada
				+ ", situacaoAtual=" + situacaoAtual + ", observacao=" + observacao + ", unidadeDestino="
				+ unidadeDestino + ", dataSaida=" + dataSaida + "]";
	}

	public void criar() {
		this.validar();
		//inicializar data de entrada
		this.setDataEntrada(LocalDateTime.now());
		this.banco.salvarProcesso(this);
			
	}
	
	public Processo selecionarPorId(int hashCode) {
		return banco.encontrarPorId(hashCode);
			
	}

	private boolean validar(){
		if(this.numero == null){
			return false;
		}
		return true;
	}

	public boolean ehOficio() {
		// TODO Auto-generated method stub
		return this.isTipoOficio();
	}

	public String getNumDocumento() {
		// TODO Auto-generated method stub
		return this.getNumDocumento();
	}

	public String getCpfInteressado() {
		// TODO Auto-generated method stub
		return this.getInteressado().getCpf();
	}

	public String getContatoInteressado() {
		// TODO Auto-generated method stub
		return this.getInteressado().getContato1();
	}

	public int getOrgaoOrigemId() {
		// TODO Auto-generated method stub
		return 0;
	}

	public int getTipoDocumentoId() {
		// TODO Auto-generated method stub
		return 0;
	}

	public int getSituacaoId() {
		// TODO Auto-generated method stub
		return 0;
	}
	
	
	
	
	
	
}
