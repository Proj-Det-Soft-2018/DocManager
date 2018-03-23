/**
 * 
 */
package br.com.docmanager.model.domain;

import java.time.LocalDateTime;

import br.com.docmanager.model.dao.IDao;
import br.com.docmanager.model.dao.HashProcessoDao;

/**
 * @author lets
 *
 */
public class Processo {
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
	
	/**
	 * @param numero
	 * @param interessado
	 * @param assunto
	 * @param unidadeOrigem
	 * @param situacaoAtual
	 */
	public Processo(String numero, Interessado interessado, String assunto, Orgao unidadeOrigem, Situacao situacaoAtual) {
		super();
		this.numero = numero;
		this.interessado = interessado;
		this.assunto = assunto;
		this.unidadeOrigem = unidadeOrigem;
		this.situacaoAtual = situacaoAtual;
	}
	
	public Processo() {
		
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
	
	public void criar(Processo processo) {
		processo.validate();
		IDao banco = new HashProcessoDao();
		banco.salvar(processo);
		
		
		
	}

	private void validate(){
		// TODO Fazer validações de negócios
		
	}
	
	
	
	
	
	
}
