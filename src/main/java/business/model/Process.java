/**
 * 
 */
package business.model;

import java.time.LocalDateTime;

import business.service.ValidationException;

/**
 * @author lets
 *
 */
public class Process {
	private Long id;
	private boolean tipoOficio;
	private String numero;
	private Interested interessado;
	private Subject assunto;
	private Organization unidadeOrigem;
	private Situation situacao;
	private String observacao;
	private Organization unidadeDestino; //para onde o processo é dirigido quando concluido
	private LocalDateTime dataEntrada; //Hora registro do processo no banco
	private LocalDateTime dataSaida; //Hora que altera e grava situação para concluido

	public Process() {
		
	}
	
	public Process(Long id, boolean tipoOficio, String numero, String observacao) {
		this.id = id;
		this.tipoOficio = tipoOficio;
		this.numero = numero;
		this.observacao = observacao;
	}

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}
	
	public void setId(Long processoId) {
		this.id = processoId;
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
	
	public String getTipo () {
		return this.tipoOficio? "Ofício" : "Processo";
	}

	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		if(this.tipoOficio == true) {
			if(numero.length() < 8) {
				throw new ValidationException("Número invalido!", "Numero", "O número digitado é inválido.");
			}
			else {
				if(!numero.substring(0, 7).matches("[0-9]+")) {
					throw new ValidationException("Número invalido!", "Numero", "O número digitado é inválido.");
				}
			}
		}
		else {
			if(!(numero.length() == 17) || !(numero.matches("[0-9]+"))) {
				throw new ValidationException("Número invalido!", "Numero", "O número digitado é inválido.");
			}
		}
		this.numero = numero;
	}

	public Interested getInteressado() {
		return interessado;
	}

	public void setInteressado(Interested interessado) {
		this.interessado = interessado;
	}

	/**
	 * @return the assunto
	 */
	public Subject getAssunto() {
		return assunto;
	}

	/**
	public void setAssunto(Assunto assunto) {
		this.assunto = assunto;
	}
	*/
	
	public void setAssuntoById(int idAssunto) {
		if(idAssunto == 0) {
			throw new ValidationException("Você não selecionou um assunto.", "Assunto", "Campo assunto é obrigatório.");
		}
		this.assunto = Subject.getAssuntoPorId(idAssunto);
	}

	public Organization getUnidadeOrigem() {
		return unidadeOrigem;
	}

	/**
	public void setUnidadeOrigem(Orgao unidadeOrigem) {
		this.unidadeOrigem = unidadeOrigem;
	}
	*/

	public void setUnidadeOrigemById(int idUnidadeOrigem) {
		if(idUnidadeOrigem == 0) {
			throw new ValidationException("Você não selecionou o Orgão!", "Orgao", "O campo Orgão é obrigatório.");
		}
		this.unidadeOrigem = Organization.getOrgaoPorId(idUnidadeOrigem);
	}

	public Situation getSituacao() {
		return situacao;
	}

	/**
	public void setSituacao(Situacao situacao) {
		this.situacao = situacao;
	}
	*/
	public void setSituacaoById(int idSituacao) {
		if(idSituacao == 0) {
			throw new ValidationException("Você não selecionou a Situação!", "Situacao", "O campo Situação é obrigatório.");
		}
		this.situacao = Situation.getSituacaoPorId(idSituacao);
	}
	
	public String getObservacao() {
		return observacao;
	}

	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}

	public LocalDateTime getDataEntrada() {
		return dataEntrada;
	}

	public void setDataEntrada(LocalDateTime dataEntrada) {
		this.dataEntrada = dataEntrada;
	}

	public Organization getUnidadeDestino() {
		return unidadeDestino;
	}

	public void setUnidadeDestino(Organization unidadeDestino) {
		this.unidadeDestino = unidadeDestino;
	}

	public LocalDateTime getDataSaida() {
		return dataSaida;
	}

	public void setDataSaida(LocalDateTime dataSaida) {
		if(dataSaida.isAfter(this.dataEntrada)) {
			this.dataSaida = dataSaida;
		}
		else {
			throw new ValidationException("Data de saída anterior a data de entrada!", "Data", "Verifique a Data e a Hora do seu computador.");
		}
		
	}
}
