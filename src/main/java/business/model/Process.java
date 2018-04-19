/**
 * 
 */
package business.model;

import java.io.File;
import java.time.LocalDateTime;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSeeAlso;

import business.service.ValidationException;

/**
 * @author lets
 *
 */
@XmlRootElement
@XmlSeeAlso(Interested.class)
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
	@XmlElement(name="type")
	public String getTipo () {
		return this.tipoOficio? "Ofício" : "Processo";
	}
	
	@XmlElement(name="number")
	public String getNumero() {
		if(this.isTipoOficio()) {
			return this.numero.replaceAll("(\\d{4})(\\d{4})(\\w)", "$1/$2-$3");
		}
		else {
			return this.numero.replaceAll("(\\d{5})(\\d{6})(\\d{4})(\\d{2})", "$1.$2/$3.$4");
		}
	}

	public void setNumero(String numero) throws ValidationException {
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
	@XmlElement
	public Interested getInteressado() {
		return interessado;
	}

	public void setInteressado(Interested interessado) {
		this.interessado = interessado;
	}

	/**
	 * @return the assunto
	 */
	@XmlElement(name="subject")
	public Subject getAssunto() {
		return assunto;
	}

	/**
	public void setAssunto(Assunto assunto) {
		this.assunto = assunto;
	}
	 * @throws ValidationException 
	*/
	
	public void setAssuntoById(int idAssunto) throws ValidationException {
		if(idAssunto == 0) {
			throw new ValidationException("Você não selecionou um assunto.", "Assunto", "Campo assunto é obrigatório.");
		}
		this.assunto = Subject.getAssuntoPorId(idAssunto);
	}
	@XmlElement(name="organization")
	public Organization getUnidadeOrigem() {
		return unidadeOrigem;
	}

	/**
	public void setUnidadeOrigem(Orgao unidadeOrigem) {
		this.unidadeOrigem = unidadeOrigem;
	}
	 * @throws ValidationException 
	*/

	public void setUnidadeOrigemById(int idUnidadeOrigem) throws ValidationException {
		if(idUnidadeOrigem == 0) {
			throw new ValidationException("Você não selecionou o Orgão!", "Orgao", "O campo Orgão é obrigatório.");
		}
		this.unidadeOrigem = Organization.getOrgaoPorId(idUnidadeOrigem);
	}
	@XmlElement(name="situation")
	public Situation getSituacao() {
		return situacao;
	}

	/**
	public void setSituacao(Situacao situacao) {
		this.situacao = situacao;
	}
	 * @throws ValidationException 
	*/
	public void setSituacaoById(int idSituacao) throws ValidationException {
		if(idSituacao == 0) {
			throw new ValidationException("Você não selecionou a Situação!", "Situacao", "O campo Situação é obrigatório.");
		}
		this.situacao = Situation.getSituacaoPorId(idSituacao);
	}
	@XmlElement(name="observation")
	public String getObservacao() {
		return observacao;
	}

	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}
	@XmlElement(name="in")
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
	@XmlElement(name="out")
	public LocalDateTime getDataSaida() {
		return dataSaida;
	}

	public void setDataSaida(LocalDateTime dataSaida) throws ValidationException {
		if(dataSaida.isAfter(this.dataEntrada)) {
			this.dataSaida = dataSaida;
		}
		else {
			throw new ValidationException("Data de saída anterior a data de entrada!", "Data", "Verifique a Data e a Hora do seu computador.");
		}
		
	}
	public void toXml() throws JAXBException {
		File file = new File("C:\\file.xml");
		JAXBContext jaxbContext = JAXBContext.newInstance(this.getClass());
		Marshaller jaxbMarshaller = jaxbContext.createMarshaller();

		jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

		jaxbMarshaller.marshal(this, file);
		jaxbMarshaller.marshal(this, System.out);

	}
}
